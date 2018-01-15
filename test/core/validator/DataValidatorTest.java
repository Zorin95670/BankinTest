package core.validator;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DataValidatorTest {
	private DataValidator validator = new DataValidator();
	private ObjectNode response;

	@Test
	public final void validateRequestAllTest() {
		ObjectNode data = null;
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("1a", validator.validateRequestAll(response, data));
		Assert.assertTrue("1b", response.hasNonNull("error"));
		Assert.assertEquals("1c", "empty email", response.get("error").asText());

		data = JsonNodeFactory.instance.objectNode();
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("2a", validator.validateRequestAll(response, data));
		Assert.assertTrue("2b", response.hasNonNull("error"));
		Assert.assertEquals("2c", "empty email", response.get("error").asText());
		
		data.put("email", "email@gmail.com");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("3a", validator.validateRequestAll(response, data));
		Assert.assertTrue("3b", response.hasNonNull("error"));
		Assert.assertEquals("3c", "empty password", response.get("error").asText());
		
		data.put("password", "password");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("4a", validator.validateRequestAll(response, data));
		Assert.assertTrue("4b", response.hasNonNull("error"));
		Assert.assertEquals("4c", "empty client id", response.get("error").asText());
		
		data.put("client_id", "client_id");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("5a", validator.validateRequestAll(response, data));
		Assert.assertTrue("5b", response.hasNonNull("error"));
		Assert.assertEquals("5c", "empty client secret", response.get("error").asText());
		
		data.put("client_secret", "secret");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("6a", validator.validateRequestAll(response, data));
		Assert.assertFalse("6b", response.hasNonNull("error"));
		
		data.put("next", "");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("7a", validator.validateRequestAll(response, data));
		Assert.assertTrue("7b", response.hasNonNull("error"));
		Assert.assertEquals("7c", "bad next value", response.get("error").asText());
		
		data.put("next", "a");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("8a", validator.validateRequestAll(response, data));
		Assert.assertFalse("8b", response.hasNonNull("error"));
	}

	@Test
	public final void validateRequestPeriodTest() {
		ObjectNode data = null;
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("1a", validator.validateRequestPeriod(response, data));
		Assert.assertTrue("1b", response.hasNonNull("error"));
		Assert.assertEquals("1c", "empty email", response.get("error").asText());

		data = JsonNodeFactory.instance.objectNode();
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("2a", validator.validateRequestPeriod(response, data));
		Assert.assertTrue("2b", response.hasNonNull("error"));
		Assert.assertEquals("2c", "empty email", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("3a", validator.validateRequestPeriod(response, data));
		Assert.assertTrue("3b", response.hasNonNull("error"));
		Assert.assertEquals("3c", "empty email", response.get("error").asText());

		data = JsonNodeFactory.instance.objectNode();
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("4a", validator.validateRequestPeriod(response, data));
		Assert.assertTrue("4b", response.hasNonNull("error"));
		Assert.assertEquals("4c", "empty email", response.get("error").asText());
		
		data.put("email", "email@gmail.com");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("5a", validator.validateRequestPeriod(response, data));
		Assert.assertTrue("5b", response.hasNonNull("error"));
		Assert.assertEquals("5c", "empty password", response.get("error").asText());
		
		data.put("password", "password");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("6a", validator.validateRequestPeriod(response, data));
		Assert.assertTrue("6b", response.hasNonNull("error"));
		Assert.assertEquals("6c", "empty client id", response.get("error").asText());
		
		data.put("client_id", "client_id");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("7a", validator.validateRequestPeriod(response, data));
		Assert.assertTrue("7b", response.hasNonNull("error"));
		Assert.assertEquals("7c", "empty client secret", response.get("error").asText());
		
		data.put("client_secret", "client_secret");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("8a", validator.validateRequestPeriod(response, data));
		Assert.assertTrue("8b", response.hasNonNull("error"));
		Assert.assertEquals("8c", "empty date start", response.get("error").asText());
		
		data.put("start", "2016-01-01");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("9a", validator.validateRequestPeriod(response, data));
		Assert.assertTrue("9b", response.hasNonNull("error"));
		Assert.assertEquals("9c", "empty date end", response.get("error").asText());
		
		data.put("end", "2015-01-01");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("10a", validator.validateRequestPeriod(response, data));
		Assert.assertTrue("10b", response.hasNonNull("error"));
		Assert.assertEquals("10c", "bad period", response.get("error").asText());
		
		data.put("end", "2017-01-01");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("11a", validator.validateRequestPeriod(response, data));
		Assert.assertFalse("11b", response.hasNonNull("error"));
		
		data.put("next", "");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("12a", validator.validateRequestPeriod(response, data));
		Assert.assertTrue("12b", response.hasNonNull("error"));
		Assert.assertEquals("12c", "bad next value", response.get("error").asText());
		
		data.put("next", "a");
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("13a", validator.validateRequestPeriod(response, data));
		Assert.assertFalse("13b", response.hasNonNull("error"));
	}

	@Test
	public final void validateEmailTest() {
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("1a", validator.validateEmail(response, null));
		Assert.assertTrue("1b", response.hasNonNull("error"));
		Assert.assertEquals("1c", "empty email", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("2a", validator.validateEmail(response, ""));
		Assert.assertTrue("2b", response.hasNonNull("error"));
		Assert.assertEquals("2c", "empty email", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("3a", validator.validateEmail(response, "a@gmail.com"));
		Assert.assertFalse("3b", response.hasNonNull("error"));
	}

	@Test
	public final void validateClientIdTest() {
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("1a", validator.validateClientId(response, null));
		Assert.assertTrue("1b", response.hasNonNull("error"));
		Assert.assertEquals("1c", "empty client id", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("2a", validator.validateClientId(response, ""));
		Assert.assertTrue("2b", response.hasNonNull("error"));
		Assert.assertEquals("2c", "empty client id", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("3a", validator.validateClientId(response, "a"));
		Assert.assertFalse("3b", response.hasNonNull("error"));

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("4a", validator.validateClientId(response, "775683bc70d94beaa8044c81b2f16006"));
		Assert.assertFalse("4b", response.hasNonNull("error"));
	}

	@Test
	public final void validatePasswordTest() {
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("1a", validator.validatePassword(response, null));
		Assert.assertTrue("1b", response.hasNonNull("error"));
		Assert.assertEquals("1c", "empty password", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("2a", validator.validatePassword(response, ""));
		Assert.assertTrue("2b", response.hasNonNull("error"));
		Assert.assertEquals("2c", "empty password", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("3a", validator.validatePassword(response, "a"));
		Assert.assertFalse("3b", response.hasNonNull("error"));

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("4a", validator.validatePassword(response,
				"sMgdYUzUPpo1DxbR67qP2ZbuTmU7H9gikvWPigDnQro9fk0PsRcb4EvI0iRheAJr"));
		Assert.assertFalse("4b", response.hasNonNull("error"));
	}

	@Test
	public final void validateClientSecretTest() {
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("1a", validator.validateClientSecret(response, null));
		Assert.assertTrue("1b", response.hasNonNull("error"));
		Assert.assertEquals("1c", "empty client secret", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("2a", validator.validateClientSecret(response, ""));
		Assert.assertTrue("2b", response.hasNonNull("error"));
		Assert.assertEquals("2c", "empty client secret", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("3a", validator.validateClientSecret(response, "a"));
		Assert.assertFalse("3b", response.hasNonNull("error"));

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("4a", validator.validateClientSecret(response,
				"sMgdYUzUPpo1DxbR67qP2ZbuTmU7H9gikvWPigDnQro9fk0PsRcb4EvI0iRheAJr"));
		Assert.assertFalse("4b", response.hasNonNull("error"));
	}

	@Test
	public final void validateDateTest() {
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("1a", validator.validateDate(response, null, "start"));
		Assert.assertTrue("1b", response.hasNonNull("error"));
		Assert.assertEquals("1c", "empty date start", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("2a", validator.validateDate(response, "", "start"));
		Assert.assertTrue("2b", response.hasNonNull("error"));
		Assert.assertEquals("2c", "empty date start", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("3a", validator.validateDate(response, "a", "start"));
		Assert.assertTrue("3b", response.hasNonNull("error"));
		Assert.assertEquals("3c", "bad date start", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("4a", validator.validateDate(response, "01/01/2017", "start"));
		Assert.assertTrue("4b", response.hasNonNull("error"));
		Assert.assertEquals("4c", "bad date start", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("5a", validator.validateDate(response, "2015-15-01", "start"));
		Assert.assertFalse("5b", response.hasNonNull("error"));
		Assert.assertTrue("5c", response.hasNonNull("start"));
		Assert.assertEquals("5d", "2016-03-01", response.get("start").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("6a", validator.validateDate(response, "2015-05-41", "start"));
		Assert.assertFalse("6b", response.hasNonNull("error"));
		Assert.assertTrue("6c", response.hasNonNull("start"));
		Assert.assertEquals("6d", "2015-06-10", response.get("start").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("7a", validator.validateDate(response, "2015-01-01", "start"));
		Assert.assertFalse("7b", response.hasNonNull("error"));
		Assert.assertTrue("7c", response.hasNonNull("start"));
		Assert.assertEquals("7d", "2015-01-01", response.get("start").asText());
	}

	@Test
	public final void validatePeriodTest() {
		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("1a", validator.validatePeriod(response, null, null));
		Assert.assertTrue("1b", response.hasNonNull("error"));
		Assert.assertEquals("1c", "unexpected error", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("2a", validator.validatePeriod(response, "2016-01-01", null));
		Assert.assertTrue("2b", response.hasNonNull("error"));
		Assert.assertEquals("2c", "unexpected error", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("3a", validator.validatePeriod(response, null, "2016-01-01"));
		Assert.assertTrue("3b", response.hasNonNull("error"));
		Assert.assertEquals("3c", "unexpected error", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertFalse("4a", validator.validatePeriod(response, "2017-01-01", "2016-01-01"));
		Assert.assertTrue("4b", response.hasNonNull("error"));
		Assert.assertEquals("4c", "bad period", response.get("error").asText());

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("5a", validator.validatePeriod(response, "2017-01-01", "2017-01-01"));
		Assert.assertFalse("5b", response.hasNonNull("error"));

		response = JsonNodeFactory.instance.objectNode();
		Assert.assertTrue("6a", validator.validatePeriod(response, "2017-01-01", "2018-01-01"));
		Assert.assertFalse("6b", response.hasNonNull("error"));
	}
}
