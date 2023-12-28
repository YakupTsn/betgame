package com.spottoto.bet.betround.concretes.requests.concretes;

import com.spottoto.bet.betround.enums.Score;
import lombok.Data;

import java.util.List;

@Data
public class GameResult {
    private List<Score> gameResultList;
    private Long serverBetRoundId;
}
