package controllers;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.mvc.Result;

public class BankinControllerTest extends DefaultControllerTest {

	@Test
	public void allTest() {
		String uri = "/API/Round/All";
		ObjectNode request = JsonNodeFactory.instance.objectNode();
		Result result = null;

		result = post(uri, request);

		Assert.assertEquals("1", BAD_REQUEST, result.status());

		request.put("client_id", "775683bc70d94beaa8044c81b2f16006");
		request.put("client_secret", "sMgdYUzUPpo1DxbR67qP2ZbuTmU7H9gikvWPigDnQro9fk0PsRcb4EvI0iRheAJr");
		request.put("email", "user2@mail.com");
		request.put("password", "a!Strongp#assword2");

		result = post(uri, request);

		Assert.assertEquals("2", OK, result.status());

		request.put("next", resultAsJson(result).get("nextTranslations").asText());

		result = post(uri, request);

		Assert.assertEquals("3", OK, result.status());
	}

	@Test
	public void periodTest() {
		String uri = "/API/Round/Period";
		ObjectNode request = JsonNodeFactory.instance.objectNode();
		Result result = null;

		result = post(uri, request);

		Assert.assertEquals("1", BAD_REQUEST, result.status());

		request.put("client_id", "775683bc70d94beaa8044c81b2f16006");
		request.put("client_secret", "sMgdYUzUPpo1DxbR67qP2ZbuTmU7H9gikvWPigDnQro9fk0PsRcb4EvI0iRheAJr");
		request.put("email", "user2@mail.com");
		request.put("password", "a!Strongp#assword2");
		request.put("start", "2016-01-01");
		request.put("end", "2016-01-02");

		result = post(uri, request);

		Assert.assertEquals("2", OK, result.status());
	}
}
