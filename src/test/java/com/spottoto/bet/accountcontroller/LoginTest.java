package com.spottoto.bet.accountcontroller;

import com.spottoto.bet.account.controller.request.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class LoginTest {
    @BeforeAll
    public static void beforeAll() {
        Locale.setDefault(Locale.ENGLISH);
    }

    String pathLogin = "/account/login";

    public LoginRequest getReguest() {
        return LoginRequest.builder()
                .mail("admin@admin.gmail.com")
                .password("12345")
                .build();
    }

    @Test
    void login200Test() {
        ValidatableResponse then = RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON).body(getReguest()).post(pathLogin).then().statusCode(200);
    }

    @Test
    void login400Test() {
        LoginRequest reg = getReguest();
        reg.setMail("admin");
        ValidatableResponse then = RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON).body(reg).post(pathLogin).then().statusCode(400);
    }

    @Test
    void login401Test() {
        LoginRequest reg = getReguest();
        reg.setMail("");
        reg.setPassword("");
        ValidatableResponse then = RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON).body(reg).post(pathLogin).then().statusCode(401);
    }

    @Test
    void login404Test() {
        LoginRequest reg = getReguest();
        reg.setMail("ad@admin.gmail.com");
        ValidatableResponse then = RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON).body(reg).post(pathLogin).then().statusCode(404);
    }
}
