package core.validator;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Interface to validate data
 *
 * @author vincent
 */
public interface IValidator {

	/**
	 * Retrieves easily data from JsonNode
	 *
	 * @param data
	 *            JsonNode
	 * @param key
	 *            key
	 * @return data or null
	 */
	default String getData(final JsonNode data, final String key) {
		if (data == null || key == null) {
			return null;
		}

		if (!data.hasNonNull(key)) {
			return null;
		}

		return data.get(key).asText();
	}
}
