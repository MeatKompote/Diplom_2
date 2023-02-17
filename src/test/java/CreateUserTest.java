import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

public class CreateUserTest extends AbstractTest {

    @Test
    @DisplayName("Создание юзера, все поля валидны")
    public void createUserAllFieldsAreValid() {

        name = generateRandomName(10);
        email = name + "@test.ru";
        password = "password";

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        // проверка респонс кода
        responseCreateUser
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("success", Matchers.equalTo(true));

        // удаление созданного юзера после теста
        String accessToken = getAccessToken(responseCreateUser);
        deleteRequest.sendDeleteRequestWithToken(endpointUser, accessToken)
                .then().statusCode(202);
    }

    @Test
    @DisplayName("Создание юзера с уже существущим email")
    public void createAlreadyExistingUser() {
        name = generateRandomName(10);
        email = name + "@test.ru";
        password = "password";

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        // сохранение токена для удаления юзера после теста
        String accessToken = getAccessToken(responseCreateUser);

        // повторный запрос на создание того же юзера
        Response responseCreateDuplicateUser = createUser(name, email, password);

        responseCreateDuplicateUser
                .then()
                .statusCode(403)
                .and()
                .assertThat().body("success", Matchers.equalTo(false))
                .and()
                .assertThat().body("message", Matchers.equalTo("User already exists"));

        // удаление созданного юзера после теста
        deleteRequest.sendDeleteRequestWithToken(endpointUser, accessToken)
                .then().statusCode(202);
    }

    @Test
    @DisplayName("Создание юзера с уже пустым полем Email")
    public void createUserWhenEmailIsEmpty() {
        name = generateRandomName(10);
        email = "";
        password = "password";

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        responseCreateUser
                .then()
                .statusCode(403)
                .and()
                .assertThat().body("success", Matchers.equalTo(false))
                .and()
                .assertThat().body("message", Matchers.equalTo("Email, password and name are required fields"));

    }

    @Test
    @DisplayName("Создание юзера с пустым полем password")
    public void createUserWhenPasswordIsEmpty() {
        name = generateRandomName(10);
        email = name + "@test.ru";
        password = "";

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        responseCreateUser
                .then()
                .statusCode(403)
                .and()
                .assertThat().body("success", Matchers.equalTo(false))
                .and()
                .assertThat().body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание юзера с пустым полем name")
    public void createUserWhenNameIsEmpty() {
        name = "";
        email = generateRandomName(10) + "@test.ru";
        password = "password";

        // создание юзера
        Response responseCreateUser = createUser(name, email, password);

        responseCreateUser
                .then()
                .statusCode(403)
                .and()
                .assertThat().body("success", Matchers.equalTo(false))
                .and()
                .assertThat().body("message", Matchers.equalTo("Email, password and name are required fields"));
    }
}
