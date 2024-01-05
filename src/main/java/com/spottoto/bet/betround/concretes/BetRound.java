package com.spottoto.bet.betround.concretes;

import com.spottoto.bet.betround.concretes.requests.concretes.BetRoundRequest;
import com.spottoto.bet.betround.concretes.requests.concretes.FootballGameRequest;
import com.spottoto.bet.betround.enums.BetRole;
import com.spottoto.bet.betround.enums.BetStatus;
import com.spottoto.bet.betround.enums.PlayableStatus;
import com.spottoto.bet.betround.enums.Score;
import com.spottoto.bet.betround.interfaces.Playable;
import com.spottoto.bet.exceptions.RestException;
import com.spottoto.bet.user.entity.User;
import com.spottoto.bet.user.entity.UserRole;
import io.hypersistence.utils.hibernate.type.json.JsonBlobType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class BetRound implements Playable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Long serverBetRoundId;
    @Column
    private String title;
    @Column
    @Enumerated(EnumType.STRING)
    private PlayableStatus playableStatus = PlayableStatus.PLANNED;
    @Column
    @Enumerated(EnumType.STRING)
    private BetRole betRole;
    @Column
    private String ownerId;
    @Column
    @Enumerated(EnumType.STRING)
    private BetStatus betStatus = BetStatus.WAITING;
    @Column(length = 1000000)
    @Type(JsonBlobType.class)
    private List<FootballGame> gameList;



    public void checkGamesDatesToChangePlayableStatus(PlayableStatus playableStatus) {
        Boolean isInvalidGameDate = gameList.stream().anyMatch(game -> game.getPlayDate().isAfter(LocalDateTime.now()));
        if (playableStatus.equals(PlayableStatus.ENDED)) {
            if (isInvalidGameDate)
                throw new RestException("There are still games being played");
        } else {
            isInvalidGameDate = gameList.stream().anyMatch(game -> game.getPlayDate().isBefore(LocalDateTime.now()));
            if (isInvalidGameDate)
                throw new RestException("Planned games should not be out of date");
        }
    }

    @Override
    public Boolean isSuccess() {
        if (betStatus.equals(BetStatus.FAILED)) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    @Override
    public BetRound playBet(Object o) {
        return null;

    }

    public BetRound create(User user, BetRoundRequest betRoundRequest) {
        checkBets(user, betRoundRequest);
        BetRound betRound = new BetRound();
        betRound.betRole = betRoundRequest.getBetRole();
        betRound.playableStatus = PlayableStatus.PLANNED;
        betRound.title = betRoundRequest.getTitle();
        betRound.ownerId = user.getId();
        betRound.gameList = new FootballGame().createList(betRoundRequest.getGameList(), betRoundRequest.getBetRole());
        betRound.serverBetRoundId = setServer(betRoundRequest);
        return betRound;
    }

    public BetRound update(Score score, String gameId) {
        playableStatus = PlayableStatus.ENDED;
        checkGamesDatesToChangePlayableStatus(playableStatus);
        gameList = new FootballGame().updateList(score, gameId, gameList);
        return this;
    }

    public List<BetRound> updateIsSuccess(BetRound betRound, List<BetRound> userBetRoundList) {
        for (BetRound userBetRound : userBetRoundList) {
            userBetRound.setPlayableStatus(PlayableStatus.ENDED);
            gameList = new FootballGame().updateListIsSuccess(betRound.gameList, userBetRound.gameList);
        }
        return userBetRoundList;
    }

    private Long setServer(BetRoundRequest betRoundRequest) {
        if (betRoundRequest.getServerBetRoundId() != null) {
            return betRoundRequest.getServerBetRoundId();
        } else
            return null;
    }

    private void checkBets(User user, BetRoundRequest betRoundRequest) {
        checkBetRoleForUser(user.getUserRole(), betRoundRequest.getBetRole());
        checkGamesForBetRole(betRoundRequest.getGameList(), betRoundRequest.getBetRole());
    }

    private void checkGamesForBetRole(List<FootballGameRequest> gameList, BetRole betRole) {
        if (betRole.equals(BetRole.SERVER) && gameList.stream().anyMatch(game -> game.getScore() != null)) {
            throw new RestException("Bets cannot be settled with results.");
        } else if (betRole.equals(BetRole.USER) && gameList.stream().anyMatch(game -> game.getScore() == null)) {
            throw new RestException("Bets cannot be left empty");
        }
    }

    private void checkBetRoleForUser(UserRole userRole, BetRole betRole) {
        if (betRole.equals(BetRole.SERVER) && userRole.equals(UserRole.ROLE_USER)) {
            throw new RestException("Wrong BetRole");
        }
    }

    public void updateBetStatus() {
        if (betRole.equals(BetRole.SERVER))
            betStatus = BetStatus.FINISH;
        Boolean status = gameList.stream().anyMatch(game -> game.isSuccess().equals(Boolean.FALSE));
        if (status) {
            betStatus = BetStatus.FAILED;
            this.isSuccess();
        } else {
            betStatus = BetStatus.SUCCESS;
            this.isSuccess();
        }

    }
}
