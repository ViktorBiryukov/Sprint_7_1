package scooter.Order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import scooter.courier.CourierCredentials;

import static io.restassured.RestAssured.given;

public class OrderClient extends CourierCredentials.ScooterRestClient {
    private static final String ORDER_PATH = "api/v1/orders/";

    @Step("Creating new order with {order}")
    public Response createOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Request a list of orders")
    public ValidatableResponse getOrdersList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then()
                .log().all();
    }
}
