package com.spottoto.bet.betround.interfaces;



public interface Playable {

    public <T> T playBet(Object o);

    public Boolean isSuccess();
}
