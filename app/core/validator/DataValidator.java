package core.validator;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Validator for bankin request
 * 
 * @author vincent
 *
 */
public class DataValidator implements IValidator {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Validates request all bankin transaction
	 * 
	 * @param response
	 *            response to return
	 * @param request
	 *            user request
	 * @return if request is valid return true otherwise false
	 */
	public final boolean validateRequestAll(final ObjectNode response, final JsonNode request) {
		if (!validateEmail(response, getData(request, "email"))) {
			return false;
		}

		if (!validatePassword(response, getData(request, "password"))) {
			return false;
		}

		if (!validateClientId(response, getData(request, "client_id"))) {
			return false;
		}

		if (!validateClientSecret(response, getData(request, "client_secret"))) {
			return false;
		}

		return true;
	}

	/**
	 * Validates request all bankin transaction for a specific period
	 * 
	 * @param response
	 *            response to return
	 * @param request
	 *            user request
	 * @return if request is valid return true otherwise false
	 */
	public final boolean validateRequestPeriod(final ObjectNode response, final JsonNode request) {
		if (!validateEmail(response, getData(request, "email"))) {
			return false;
		}

		if (!validatePassword(response, getData(request, "password"))) {
			return false;
		}

		if (!validateClientId(response, getData(request, "client_id"))) {
			return false;
		}

		if (!validateClientSecret(response, getData(request, "client_secret"))) {
			return false;
		}

		if (!validateDate(response, getData(request, "start"), "start")) {
			return false;
		}

		if (!validateDate(response, getData(request, "end"), "end")) {
			return false;
		}

		if (!validatePeriod(response, getData(request, "start"), getData(request, "end"))) {
			return false;
		}

		return true;
	}

	/**
	 * Validates client id
	 * 
	 * @param response
	 *            response to return
	 * @param clientId
	 *            client id
	 * @return if client is empty return false otherwise true
	 */
	public final boolean validateClientId(final ObjectNode response, final String clientId) {
		if (clientId == null || clientId.length() == 0) {
			response.put("error", "empty client id");
			return false;
		}

		return true;
	}

	/**
	 * Validates user email
	 * 
	 * @param response
	 *            response to return
	 * @param email
	 *            email
	 * @return if email is empty return false otherwise true
	 */
	public final boolean validateEmail(final ObjectNode response, final String email) {
		if (email == null || email.length() == 0) {
			response.put("error", "empty email");
			return false;
		}

		return true;
	}

	/**
	 * Validates client secret
	 * 
	 * @param response
	 *            response to return
	 * @param clientSecret
	 *            client secret
	 * @return if client secret is empty return false otherwise true
	 */
	public final boolean validateClientSecret(final ObjectNode response, final String clientSecret) {
		if (clientSecret == null || clientSecret.length() == 0) {
			response.put("error", "empty client secret");
			return false;
		}

		return true;
	}

	/**
	 * Validates user password
	 * 
	 * @param response
	 *            response to return
	 * @param password
	 *            password
	 * @return if password is empty return false otherwise true
	 */
	public final boolean validatePassword(final ObjectNode response, final String password) {
		if (password == null || password.length() == 0) {
			response.put("error", "empty password");
			return false;
		}

		return true;
	}

	/**
	 * Validates date
	 * 
	 * @param response
	 *            response to return
	 * @param date
	 *            date
	 * @param name
	 *            field name
	 * @return if date has bad format or empty return false otherwise true
	 */
	public final boolean validateDate(final ObjectNode response, final String date, final String name) {
		if (date == null || date.length() == 0) {
			response.put("error", "empty date " + name);
			return false;
		}

		try {
			Date dateParse = dateFormat.parse(date);

			response.put(name, dateFormat.format(dateParse));
		} catch (Exception e) {
			response.put("error", "bad date " + name);
			return false;
		}

		return true;
	}

	/**
	 * Validates period between two dates
	 * 
	 * @param response
	 *            response to return
	 * @param start
	 *            start date
	 * @param end
	 *            end date
	 * @return if start date is superior to end date return false otherwise true
	 */
	public final boolean validatePeriod(final ObjectNode response, final String start, final String end) {
		Date startDate;
		Date endDate;

		try {
			startDate = dateFormat.parse(start);
			endDate = dateFormat.parse(end);
		} catch (Exception e) {
			response.put("error", "unexpected error");
			return false;
		}

		if (startDate.compareTo(endDate) > 0) {
			response.put("error", "bad period");
			return false;
		}

		return true;
	}
}
