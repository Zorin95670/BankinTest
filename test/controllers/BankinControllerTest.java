package controllers;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.mvc.Result;

public class BankinControllerTest extends DefaultControllerTest {


	@Test
	public void allTest() {
		String uri = "/API/Arrondi/All";
        ObjectNode request = JsonNodeFactory.instance.objectNode();
        Result result = null;

        result = post(uri, request);
        
        Assert.assertEquals("1", BAD_REQUEST, result.status());
        
        request.put("client_id", "775683bc70d94beaa8044c81b2f16006");
        request.put("client_secret", "sMgdYUzUPpo1DxbR67qP2ZbuTmU7H9gikvWPigDnQro9fk0PsRcb4EvI0iRheAJr");
        request.put("email", "user2@mail.com");
        request.put("password", "a!Strongp#assword2");

        result = post(uri, request);
        
        System.out.println(resultAsJson(result));
        Assert.assertEquals("2", OK, result.status());
	}
}
