package com.spottoto.bet.serverbettest;

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
public class SaveBetRoundIsSuccessAndSendMailTest {
    @Autowired
    private TokenManager tokenManager;


    @BeforeAll
    public static void beforeAll() {
        Locale.setDefault(Locale.ENGLISH);
    }

    String pathSaveBetRoundIsSuccessAndSendMail = "/betround/finalizes";

    public String getToken() { return tokenManager.generateToken("admin@admin.gmail.com");}

    public String getTokenUser() {
        return tokenManager.generateToken("user@user.gmail.com");
    }

    //betRoundId equals to serverBetRoundId

    @Test
    void saveGameResult400Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .queryParam( "serverBetRoundId", 5L,"gameId").when()
                .patch(pathSaveBetRoundIsSuccessAndSendMail).then()
                .assertThat().statusCode(400);
    }
    @Test
    void saveGameResult201Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .queryParam( "betRoundId", 6L).when()
                .patch(pathSaveBetRoundIsSuccessAndSendMail).then()
                .assertThat().statusCode(201);
    }
    @Test
    void saveGameResult401Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .queryParam( "betRoundId", 5L).when()
                .patch(pathSaveBetRoundIsSuccessAndSendMail).then()
                .assertThat().statusCode(401);
    }
    @Test
    void saveGameResult403Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getTokenUser())
                .contentType(ContentType.JSON)
                .queryParam( "betRoundId", 5L).when()
                .patch(pathSaveBetRoundIsSuccessAndSendMail).then()
                .assertThat().statusCode(403);
    }
    @Test
    void saveGameResult404Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .queryParam( "betRoundId", 1L).when()
                .patch(pathSaveBetRoundIsSuccessAndSendMail).then()
                .assertThat().statusCode(404);
    }
}
