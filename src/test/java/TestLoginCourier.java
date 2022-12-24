import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scooter.courier.Courier;
import scooter.courier.CourierClient;
import scooter.courier.CourierCredentials;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestLoginCourier {

    CourierClient courierClient;
    Courier courier;
    int courierId;
    CourierCredentials courierCredentials;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandomCourier();
        courierClient.createCourier(courier);
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    @After
    public void tearDown() {
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    @Description("Создание курьеара и авторизвция с корректными учетными данными,проверка получения statusCode=200")
    public void successLoginCourierTest() {
        ValidatableResponse loginResponse = courierClient.loginCourier(courierCredentials).statusCode(200);
        courierId = loginResponse.extract().path("id");
        loginResponse.assertThat().body("id", notNullValue());
        System.out.println(courierId);
    }

    @Test
    @DisplayName("Авторизация пользователя с некорректным логином")
    @Description("Создания курьера и проверка его авторизации с некорректным лигином ,проверка получения statusCode=404")
    public void failedLoginCourierIncorrectLoginTest() {
        ValidatableResponse loginResponse = courierClient.loginCourier(courierCredentials);
        courierId = loginResponse.extract().path("id");
        CourierCredentials incorrectLoginCred = new CourierCredentials(courier.getLogin() + "test", courier.getPassword());
        ValidatableResponse failedLoginResponse = courierClient.loginCourier(incorrectLoginCred).statusCode(404);
        failedLoginResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация пользователя с некорректным паролем")
    @Description("Создание курьера и проверка его авторизации с некорректным паролем ,проверка получения statusCode=404")
    public void failedLoginCourierIncorrectPasswordTest() {
        ValidatableResponse loginResponse = courierClient.loginCourier(courierCredentials);
        courierId = loginResponse.extract().path("id");
        CourierCredentials incorrectPasswordCred = new CourierCredentials(courier.getLogin(), courier.getPassword() + "123");
        ValidatableResponse failedLoginResponse = courierClient.loginCourier(incorrectPasswordCred).statusCode(404);
        failedLoginResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация пользователя без логина")
    @Description("Создание курьера и проверка его авторизации без логина ,проверка получения statusCode=400")
    public void failedLoginCourierWithoutLoginTest() {
        ValidatableResponse loginResponse = courierClient.loginCourier(courierCredentials);
        courierId = loginResponse.extract().path("id");
        CourierCredentials withoutLoginCred = new CourierCredentials("", courier.getPassword());
        ValidatableResponse failedLoginResponse = courierClient.loginCourier(withoutLoginCred).statusCode(400);
        failedLoginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авотризация пользователя без пароля")
    @Description("Создание курьера и проверка его авторизации без пароля ,проверка получения statusCode=400")
    public void failedLoginCourierWithoutPasswordTest() {
        ValidatableResponse loginResponse = courierClient.loginCourier(courierCredentials);
        courierId = loginResponse.extract().path("id");
        CourierCredentials withoutPasswordCred = new CourierCredentials(courier.getLogin(), "");
        ValidatableResponse failedLoginResponse = courierClient.loginCourier(withoutPasswordCred).statusCode(400);
        failedLoginResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
}