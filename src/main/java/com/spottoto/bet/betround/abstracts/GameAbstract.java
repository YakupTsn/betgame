package com.spottoto.bet.betround.abstracts;

import com.spottoto.bet.betround.enums.BetRole;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public abstract class GameAbstract {

    protected LocalDateTime playDate;
    protected  abstract void  validate(BetRole betRole);

    protected abstract Boolean isSuccess() ;
}
