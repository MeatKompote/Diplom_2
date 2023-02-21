package requests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AbstractPojo;

import static io.restassured.RestAssured.given;

public class PatchRequest extends AbstractRequest{

    public PatchRequest(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    @Step("Отправка Patch запроса")
    public static Response sendPatchRequest(AbstractPojo json, String endpoint) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .patch(endpoint);
        timeout(1);
        return response;
    }

    @Step("Отправка Patch запроса с access токеном")
    public Response sendPatchRequestWithAuthToken(AbstractPojo json, String endpoint, String token) {
        Response response = given()
                .auth()
                .oauth2(token)
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .patch(endpoint);
        timeout(1);
        return response;
    }

}

