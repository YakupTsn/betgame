package com.spottoto.bet.user.service;

import com.spottoto.bet.betround.concretes.BetRoundDto;
import com.spottoto.bet.user.entity.UserDto;
import com.spottoto.bet.user.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    void changePassword(String newPassword);

    void changedRole(String userId, UserRole userRole);

    Page<UserDto> getAllUsers(Pageable pageable);

    Page<BetRoundDto> getAllUserBets(Pageable pageable);
}
