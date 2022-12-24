package scooter.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends CourierCredentials.ScooterRestClient {

    private static final String COURIER_PATH = "api/v1/courier/";
    private static final String COURIER_LOGIN = "api/v1/courier/login";

    @Step("Вход пользователя с логином :{credentials.login} и паролем :{credentials.password}")
    public ValidatableResponse loginCourier(CourierCredentials courierCredentials) {
        return given()
                .header("Content-type", "application/json")
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    @Step("Создание курьера с логином :{courier.login} паролем :{courier.password} и имя :{courier.firstName}")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Удалить курьера по ID :{courierId}")
    public ValidatableResponse deleteCourier(int courierId) {

        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then();
    }
}