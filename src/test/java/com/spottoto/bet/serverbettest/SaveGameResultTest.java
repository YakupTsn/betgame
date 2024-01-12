package com.spottoto.bet.serverbettest;

import com.spottoto.bet.betround.concretes.BetRound;
import com.spottoto.bet.betround.enums.Score;
import com.spottoto.bet.betround.services.BetRoundService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class SaveGameResultTest {
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    private BetRoundService betRoundService;

    @BeforeAll
    public static void beforeAll() {
        Locale.setDefault(Locale.ENGLISH);
    }

    String pathSaveGameResult = "/betround";

    public String getToken() { return tokenManager.generateToken("admin@admin.gmail.com");}

    public String getTokenUser() {
        return tokenManager.generateToken("user@user.gmail.com");
    }
    public List<String> getGameIdList(Long id){
        BetRound betRound = betRoundService.findOneBetRoundByServerBetRoundId(id);
        List<String> gameIdList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            gameIdList.add(betRound.getGameList().get(i).getId());
        }
       return gameIdList;
    }
    @Test
    void saveGameResult201Test() {
        for (int i = 0; i < 13; i++) {
            RestAssured.given().baseUri("http://localhost:8080")
                    .header("Authorization", "Bearer " + getToken())
                    .contentType(ContentType.JSON)
                    .queryParams("score", Score.FIRST, "serverBetRoundId", 6L, "gameId", getGameIdList(6L).get(i)).when()
                    .patch(pathSaveGameResult).then()
                    .assertThat().statusCode(201);
        }
    }
    @Test
    void saveGameResult400Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .queryParams("score", "1", "serverBetRoundId", 4L,"gameId","e5454713-c779-479b-b630-7b2f52cef62a").when()
                .patch(pathSaveGameResult).then()
                .assertThat().statusCode(400);
    }
    @Test
    void saveGameResult401Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .queryParams("score", Score.FIRST, "serverBetRoundId", 4L,"gameId","e5454713-c779-479b-b630-7b2f52cef62a").when()
                .patch(pathSaveGameResult).then()
                .assertThat().statusCode(401);
    }
    @Test
    void saveGameResult403Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getTokenUser())
                .contentType(ContentType.JSON)
                .queryParams("score", Score.FIRST, "serverBetRoundId", 4L,"gameId","e5454713-c779-479b-b630-7b2f52cef62a").when()
                .patch(pathSaveGameResult).then()
                .assertThat().statusCode(403);
    }
    @Test
    void saveGameResult404Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .queryParams("score", Score.FIRST, "serverBetRoundId", 4L,"gameId","any").when()
                .patch(pathSaveGameResult).then()
                .assertThat().statusCode(404);
    }
}
