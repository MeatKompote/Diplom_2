package requests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AbstractPojo;

import static io.restassured.RestAssured.given;

public class PostRequest extends AbstractRequest {

    public PostRequest(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    public Response sendPostRequest (AbstractPojo json, String endpoint) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(endpoint);
        timeout(1);
        return response;
    }

    public Response sendPostRequestWithAuthToken (AbstractPojo json, String endpoint, String token) {
        Response response = given()
                .auth()
                .oauth2(token)
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(endpoint);
        timeout(1);
        return response;
    }
}
