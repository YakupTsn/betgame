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
public class GetAllUsersTest {
    @Autowired
    private TokenManager tokenManager;

    @BeforeAll
    public static void beforeAll() {
        Locale.setDefault(Locale.ENGLISH);
    }

    String pathGetAllUsers = "/users";

    public String getToken() {
        return tokenManager.generateToken("admin@admin.gmail.com");
    }
    public String getTokenUser() {
        return tokenManager.generateToken("user@user.gmail.com");
    }

    @Test
    void getAllUsers200Test() {
        ValidatableResponse then = RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .get(pathGetAllUsers)
                .then().statusCode(200);
    }

    @Test
    void getAllUsers401Test() {
        ValidatableResponse then = RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .get(pathGetAllUsers)
                .then().statusCode(401);
    }
    @Test
    void getAllUsers403Test() {
        ValidatableResponse then = RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getTokenUser())
                .contentType(ContentType.JSON)
                .get(pathGetAllUsers)
                .then().statusCode(403);
    }
}
