package com.spottoto.bet.user.service;

import com.spottoto.bet.betround.concretes.BetRound;
import com.spottoto.bet.betround.concretes.BetRoundDto;
import com.spottoto.bet.betround.services.BetRoundService;
import com.spottoto.bet.exceptions.NotFoundUserException;
import com.spottoto.bet.security.SecurityContextUtil;
import com.spottoto.bet.user.entity.User;
import com.spottoto.bet.user.entity.UserDto;
import com.spottoto.bet.user.entity.UserRole;
import com.spottoto.bet.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SecurityContextUtil securityContextUtil;
    private final BetRoundService betRoundService;


    @Override
    public void changePassword(String newPassword) {
        User user = securityContextUtil.getCurrentUser();
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public void changedRole(String userId, UserRole userRole) {
        User user = findById(userId);
        user.setUserRole(userRole);
        userRepository.save(user);
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        Pageable allPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().isEmpty() ? Sort.by("createDt").descending() : pageable.getSort());
        Page<User> pageUser = userRepository.findAll(allPageable);
        return pageUser.map((user) -> modelMapper.map(user, UserDto.class));
    }

    @Override
    public Page<BetRoundDto> getAllUserBets(Pageable pageable) {
        Pageable allPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().isEmpty() ? Sort.by("id").descending() : pageable.getSort());
        Page<BetRound> userBetRounds = betRoundService.findByUserBets(securityContextUtil.getCurrentUser().getId(), allPageable);
        return userBetRounds.map((betRound) -> modelMapper.map(betRound, BetRoundDto.class));
    }


    public User findById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundUserException(userId + " user not found!"));
    }

}
