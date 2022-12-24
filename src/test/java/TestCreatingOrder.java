import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import scooter.Order.Order;
import scooter.Order.OrderClient;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class TestCreatingOrder {

    private final List<String> color;
    private final Matcher<Object> expected;

    public TestCreatingOrder(List<String> color, Matcher<Object> expected) {
        this.color = color;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] getColorData() {
        return new Object[][]{
                {List.of("BLACK", "GREY"), notNullValue()},
                {List.of("BLACK"), notNullValue()},
                {List.of("GREY"), notNullValue()},
                {null, notNullValue()}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с параметрами цвета")
    public void CreatingOrderTest() {
        OrderClient orderClient = new OrderClient();
        Order order = new Order(color);
        Response createOrderResponse = orderClient.createOrder(order);
        createOrderResponse.then().assertThat().statusCode(201)
                .and()
                .body("track", expected);
    }
}