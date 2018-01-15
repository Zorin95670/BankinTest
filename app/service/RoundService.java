package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Service to manage round api
 * 
 * @author vincent
 *
 */
public final class RoundService {

	private RoundService() {

	}

	/**
	 * Return the round of value. If value has some decimal, the return value
	 * will be the no decimal increase of 1.
	 * 
	 * If the value has no decimal and the round is superior to 10% of the
	 * initial value, no round is make.
	 * 
	 * But if the result round is under 10%, the result round is a multiplier of
	 * 5.
	 * 
	 * @param value
	 *            the value
	 * @return round of value
	 */
	public final static double get(final double value) {
		int noDecimal = (int) value;

		if (noDecimal - value < 0.00f) {
			return noDecimal + 1;
		}

		int round = noDecimal - (((int) value) % 5) + 5;

		double percent = (round - noDecimal) * 100 / noDecimal;

		if (percent > 10.00f) {
			return value;
		}

		return round;
	}

	/**
	 * Applies round to transaction
	 * 
	 * @param transaction
	 *            transaction
	 */
	public final static void setRoundToTransaction(JsonNode transaction) {
		if (transaction.get("amount").asDouble() >= 0) {
			return;
		}

		((ObjectNode) transaction).put("round", get(-transaction.get("amount").asDouble()));
	}
}
