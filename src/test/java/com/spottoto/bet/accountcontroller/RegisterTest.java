package com.spottoto.bet.accountcontroller;

import com.spottoto.bet.account.controller.request.RegisterRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
public class RegisterTest {
    @BeforeAll
    public static void beforeAll() {
        Locale.setDefault(Locale.ENGLISH);
    }
    String pathRegister = "/account/register";
    public RegisterRequest getReguest() {
        return RegisterRequest.builder()
                .firstname("user first")
                .lastname("user last")
                .mail("user@user.gmail.com")
                .password("12345")
                .build();
    }

    @Test
    void register201Test(){
         RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .body(getReguest()).post(pathRegister)
                .then().statusCode(201);
    }
    @Test
    void register400Test(){
        RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .body("").post(pathRegister)
                .then().statusCode(400);
    }
}
