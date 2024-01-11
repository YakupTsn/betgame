package com.spottoto.bet.betround.concretes.requests.concretes;

import com.spottoto.bet.betround.concretes.requests.PlayableRequest;
import com.spottoto.bet.betround.enums.BetRole;
import com.spottoto.bet.exceptions.GeneralException;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BetRoundRequest extends PlayableRequest {
    private List<FootballGameRequest> gameList;
    private BetRole betRole;
    private Long serverBetRoundId;

    public void checkBetRoundListSize() {
        if(gameList.size()!=13){
            throw new GeneralException("You must add 13 bet");
        }
    }


}
