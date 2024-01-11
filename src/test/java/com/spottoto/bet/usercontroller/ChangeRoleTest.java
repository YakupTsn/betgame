package com.spottoto.bet.usercontroller;

import com.spottoto.bet.security.TokenManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
public class ChangeRoleTest {
    @Autowired
    private TokenManager tokenManager;
    @BeforeAll
    public static void beforeAll() {
        Locale.setDefault(Locale.ENGLISH);
    }
    String pathChangeRole = "/users/change/role";
    public String getToken() {
        return tokenManager.generateToken("admin@admin.gmail.com");
    }
    public String getTokenUser() {
        return tokenManager.generateToken("user@user.gmail.com");
    }
    @Test
    void changeRole201Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .queryParams("userId","ce6eae5d-5d27-42a9-bf75-a13c891695a8","userRole", "ROLE_USER").when()
                .patch(pathChangeRole).then()
                .assertThat().statusCode(201);
    }
    @Test
    void changeRole401Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .queryParams("userId","ce6eae5d-5d27-42a9-bf75-a13c891695a8","userRole", "ROLE_USER").when()
                .patch(pathChangeRole).then()
                .assertThat().statusCode(401);
    }

    @Test
    void changeRole403Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getTokenUser())
                .contentType(ContentType.JSON)
                .queryParams("userId","ce6eae5d-5d27-42a9-bf75-a13c891695a8","userRole", "ROLE_USER").when()
                .patch(pathChangeRole).then()
                .assertThat().statusCode(403);
    }

    @Test
    void changeRole404Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .queryParams("userId","any","userRole", "ROLE_USER").when()
                .patch(pathChangeRole).then()
                .assertThat().statusCode(404);
    }
}
