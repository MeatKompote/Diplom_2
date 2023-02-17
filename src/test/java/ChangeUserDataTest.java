import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import pojo.CreateUserJson;
import pojo.GetUserInfoJson;
import pojo.UserLoginJson;
import requests.PatchRequest;

import static org.junit.Assert.assertEquals;

public class ChangeUserDataTest extends AbstractTest{

    @Test
    @DisplayName("Изменение имени пользователя без авторизации")
    public void changeUserNameUnauthorized() {
        name = generateRandomName(10);
        String newName = "new" + name;
        email = name + "@test.ru";
        password = "password";

        // создание pojo для создания юзера с коректными данными
        CreateUserJson correctCreateUserJson = new CreateUserJson(email, password, name);

        // запрос на создание юзера
        Response responseCreateUser = postRequest.sendPostRequest(correctCreateUserJson, endpointCreateUser);

        responseCreateUser
                .then()
                .statusCode(200);

        // сохранение токена
        String accessToken = getAccessToken(responseCreateUser);

        // создание pojo для изменения юзера
        CreateUserJson userWithNewCredentials = new CreateUserJson(email, password, newName);

        // попытка изменить имя пользователя
        PatchRequest.sendPatchRequest(userWithNewCredentials, endpointUser)
                .then()
                .statusCode(401)
                .and()
                .assertThat().body("success", Matchers.equalTo(false))
                .and()
                .assertThat().body("message", Matchers.equalTo("You should be authorised"));

        // удаление созданного юзера после теста
        deleteRequest.sendDeleteRequestWithToken(endpointUser, accessToken)
                .then().statusCode(202);
    }

    @Test
    @DisplayName("Изменение email без авторизации")
    public void changeUserEmailUnauthorized() {
        name = generateRandomName(10);
        email = name + "@test.ru";
        String newEmail = "new" + email;
        password = "password";

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        responseCreateUser
                .then()
                .statusCode(200);

        // сохранение токена
        String accessToken = getAccessToken(responseCreateUser);

        // создание pojo для изменения юзера
        CreateUserJson userWithNewCredentials = new CreateUserJson(newEmail, password, name);

        // попытка изменить имя пользователя
        PatchRequest.sendPatchRequest(userWithNewCredentials, endpointUser)
                .then()
                .statusCode(401)
                .and()
                .assertThat().body("success", Matchers.equalTo(false))
                .and()
                .assertThat().body("message", Matchers.equalTo("You should be authorised"));


        // удаление созданного юзера после теста
        deleteRequest.sendDeleteRequestWithToken(endpointUser, accessToken)
                .then().statusCode(202);

    }

    @Test
    @DisplayName("Изменение пароля без авторизации")
    public void changeUserPasswordUnauthorized() {
        name = generateRandomName(10);
        email = name + "@test.ru";
        password = "password";
        String newPassword = "new" + password;

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        responseCreateUser
                .then()
                .statusCode(200);

        // сохранение токена
        String accessToken = getAccessToken(responseCreateUser);


        // создание pojo для изменения юзера
        CreateUserJson userWithNewCredentials = new CreateUserJson(email, newPassword, name);

        // попытка изменить имя пользователя
        PatchRequest.sendPatchRequest(userWithNewCredentials, endpointUser)
                .then()
                .statusCode(401)
                .and()
                .assertThat().body("success", Matchers.equalTo(false))
                .and()
                .assertThat().body("message", Matchers.equalTo("You should be authorised"));


        // удаление созданного юзера после теста
        deleteRequest.sendDeleteRequestWithToken(endpointUser, accessToken)
                .then().statusCode(202);

    }

    @Test
    @DisplayName("Изменение имени пользователя с авторизацией")
    public void changeUserNameAuthorized() {
        name = generateRandomName(10);
        String newName = "new" + name;
        email = name + "@test.ru";
        password = "password";

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        responseCreateUser
                .then()
                .statusCode(200);

        // сохранение токена
        String accessToken = getAccessToken(responseCreateUser);

        // создание pojo для изменения юзера
        CreateUserJson userWithNewCredentials = new CreateUserJson(email, password, newName);

        // запрос на изменение данных пользователя
        Response changeUserResponse = patchRequest.sendPatchRequestWithAuthToken(userWithNewCredentials, endpointUser, accessToken);

        // проверка статус кода
        changeUserResponse
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("success" , Matchers.equalTo(true));

        // проверка полей name и email
        GetUserInfoJson getUserInfoJsonobject = changeUserResponse.body().as(GetUserInfoJson.class);

        assertEquals("Поле email не совпадает", email.toLowerCase(), getUserInfoJsonobject.getUser().getEmail());
        assertEquals("Поле name не совпадает", newName, getUserInfoJsonobject.getUser().getName());

        // удаление созданного юзера после теста
        deleteRequest.sendDeleteRequestWithToken(endpointUser, accessToken)
                .then().statusCode(202);
    }

