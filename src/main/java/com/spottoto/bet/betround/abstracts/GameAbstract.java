package com.spottoto.bet.betround.abstracts;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public abstract class GameAbstract {

    protected LocalDateTime playDate;
    protected  abstract void  validate();

    protected abstract Boolean isSuccess() ;
}
