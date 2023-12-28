package com.spottoto.bet.betround.concretes.requests;

import com.spottoto.bet.betround.enums.Score;
import lombok.Data;

import java.util.List;

@Data
public class GameResult {
    private List<Score> scoreList;
    private Long serverBetRoundId;
}
