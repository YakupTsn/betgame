package com.spottoto.bet.account.service;


import com.spottoto.bet.account.controller.request.LoginRequest;
import com.spottoto.bet.account.controller.request.RegisterRequest;
import com.spottoto.bet.user.entity.UserDto;
import org.springframework.http.ResponseEntity;

public interface AccountService {
    UserDto register(RegisterRequest request);

    ResponseEntity<String> login(LoginRequest request);

    UserDto getMe();

    String putPassword(String mail);
}
