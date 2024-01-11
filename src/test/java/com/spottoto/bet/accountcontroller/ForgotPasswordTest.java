package com.spottoto.bet.accountcontroller;

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
public class ForgotPasswordTest {
    @BeforeAll
    public static void beforeAll() {
        Locale.setDefault(Locale.ENGLISH);
    }

    String pathForgotPassword = "/account/forgotpassword";

    @Test
    void putPassword200Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .queryParam("mail", "user@user.gmail.com").when()
                .put(pathForgotPassword).then()
                .assertThat().statusCode(200);
    }

    @Test
    void putPassword404Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .param("mail", "any@user.gmail.com")
                .put(pathForgotPassword)
                .then().statusCode(404);

    }
}
