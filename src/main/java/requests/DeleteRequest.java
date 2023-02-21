package requests;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AbstractPojo;

import static io.restassured.RestAssured.given;

public class DeleteRequest extends AbstractRequest {

    public DeleteRequest(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    @Step("Отправка Delete запроса с access токеном")
    public Response sendDeleteRequestWithToken (String endpoint, String token) {
        Response response = given()
                .auth()
                .oauth2(token)
                .when()
                .delete(endpoint);
        timeout(1);
        return response;
    }
}

