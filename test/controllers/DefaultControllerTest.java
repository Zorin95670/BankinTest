package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.Http.RequestBuilder;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

public abstract class DefaultControllerTest extends WithApplication {

	public static final int OK = 200;
	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;

	public final Result post(final String uri, final JsonNode data) {
		RequestBuilder request = Helpers.fakeRequest().method("POST").uri(uri);

		if (data != null) {
			request.bodyJson(data);
		}

		return Helpers.route(this.app, request);
	}
	
    public final JsonNode resultAsJson(final Result result) {
        return Json.parse(Helpers.contentAsString(result));
    }
}
