package requests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetRequest extends AbstractRequest {

    public GetRequest(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    @Step("Отправка Get запроса")
    public Response sendGetRequest (String endpoint) {
        Response response = given()
                .when()
                .get(endpoint);
        timeout(1);
        return response;
    }

    @Step("Отправка Get запроса c access токеном")
    public Response sendGetRequestWithToken (String endpoint, String token) {
        Response response = given()
                .auth()
                .oauth2(token)
                .when()
                .get(endpoint);
        timeout(1);
        return response;
    }
}
