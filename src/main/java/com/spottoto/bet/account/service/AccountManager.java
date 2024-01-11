package com.spottoto.bet.account.service;


import com.spottoto.bet.account.controller.request.LoginRequest;
import com.spottoto.bet.account.controller.request.RegisterRequest;
import com.spottoto.bet.exceptions.NotFoundMailException;
import com.spottoto.bet.exceptions.UnAuthorizedException;
import com.spottoto.bet.exceptions.UserMailExistsException;
import com.spottoto.bet.mail.MailService;
import com.spottoto.bet.mapper.ModelMapperManager;
import com.spottoto.bet.security.SecurityContextUtil;
import com.spottoto.bet.security.TokenManager;
import com.spottoto.bet.user.entity.User;
import com.spottoto.bet.user.entity.UserDto;
import com.spottoto.bet.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

import static com.spottoto.bet.account.controller.EmailValidation.patternMatches;


@Service
@RequiredArgsConstructor
public class AccountManager implements AccountService {
    private final UserRepository userRepository;
    private final TokenManager tokenManager;
    private final ModelMapperManager modelMapperManager;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextUtil securityContextUtil;
    private final MailService mailService;

    @Override
    public UserDto register(RegisterRequest request) {
        patternMatches(request.getMail());
        boolean requestedUserNameisExist = this.userRepository.existsByMail(request.getMail());
        User user = new User();
        if (requestedUserNameisExist) {
            throw new UserMailExistsException("This mailing address already exists");
        }
        user = user.create(request);
        userRepository.save(user.create(request));
        return modelMapperManager.forResponse().map(user, UserDto.class);
    }

    @Override
    public ResponseEntity<String> login(LoginRequest request) {
        requestControl(request);
        patternMatches(request.getMail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getMail(),
                        request.getPassword()));
        return ResponseEntity.ok(tokenManager.generateToken(request.getMail()));
    }

    private void requestControl(LoginRequest request) {
        if(request.getMail().isEmpty() && request.getPassword().isEmpty())
            throw new UnAuthorizedException("Email and password cannot be left blank");
    }

    @Override
    public UserDto getMe() {
        User user = securityContextUtil.getCurrentUser();
        return modelMapperManager.forResponse().map(user, UserDto.class);
    }

    @Override
    public String putPassword(String mail) {
        User user = userRepository.findByMail(mail).orElseThrow(
                () -> new NotFoundMailException(mail + " This mailing address does not exist")
        );
        Random randomPassword = new Random();
        String newPassword = randomPassword.nextDouble(10) + "";
        newPassword = newPassword.replace(".", "1");
        user.setPassword(newPassword);
        user.setUpdateDt(LocalDateTime.now());
        userRepository.save(user);
        return mailService.sendMailPassword(user.getPassword(), user.getMail());
       // return "Ok";
    }

}
