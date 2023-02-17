import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import pojo.UserLoginJson;


public class LoginTest extends AbstractTest {

    @Test
    @DisplayName("Логин с коррекными данными пользователя")
    public void loginWithCorrectCredentials() {

        name = generateRandomName(10);
        email = name + "@test.ru";
        password = "password";

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        responseCreateUser
                .then()
                .statusCode(200);

        // создание pojo для логина с коректными данными
        UserLoginJson userLoginJson = new UserLoginJson(email, password);


        // запрос на логин
        Response responseLogin = postRequest.sendPostRequest(userLoginJson, endpointLogin);

        responseLogin
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("success", Matchers.equalTo(true));

        // удаление
        deleteRequest.sendDeleteRequestWithToken(endpointUser, getAccessToken(responseLogin))
                .then().statusCode(202);
    }

    @Test
    @DisplayName("Логин с неправильным email")
    public void loginWithIncorrectEmail() {
        name = generateRandomName(10);
        email = name + "@test.ru";
        password = "password";
        String incorrectEmail = "incorrectEmail@test.ru";

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        responseCreateUser
                .then()
                .statusCode(200);

        // создание pojo для логина с неверным email
        UserLoginJson incorrectUserLoginJson = new UserLoginJson(incorrectEmail, password);


        // запрос на логин с неверными кредами
        postRequest.sendPostRequest(incorrectUserLoginJson, endpointLogin)
                .then()
                .statusCode(401)
                .and()
                .assertThat().body("success", Matchers.equalTo(false))
                .and()
                .assertThat().body("message", Matchers.equalTo("email or password are incorrect"));

    }

    @Test
    @DisplayName("Логин с неправильным password")
    public void loginWithIncorrectPassword() {
        name = generateRandomName(10);
        email = name + "@test.ru";
        password = "password";
        String incorrectPasword = "incorrectPassword";

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        responseCreateUser
                .then()
                .statusCode(200);

        // создание pojo для логина с неверным email
        UserLoginJson incorrectUserLoginJson = new UserLoginJson(email, incorrectPasword);


        // запрос на логин с неверными кредами
        postRequest.sendPostRequest(incorrectUserLoginJson, endpointLogin)
                .then()
                .statusCode(401)
                .and()
                .assertThat().body("success", Matchers.equalTo(false))
                .and()
                .assertThat().body("message", Matchers.equalTo("email or password are incorrect"));
    }
}
