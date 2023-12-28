package com.spottoto.bet.betround.concretes.requests.concretes;

import com.spottoto.bet.betround.concretes.requests.PlayableRequest;
import com.spottoto.bet.betround.enums.BetRole;
import lombok.Data;

import java.util.List;

@Data
public class BetRoundRequest extends PlayableRequest {
    private List<FootballGameRequest> gameList;
    private BetRole betRole;
    private Long serverBetRoundId;
}
