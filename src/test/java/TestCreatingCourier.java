import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scooter.courier.Courier;
import scooter.courier.CourierClient;

import static org.hamcrest.CoreMatchers.equalTo;


public class TestCreatingCourier {

    CourierClient courierClient;
    Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandomCourier();
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Создание курьера с корректными данными и проверка statusCode=201 и body=true")
    public void successCreatingCourierTest() {
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(201);
        createResponse.assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание одинаковых курьеров")
    @Description("Проверка ответа (status code and body) при попытке создать одинаковых курьеров")
    public void creatingIdenticalCourierTest() {
        courierClient.createCourier(courier);
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(409);
        createResponse.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера с уже занятым логином")
    @Description("Создание курьера с существующим логином и проверка ответа (status code and body)")
    public void creatingExistingLoginCourierTest() {
        courierClient.createCourier(courier);
        String firstCourierLogin = courier.getLogin();
        Courier secondCourier = Courier.getRandomCourier();
        secondCourier.setLogin(firstCourierLogin);
        ValidatableResponse createResponse = courierClient.createCourier(secondCourier).statusCode(409);
        createResponse.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Создание курьера без логина и проверка ответа (status code and body)")
    public void creatingCourierWithoutLoginTest() {
        courier.setLogin("");
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(400);
        createResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Создание курьера без пароля и проверка ответа (status code and body)")
    public void creatingCourierWithoutPasswordTest() {
        courier.setPassword("");
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(400);
        createResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
