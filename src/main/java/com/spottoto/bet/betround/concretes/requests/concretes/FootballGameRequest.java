package com.spottoto.bet.betround.concretes.requests.concretes;


import com.spottoto.bet.betround.concretes.requests.GameRequestAbstract;
import com.spottoto.bet.betround.enums.Score;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class FootballGameRequest extends GameRequestAbstract {
    private String serverId;
    private String firstTeamName;
    private String secondTeamName;
    private Score score;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime playDate;

}
