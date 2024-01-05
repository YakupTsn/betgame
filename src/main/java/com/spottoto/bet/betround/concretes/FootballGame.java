package com.spottoto.bet.betround.concretes;

import com.spottoto.bet.betround.abstracts.GameAbstract;
import com.spottoto.bet.betround.concretes.requests.concretes.FootballGameRequest;
import com.spottoto.bet.betround.enums.BetRole;
import com.spottoto.bet.betround.enums.Score;
import com.spottoto.bet.exceptions.NotFoundException;
import com.spottoto.bet.exceptions.RestException;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
public class FootballGame extends GameAbstract {
    private String serverId;
    private String id = UUID.randomUUID().toString();
    private String firstTeamName;
    private String secondTeamName;
    private Boolean isSuccess;
    private Score score;

    public List<FootballGame> createList(List<FootballGameRequest> gameList, BetRole betRole) {
        List<FootballGame> footballGameList = new ArrayList<>();
        for (FootballGameRequest footballGameRequest : gameList) {
            FootballGame footballGame = new FootballGame();
            footballGame.setServerId(footballGameRequest.getServerId());
            footballGame.setFirstTeamName(footballGameRequest.getFirstTeamName());
            footballGame.setSecondTeamName(footballGameRequest.getSecondTeamName());
            footballGame.setScore(footballGameRequest.getScore());
            footballGame.setIsSuccess(footballGameRequest.getIsSuccess());
            footballGame.setPlayDate(footballGameRequest.getPlayDate());
            footballGame.validate(betRole);
            footballGameList.add(footballGame);
        }
        return footballGameList;
    }

    public List<FootballGame> updateList(Score score, String gameId, List<FootballGame> gameList) {
        findById(gameList, gameId);
        for (FootballGame footballGame : gameList) {
            if (footballGame.getId().equals(gameId)) {
                footballGame.setScore(score);
            }
        }
        return gameList;
    }

    FootballGame findById(List<FootballGame> gameList, String gameId) {
        Optional<FootballGame> gameOptional = gameList.stream().filter(game -> game.getId().equals(gameId)).findFirst();
        if (gameOptional.isEmpty())
            throw new NotFoundException(" Game not found");
        return gameOptional.get();
    }

    public List<FootballGame> updateListIsSuccess(List<FootballGame> betroundGameList, List<FootballGame> userBetRoundGameList) {
        for (FootballGame footballGameUserBetRound : userBetRoundGameList) {
            for (FootballGame footballGameBetRound : betroundGameList) {
                checkGameSummary(footballGameBetRound, footballGameUserBetRound);
            }
        }
        return userBetRoundGameList;
    }

    private void checkGameSummary(FootballGame serverGame, FootballGame userGame) {
        if (serverGame.getId().equals(userGame.getServerId())) {
            if (serverGame.getScore().equals(userGame.getScore())) {
                userGame.setIsSuccess(true);
            } else {
                userGame.setIsSuccess(false);
            }
        }
    }


    @Override
    protected void validate(BetRole betRole) {
        checkPlayDate();
        checkTeams();
        if (betRole.equals(BetRole.USER)) {
            checkGameServerId();
        }
    }

    private void checkGameServerId() {
        if (this.serverId.isEmpty()) {
            throw new NotFoundException("The bet to be placed is not registered in the system");
        }
    }


    private void checkTeams() {
        if (this.getFirstTeamName().isEmpty() || this.getSecondTeamName().isEmpty())
            throw new RestException("Any team cannot be left vacant.");
    }

    private void checkPlayDate() {
        if (this.playDate.isBefore(LocalDateTime.now()))
            throw new RestException("A bet from a previous date cannot be added.");
    }

    @Override
    protected Boolean isSuccess() {
        return isSuccess;
    }


}
