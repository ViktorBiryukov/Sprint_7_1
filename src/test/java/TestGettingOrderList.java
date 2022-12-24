import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Test;
import scooter.Order.OrderClient;

import static org.hamcrest.CoreMatchers.notNullValue;

public class TestGettingOrderList {

    @Test
    @DisplayName("Проверка получение списка заказов")
    @Description("Получение списка заказов и роверка что список не пустой")
    public void getOrderListTest() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse orderListResponse = orderClient.getOrdersList().statusCode(200);
        orderListResponse.assertThat().body("orders.id", notNullValue());
    }
}