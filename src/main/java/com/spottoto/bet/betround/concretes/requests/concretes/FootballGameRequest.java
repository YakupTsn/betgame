package com.spottoto.bet.betround.concretes.requests.concretes;


import com.spottoto.bet.betround.concretes.requests.GameRequestAbstract;
import com.spottoto.bet.betround.enums.Score;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FootballGameRequest extends GameRequestAbstract {
    private String serverId;
    private String firstTeamName;
    private String secondTeamName;
    private Score score;
    private LocalDateTime playDate;

}
