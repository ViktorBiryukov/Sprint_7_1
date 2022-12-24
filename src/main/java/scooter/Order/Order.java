package scooter.Order;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    public List<String> color;
    public String address;
    public String firstName;
    public String lastName;
    public String deliveryDate;
    public String metroStation;
    public String phone;
    public int rentTime;
    public String comment;

    public Order(List<String> color) {
        this.color = color;
        this.address = "ул. Кораблестроителей 19";
        this.firstName = "Тест";
        this.lastName = "Тестовый";
        this.deliveryDate = LocalDateTime.now().toString();
        this.metroStation = "2";
        this.phone = "89991111111";
        this.rentTime = 2;
        this.comment = "Тест";
    }
}
