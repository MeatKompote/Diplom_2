import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import pojo.CreateOrderResponseJson;
import pojo.ListOfOrdersForUserJson;
import pojo.OrdersInListOfOrdersJson;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GetListOfOrdersTest extends AbstractTest {

    @Test
    @DisplayName("Получение списка заказов без авторизации")
    public void getListOfOrdersUnauthorized() {

        getRequest.sendGetRequest(endpointOrder)
                .then()
                .statusCode(401)
                .and()
                .assertThat()
                .body("success", Matchers.equalTo(false))
                .and()
                .assertThat()
                .body("message", Matchers.equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Получение списка заказов с авторизацией")
    public void getListOfOrdersAuthorized() {
        name = generateRandomName(10);
        email = name + "@test.ru";
        password = "password";

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        // проверка ствтус кода
        responseCreateUser
                .then()
                .statusCode(200);

        // сохранение токена
        String accessToken = getAccessToken(responseCreateUser);

        // создание заказов
        List<String> order1 = Arrays.asList(bunR2D3, spicyXSause, beefMeteor);
        List<String> order2 = Arrays.asList(craterBun, galacticSause, mineralRings);

        Response responseCreateOrder1 = createOrderAuthorized(order1, accessToken);

        responseCreateOrder1
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("success", Matchers.equalTo(true));

        Response responseCreateOrder2 = createOrderAuthorized(order2, accessToken);

        responseCreateOrder2
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("success", Matchers.equalTo(true));

        // десериализация
        CreateOrderResponseJson createOrder1ResponseJsonObject = responseCreateOrder1.body().as(CreateOrderResponseJson.class);
        CreateOrderResponseJson createOrder2ResponseJsonObject = responseCreateOrder2.body().as(CreateOrderResponseJson.class);

        // сохраниение id заказов
        String orderId1 = createOrder1ResponseJsonObject.getOrder().get_id();
        String orderId2 = createOrder2ResponseJsonObject.getOrder().get_id();

        // запрос на получение заказов для юзера
        Response responseGetOrdersForUser = getRequest.sendGetRequestWithToken(endpointOrder, accessToken);

        // удаление юзера
        deleteRequest.sendDeleteRequestWithToken(endpointUser, accessToken)
                .then()
                .statusCode(202);

        // проверка ствтус кода
        responseGetOrdersForUser
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("success", Matchers.equalTo(true));

        // десериализация
        ListOfOrdersForUserJson listOfOrdersForUserJson = responseGetOrdersForUser.body().as(ListOfOrdersForUserJson.class);

        // список заказов
        List<OrdersInListOfOrdersJson> listOfOrders = listOfOrdersForUserJson.getOrders();

        // проверки списка заказов
        assertEquals("Количестово заказов не совпадает с ожидаемым", 2, listOfOrders.size());
        assertEquals("id первого заказа не совпадает", orderId1, listOfOrders.get(0).get_id());
        assertEquals("id второго заказа не совпадает", orderId2, listOfOrders.get(1).get_id());
    }

}
