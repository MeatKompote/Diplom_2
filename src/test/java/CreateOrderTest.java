import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import pojo.CreateOrderJson;
import pojo.CreateOrderResponseJson;
import pojo.IngredientJson;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CreateOrderTest extends AbstractTest {


    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithIngredientsUnauthorized() {
        // json для создания заказа
        CreateOrderJson createOrderJsonObject = new CreateOrderJson(Arrays.asList(bunR2D3, spicyXSause, beefMeteor));

        // отправка запроса на создание заказа
        postRequest.sendPostRequest(createOrderJsonObject, endpointOrder)
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("success", Matchers.equalTo(true))
                .and()
                .assertThat()
                .body("name", Matchers.equalTo("Spicy метеоритный флюоресцентный бургер"));
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderWithIngredientsAuthorized() {
        name = generateRandomName(10);
        email = name + "@test.ru";
        password = "password";

        // создание юзера
        Response createUserResponse = createUser(name, email, password);

        // проверка успешного создания юзера
        createUserResponse
                .then()
                .statusCode(200);

        // сохраниение токена
        String accessToken = getAccessToken(createUserResponse);

        Response createOrderResponse = createOrderAuthorized(Arrays.asList(craterBun, spikesSause, marsCrystals), accessToken);

        // удаление созданного юзера после теста
        deleteRequest.sendDeleteRequestWithToken(endpointUser, accessToken)
                .then()
                .statusCode(202);

        // проверка статуса
        createOrderResponse
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("success", Matchers.equalTo(true))
                .and()
                .assertThat()
                .body("name", Matchers.equalTo("Альфа-сахаридный антарианский краторный бургер"));

        // десериализация
        CreateOrderResponseJson createOrderResponseJsonObject = createOrderResponse.body().as(CreateOrderResponseJson.class);

        // проверка имя юзера из респонса
        assertEquals("Поле name не совпадает", name, createOrderResponseJsonObject.getOrder().getOwner().getName());

        // проверка email из респонса
        assertEquals("Поле email не совпадает", email.toLowerCase(), createOrderResponseJsonObject.getOrder().getOwner().getEmail());

        // проверка индредиентов
        List<IngredientJson> ingredients = createOrderResponseJsonObject.getOrder().getIngredients();
        assertEquals("Первый ингредиент не совпадает", craterBun, ingredients.get(0).get_id());
        assertEquals("Второй ингредиент не совпадает", spikesSause, ingredients.get(1).get_id());
        assertEquals("Третий ингредиент не совпадает", marsCrystals, ingredients.get(2).get_id());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов и без авторизации")
    public void createOrderWithoutIngredientsUnauthorized() {
        // json для создания заказа
        CreateOrderJson createOrderJsonObject = new CreateOrderJson();

        // отправка запроса на создание заказа
        postRequest.sendPostRequest(createOrderJsonObject, endpointOrder)
                .then()
                .statusCode(400)
                .and()
                .assertThat()
                .body("success", Matchers.equalTo(false))
                .and()
                .assertThat()
                .body("message", Matchers.equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неправильным хэшем ингредиентов и без авторизации")
    public void createOrderWithIncorrectIngredientsUnauthorized() {
        // json для создания заказа
        CreateOrderJson createOrderJsonObject = new CreateOrderJson(Arrays.asList("61c0c5a71d1f82001bdaaa7b", "61c0c5a71d1f82001bdaaa7c", "61c0c5a71d1f82001bdaaa7d"));

        // отправка запроса на создание заказа
        postRequest.sendPostRequest(createOrderJsonObject, endpointOrder)
                .then()
                .statusCode(400)
                .and()
                .assertThat()
                .body("success", Matchers.equalTo(false))
                .and()
                .assertThat()
                .body("message", Matchers.equalTo("One or more ids provided are incorrect"));
    }
}
