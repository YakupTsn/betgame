package com.spottoto.bet.betround.services;

import com.spottoto.bet.betround.concretes.BetRound;
import com.spottoto.bet.betround.concretes.BetRoundDto;
import com.spottoto.bet.betround.concretes.requests.concretes.BetRoundRequest;
import com.spottoto.bet.betround.enums.BetRole;
import com.spottoto.bet.betround.enums.PlayableStatus;
import com.spottoto.bet.betround.enums.Score;
import com.spottoto.bet.betround.repositories.BetRoundRepository;
import com.spottoto.bet.exceptions.NotFoundException;
import com.spottoto.bet.exceptions.RestException;
import com.spottoto.bet.exceptions.UserNotFoundException;
import com.spottoto.bet.mail.MailManager;
import com.spottoto.bet.security.SecurityContextUtil;
import com.spottoto.bet.user.entity.User;
import com.spottoto.bet.user.repository.UserRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BetRoundService {
    private final SecurityContextUtil securityContextUtil;
    private final BetRoundRepository betRoundRepository;
    private final UserRepository userRepository;
    private final MailManager mailManager;
    private final ModelMapper modelMapper;

    public void saveBetRound(BetRoundRequest betRoundRequest) {
        User user = securityContextUtil.getCurrentUser();
        betRoundRequest.checkBetRoundListSize();
        checkServerBetRound(betRoundRequest);
        BetRound betRound = new BetRound().create(user, betRoundRequest);
        betRoundRepository.save(betRound);
    }


    private void checkServerBetRound(BetRoundRequest betRoundRequest) {
        List<BetRound> betRound = betRoundRepository.findByServerBetRoundIdAndBetRole(betRoundRequest.getServerBetRoundId(), BetRole.SERVER);
        if (betRoundRequest.getBetRole().equals(BetRole.USER)) {
            if (betRound.isEmpty())
                throw new RestException("The bet to be placed is not registered in the system");
            if (betRound.get(0).getPlayableStatus().equals(PlayableStatus.ENDED))
                throw new RestException("Bets Are Off");
        }else {
            findByBetRoundServer(betRound, betRoundRequest.getServerBetRoundId());
        }
    }
    private void findByBetRoundServer(List<BetRound> betRound, Long serverBetRoundId){
       Optional<BetRound> betRoundOptional = betRound.stream().filter(cupon -> cupon.getServerBetRoundId().equals(serverBetRoundId)).findFirst();
        if(!betRoundOptional.isEmpty()){
            throw new RestException(serverBetRoundId+" bet round server available");
        }
    }
    public BetRound findOneBetRoundByServerBetRoundId(Long serverBetRoundId) {
        List<BetRound> betRound = betRoundRepository.findByServerBetRoundIdAndBetRole(serverBetRoundId, BetRole.SERVER);
        if (betRound.isEmpty())
            throw new NotFoundException("Bet round id : "+ serverBetRoundId + " is not found");
        return betRound.get(0);
    }

    private List<BetRound> findBetRoundsByServerBetRoundId(Long serverBetRoundId) {
        return betRoundRepository.findByServerBetRoundIdAndBetRole(serverBetRoundId, BetRole.USER);
    }

    public void saveBetRoundIsSuccessAndSendMail(Long betRoundId) {
        BetRound betRound = findOneBetRoundByServerBetRoundId(betRoundId);
        BetRound newBetRound = new BetRound();
        List<BetRound> userBetRoundList = findBetRoundsByServerBetRoundId(betRound.getServerBetRoundId());
        List<BetRound> userBetRoundListNew = newBetRound.updateIsSuccess(betRound, userBetRoundList);
        betRoundRepository.saveAll(userBetRoundListNew);
        betRoundRepository.save(betRound);
         changeBetStatus(betRound);
        isSuccesSendMail(userBetRoundListNew);
    }

    public void changeBetStatus(BetRound betRound) {
        betRound.updateBetStatus();
        betRoundRepository.save(betRound);

    }

    private void isSuccesSendMail(List<BetRound> userBetRoundListGuncel) {
        for (BetRound userBetRound : userBetRoundListGuncel) {
            String mail = findUserByOwnerId(userBetRound);
            changeBetStatus(userBetRound);
            betRoundRepository.save(userBetRound);
            String result = "Your cupon: " + userBetRound.getGameList().toString() + "Cupon Result " + userBetRound.getBetStatus();
            mailManager.couponResult(mail, result);
        }
    }

    private String findUserByOwnerId(BetRound userBetRound) {
        User user = userRepository.findById(userBetRound.getOwnerId()).orElseThrow(()-> new UserNotFoundException("User not found"));
        return user.getMail();
    }


    public Page<BetRoundDto> getAllBetRounds(@Nullable PlayableStatus playableStatus, Pageable pageable) {
        Page<BetRound> pageBetRound;
        Pageable allPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().isEmpty() ? Sort.by("id").descending() : pageable.getSort()
        );
        if (playableStatus == null) {
            pageBetRound = betRoundRepository.findByBetRole(BetRole.SERVER, allPageable);
        } else
            pageBetRound = betRoundRepository.findByPlayableStatusAndBetRole(playableStatus, BetRole.SERVER, allPageable);
        return pageBetRound.map((betRound) -> modelMapper.map(betRound, BetRoundDto.class));
    }

    public Page<BetRound> findByUserBets(String id, Pageable pageable) {
        return betRoundRepository.findByOwnerId(id, pageable);
    }

    public void saveGameResult(Long serverBetRoundId, String gameId, Score score) {
        BetRound betRound = findOneBetRoundByServerBetRoundId(serverBetRoundId);
        betRound.update(score, gameId);
        betRoundRepository.save(betRound);
    }

}
