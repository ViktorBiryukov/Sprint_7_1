package scooter.courier;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class CourierCredentials {

    private String login;
    private String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public CourierCredentials() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public static class ScooterRestClient {

        public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";

        public RequestSpecification getBaseSpec() {
            return new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setBaseUri(BASE_URL)
                    .build();
        }
    }
}