    @Test
    @DisplayName("Изменение email с авторизацией")
    public void changeUserEmailAuthorized() {
        name = generateRandomName(10);
        email = name + "@test.ru";
        String newEmail = "new" + email;
        password = "password";

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        responseCreateUser
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("success" , Matchers.equalTo(true));

        // сохранение токена
        String accessToken = getAccessToken(responseCreateUser);

        // создание pojo для изменения юзера
        CreateUserJson userWithNewCredentials = new CreateUserJson(newEmail, password, name);

        // запрос на изменение данных пользователя
        Response changeUserResponse = patchRequest.sendPatchRequestWithAuthToken(userWithNewCredentials, endpointUser, accessToken);

        // проверка статус кода
        changeUserResponse
                .then()
                .statusCode(200);

        // проверка полей name и email
        GetUserInfoJson getUserInfoJsonobject = changeUserResponse.body().as(GetUserInfoJson.class);

        assertEquals("Поле email не совпадает", newEmail.toLowerCase(), getUserInfoJsonobject.getUser().getEmail());
        assertEquals("Поле name не совпадает", name, getUserInfoJsonobject.getUser().getName());

        // удаление созданного юзера после теста
        deleteRequest.sendDeleteRequestWithToken(endpointUser, accessToken)
                .then().statusCode(202);
    }

    @Test
    @DisplayName("Изменение пароля с авторизацией")
    public void changeUserPasswordAuthorized() {
        name = generateRandomName(10);
        email = name + "@test.ru";
        password = "password";
        String newPassword = "new" + password;

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        responseCreateUser
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("success" , Matchers.equalTo(true));

        // сохранение токена
        String accessToken = getAccessToken(responseCreateUser);

        // создание pojo для изменения юзера
        CreateUserJson userWithNewCredentials = new CreateUserJson(email, newPassword, name);

        // запрос на изменение данных пользователя
        patchRequest.sendPatchRequestWithAuthToken(userWithNewCredentials, endpointUser, accessToken)
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("success", Matchers.equalTo(true));

        // создание pojo для логина юзером с новым паролем
        UserLoginJson userLoginJsonObject = new UserLoginJson(email, newPassword);

        // запрос на логин с новым паролем
        Response userLoginResponse = postRequest.sendPostRequest(userLoginJsonObject, endpointLogin);

        // проверка успешного логина
        userLoginResponse
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("success", Matchers.equalTo(true));

        // сохраниение токена токена
        String newAccessToken = getAccessToken(userLoginResponse);

        // удаление созданного юзера после теста
        deleteRequest.sendDeleteRequestWithToken(endpointUser, newAccessToken)
                .then().statusCode(202);
    }

    @Test
    @DisplayName("Изменение email на уже существующий email")
    public void changeUserEmailWhenEmailIsAlreadyUsed() {
        name = generateRandomName(10);
        email = name + "@test.ru";
        String newEmail = "new" + email;
        password = "password";

        // создание первого юзера
        Response responseCreateFirstUser = createUser(name, email, password);

        // проверка респонс кода
        responseCreateFirstUser
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("success" , Matchers.equalTo(true));

        // сохранение первого токена
        String firstAccessToken = getAccessToken(responseCreateFirstUser);

        // создание второго юзера
        Response responseCreateSecondUser = createUser(name, newEmail, password);

        // проверка респонс кода
        responseCreateSecondUser
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("success" , Matchers.equalTo(true));

        // сохранение второго токена
        String secondAccessToken = getAccessToken(responseCreateSecondUser);

        // создание pojo для изменения юзера
        CreateUserJson userWithNewCredentials = new CreateUserJson(newEmail, password, name);

        // запрос на изменение данных пользователя
        patchRequest.sendPatchRequestWithAuthToken(userWithNewCredentials, endpointUser, firstAccessToken)
                .then()
                .statusCode(403)
                .and()
                .assertThat().body("success", Matchers.equalTo(false))
                .and()
                .assertThat().body("message", Matchers.equalTo("User with such email already exists"));

        // удаление первого созданного юзера после теста
        deleteRequest.sendDeleteRequestWithToken(endpointUser, firstAccessToken)
                .then().statusCode(202);

        // удаление второго созданного юзера после теста
        deleteRequest.sendDeleteRequestWithToken(endpointUser, secondAccessToken)
                .then().statusCode(202);
    }

}
