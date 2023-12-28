package com.spottoto.bet.betround.concretes.requests;

import com.spottoto.bet.betround.enums.BetRole;
import lombok.Data;

@Data
public abstract class PlayableRequest {
    private String title;
    private BetRole betRole;

}
