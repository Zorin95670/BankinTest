package service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.CompletionStage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.ws.WSBodyReadables;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;

/**
 * Class to manage bankin service
 * 
 * @author vincent
 *
 */
public class BankinService {
	private final WSClient ws;
	private final static String BANKIN_URL = "https://sync.bankin.com/v2/";
	private final static String DEFAULT_LIMIT_RECORDS = "25";

	/**
	 * Create service to manage bankin api
	 * 
	 * @param ws
	 *            WS Client to send bankin request
	 */
	public BankinService(WSClient ws) {
		this.ws = ws;
	}

	/**
	 * Create default request for bankin api
	 * 
	 * @param url
	 *            url to request
	 * @param data
	 *            data to set in request
	 * @return default bankin request
	 */
	public WSRequest createRequest(final String url, final JsonNode data) {
		WSRequest request = ws.url(url);

		request.addQueryParameter("client_id", data.get("client_id").asText());
		request.addQueryParameter("client_secret", data.get("client_secret").asText());
		request.addQueryParameter("limit", DEFAULT_LIMIT_RECORDS);
		request.addHeader("Bankin-Version", "2016-01-18");

		return request;
	}

	/**
	 * Connect user to bankin service
	 * 
	 * @param data
	 *            Data for bankin request
	 * @param defaultResponse
	 *            response of bankin service
	 * @return access token
	 */
	public final CompletionStage<String> connect(final JsonNode data, final ObjectNode defaultResponse) {
		WSRequest request = createRequest(BANKIN_URL + "authenticate", data);

		request.addQueryParameter("email", data.get("email").asText());
		request.addQueryParameter("password", data.get("password").asText());

		return request.post("").thenApply(r -> {
			JsonNode tokenResponse = r.getBody(WSBodyReadables.instance.json());

			if (!tokenResponse.hasNonNull("access_token")) {
				defaultResponse.put("error", "bad credential");
				return null;
			}

			return tokenResponse.get("access_token").asText();
		});
	}

	/**
	 * Retrieves all transaction for a bankin user
	 * 
	 * @param data
	 *            Data for bankin request
	 * @param defaultResponse
	 *            response of bankin service
	 * @return transactions
	 */
	public final CompletionStage<ObjectNode> getAllTransactions(final JsonNode data, final ObjectNode defaultResponse) {
		WSRequest request = createRequest(BANKIN_URL + "transactions", data);

		return connect(data, defaultResponse).thenApply(token -> {
			if (token == null) {
				return defaultResponse;
			}

			request.addHeader("Authorization", "Bearer " + token);

			return getTransactions(token, request, data, defaultResponse).toCompletableFuture().join();
		});
	}

	/**
	 * Retrieves all transaction for a period for a bankin user
	 * 
	 * @param data
	 *            Data for bankin request
	 * @param defaultResponse
	 *            response of bankin service
	 * @return transactions
	 */
	public final CompletionStage<ObjectNode> getAllTransactionsForPeriod(final JsonNode data,
			final ObjectNode defaultResponse) {
		WSRequest request = createRequest(BANKIN_URL + "transactions", data);

		request.addQueryParameter("since", data.get("start").asText());
		request.addQueryParameter("until", data.get("end").asText());

		return connect(data, defaultResponse).thenApply(token -> {
			if (token == null) {
				return defaultResponse;
			}

			request.addHeader("Authorization", "Bearer " + token);

			return getTransactions(token, request, data, defaultResponse).toCompletableFuture().join();
		});
	}

	/**
	 * Retrieves transactions from bankin api
	 * 
	 * @param token
	 *            token of user
	 * @param request
	 *            bankin request
	 * @param data
	 *            Data for bankin request
	 * @param defaultResponse
	 *            response of bankin service
	 * @return transactions
	 */
	public final CompletionStage<ObjectNode> getTransactions(final String token, final WSRequest request,
			final JsonNode data, final ObjectNode defaultResponse) {
		if (data.hasNonNull("next")) {
			request.addQueryParameter("after", data.get("next").asText());
		}

		return request.get().thenApply(r -> {
			JsonNode response = r.getBody(WSBodyReadables.instance.json());
			ArrayNode transactions = JsonNodeFactory.instance.arrayNode();

			for (int i = 0; i < response.get("resources").size(); i += 1) {
				transactions.add(response.get("resources").get(i));
			}

			if (response.hasNonNull("pagination") && response.get("pagination").hasNonNull("next_uri")) {
				defaultResponse.put("nextTransactions",
						getNextToken(response.get("pagination").get("next_uri").asText()));
			}

			if (response.hasNonNull("pagination") && response.get("pagination").hasNonNull("previous_uri")) {
				defaultResponse.put("previousTransactions",
						getNextToken(response.get("pagination").get("previous_uri").asText()));

			}

			defaultResponse.set("transactions", transactions);

			return defaultResponse;
		});
	}

	/**
	 * Retrieve value from after of bankin next uri
	 * 
	 * @param uri
	 *            next uri
	 * @return value of after query parameter
	 */
	public String getNextToken(final String uri) {
		if (uri == null) {
			return null;
		}

		int start = uri.indexOf("after=") + "after=".length();
		int end = uri.indexOf("&limit");

		try {
			return URLDecoder.decode(uri.substring(start, end), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
