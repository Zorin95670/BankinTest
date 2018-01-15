package controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import core.validator.DataValidator;
import play.libs.ws.WSClient;
import play.mvc.Result;
import service.BankinService;
import service.RoundService;

/**
 * Controller to manage bankin request
 * 
 * @author vincent
 *
 */
public class BankinController extends AbstractController {

	private BankinService service;
	private DataValidator validator = new DataValidator();

	@Inject
	public BankinController(WSClient ws) {
		this.service = new BankinService(ws);
	}

	/**
	 * Service to retrieve all bankin transactions with an apply round
	 * 
	 * @return json
	 */
	public final CompletionStage<Result> all() {
		JsonNode request = request().body().asJson();
		ObjectNode response = JsonNodeFactory.instance.objectNode();

		if (!validator.validateRequestAll(response, request)) {
			return CompletableFuture.completedFuture(badRequest(response));

		}

		System.out.println(request);
		return service.getAllTransactions(request, response).thenApply(r -> {
			if (!r.hasNonNull("transactions")) {
				return badRequest(r);
			}

			System.out.println("-----------------------");
			System.out.println(r);
			for (int i = 0; i < r.get("transactions").size(); i += 1) {
				setRoundToTransaction(r.get("transactions").get(i));
			}

			return ok(r);
		});

	}

	/**
	 * Service to retrieve all bankin transactions for a period with an apply
	 * round
	 * 
	 * @return json
	 */
	public final CompletionStage<Result> period() {
		JsonNode request = request().body().asJson();
		ObjectNode response = JsonNodeFactory.instance.objectNode();

		if (!validator.validateRequestPeriod(response, request)) {
			return CompletableFuture.completedFuture(ok(response));

		}

		return service.getAllTransactionsForPeriod(request, response).thenApply(r -> {
			for (int i = 0; i < r.get("transactions").size(); i += 1) {
				setRoundToTransaction(r.get("transactions").get(i));
			}

			return ok(r);
		});
	}

	/**
	 * Applies round to transaction
	 * 
	 * @param transaction
	 *            transaction
	 */
	public final void setRoundToTransaction(JsonNode transaction) {
		if (transaction.get("amount").asDouble() >= 0) {
			return;
		}

		((ObjectNode) transaction).put("arrondi", RoundService.get(-transaction.get("amount").asDouble()));
	}
}
