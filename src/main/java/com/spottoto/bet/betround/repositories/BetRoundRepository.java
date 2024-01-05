package com.spottoto.bet.betround.repositories;

import com.spottoto.bet.betround.concretes.BetRound;
import com.spottoto.bet.betround.enums.BetRole;
import com.spottoto.bet.betround.enums.PlayableStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@EnableJpaRepositories
public interface BetRoundRepository extends JpaRepository<BetRound, Long> {

    List<BetRound> findByServerBetRoundIdAndBetRole(Long serverBetRoundId, BetRole betRole);

    Page<BetRound> findByPlayableStatusAndBetRole(PlayableStatus playableStatus, BetRole betRole, Pageable allPageable);

    Page<BetRound> findByBetRole(BetRole betRole, Pageable allPageable);

    Page<BetRound> findByOwnerId(String id, Pageable pageable);
}
