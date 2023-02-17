import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import pojo.CreateOrderJson;
import pojo.CreateUserJson;
import pojo.CreateUserResponseJson;
import requests.DeleteRequest;
import requests.GetRequest;
import requests.PatchRequest;
import requests.PostRequest;

import java.util.List;

public abstract class AbstractTest {

    // переменная для BaseURI
    String baseURI = "https://stellarburgers.nomoreparties.site/";

    // переменные для эндпоинтов
    String endpointCreateUser = "/api/auth/register";
    String endpointLogin = "/api/auth/login";
    String endpointUser = "/api/auth/user";
    String endpointOrder = "/api/orders";

    // создание запросов
    GetRequest getRequest = new GetRequest(baseURI);
    PostRequest postRequest = new PostRequest(baseURI);
    PatchRequest patchRequest = new PatchRequest(baseURI);
    DeleteRequest deleteRequest = new DeleteRequest(baseURI);

    // переменные для JSON с корректными данными
    String email;
    String name;
    String password;

    // переменные для ингредиентов
    String bunR2D3 = "61c0c5a71d1f82001bdaaa6d";
    String protostomiaMeat = "61c0c5a71d1f82001bdaaa6f";
    String beefMeteor = "61c0c5a71d1f82001bdaaa70";
    String marsCuttlet = "61c0c5a71d1f82001bdaaa71";
    String spicyXSause = "61c0c5a71d1f82001bdaaa72";
    String lumenFillet = "61c0c5a71d1f82001bdaaa6e";
    String spaceSause = "61c0c5a71d1f82001bdaaa73";
    String galacticSause = "61c0c5a71d1f82001bdaaa74";
    String craterBun = "61c0c5a71d1f82001bdaaa6c";
    String spikesSause = "61c0c5a71d1f82001bdaaa75";
    String mineralRings = "61c0c5a71d1f82001bdaaa76";
    String treeFetus = "61c0c5a71d1f82001bdaaa77";
    String marsCrystals = "61c0c5a71d1f82001bdaaa78";
    String exsoPlatanga = "61c0c5a71d1f82001bdaaa79";
    String asteroidCheese = "61c0c5a71d1f82001bdaaa7a";

    public String generateRandomName(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    public String getAccessToken(Response response) {
        CreateUserResponseJson createUserResponseJson = response
                .body()
                .as(CreateUserResponseJson.class);
        String token = createUserResponseJson.getAccessToken();
        return token.substring(7);
    }

    public Response createUser(String name, String email, String password) {
        // создание pojo для создания юзера с коректными данными
        CreateUserJson correctUserJson = new CreateUserJson(email, password, name);

        // запрос на создание юзера
        return postRequest.sendPostRequest(correctUserJson, endpointCreateUser);
    }

    public Response createOrderUnathorized(List<String> ingredients) {
        // json для создания заказа
        CreateOrderJson createOrderJsonObject = new CreateOrderJson(ingredients);

        return postRequest.sendPostRequest(createOrderJsonObject, endpointOrder);
    }

    public Response createOrderAthorized(List<String> ingredients, String accessToken) {
        // json для создания заказа
        CreateOrderJson createOrderJsonObject = new CreateOrderJson(ingredients);

        return postRequest.sendPostRequestWithAuthToken(createOrderJsonObject, endpointOrder, accessToken);
    }
}
