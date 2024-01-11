package com.spottoto.bet.usercontroller;

import com.spottoto.bet.security.TokenManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class GetAllUserBetsTest {
    @Autowired
    private TokenManager tokenManager;

    @BeforeAll
    public static void beforeAll() {
        Locale.setDefault(Locale.ENGLISH);
    }

    String pathGetAllUserBets = "/users/betrounds";

    public String getToken() {
        return tokenManager.generateToken("admin@admin.gmail.com");
    }
    @Test
    void getAllUserBets200Test() {
        ValidatableResponse then = RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .get(pathGetAllUserBets)
                .then().statusCode(200);
    }

    @Test
    void getAllUserBets401Test() {
        ValidatableResponse then = RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .get(pathGetAllUserBets)
                .then().statusCode(401);
    }
}
