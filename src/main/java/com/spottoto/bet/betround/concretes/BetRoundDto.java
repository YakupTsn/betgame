package com.spottoto.bet.betround.concretes;

import com.spottoto.bet.betround.enums.BetRole;
import com.spottoto.bet.betround.enums.BetStatus;
import com.spottoto.bet.betround.enums.PlayableStatus;
import lombok.Data;

import java.util.List;

@Data
public class BetRoundDto {
    private Long id;
    private Long serverBetRoundId;
    private String title;
    private PlayableStatus playableStatus;
    private BetRole betRole;
    private String ownerId;
    private BetStatus betStatus;
    private List<FootballGame> gameList;
}
