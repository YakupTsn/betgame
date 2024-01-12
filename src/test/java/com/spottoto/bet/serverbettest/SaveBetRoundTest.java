package com.spottoto.bet.serverbettest;

import com.spottoto.bet.betround.concretes.requests.concretes.BetRoundRequest;
import com.spottoto.bet.betround.concretes.requests.concretes.FootballGameRequest;
import com.spottoto.bet.betround.enums.BetRole;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class SaveBetRoundTest {
    @Autowired
    private TokenManager tokenManager;

    @BeforeAll
    public static void beforeAll() {
        Locale.setDefault(Locale.ENGLISH);
    }

    String pathSaveBetRound = "/betround";

    public static BetRoundRequest getAdminReguest() {
        List<FootballGameRequest> gameList = getAdminGameList();

        return BetRoundRequest.builder()
                .serverBetRoundId(6L)
                .betRole(BetRole.SERVER)
                .gameList(gameList)
                .build();
    }

    public static List<FootballGameRequest> getAdminGameList() {
        List<FootballGameRequest> gameList = new ArrayList<>();
        LocalDateTime date = LocalDateTime.of(2024, 01, 13, 20, 46, 15);
        for (int i = 0; i < 13; i++) {
            FootballGameRequest aw = FootballGameRequest.builder()
                    .firstTeamName("string")
                    .playDate(date)
                    .secondTeamName("string")
                    .serverId("string")
                    .build();
            gameList.add(aw);
        }

        return gameList;
    }

    public String getToken() {
        return tokenManager.generateToken("admin@admin.gmail.com");
    }

    @Test
    void saveBetRound201Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .body(getAdminReguest()).post(pathSaveBetRound)
                .then().statusCode(201);
    }

    @Test
    void saveBetRound400Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .header("Authorization", "Bearer " + getToken())
                .contentType(ContentType.JSON)
                .body("").post(pathSaveBetRound)
                .then().statusCode(400);
    }

    @Test
    void saveBetRound401Test() {
        RestAssured.given().baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .body("").post(pathSaveBetRound)
                .then().statusCode(401);
    }

}
