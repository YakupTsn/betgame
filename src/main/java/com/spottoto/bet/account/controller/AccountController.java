package com.spottoto.bet.account.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.spottoto.bet.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spottoto.bet.user.entity.UserDto;
import com.spottoto.bet.account.controller.request.RegisterRequest;
import com.spottoto.bet.account.controller.request.LoginRequest;
import com.spottoto.bet.security.annotations.*;
import com.spottoto.bet.exceptions.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @Operation(
            summary = "CREATE an user entity.",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public UserDto register(@RequestBody RegisterRequest request) {
        return accountService.register(request);
    }

    @PostMapping("/login")
    @Operation(
            description = "login to system with mail and password.",
            summary = "User login.",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(description = "UnAuthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Forbidden", responseCode = "403", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        return accountService.login(request);
    }

    @IsAuthentificated
    @Operation(
            summary = "Who Am I ?",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(description = "UnAuthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            })
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getMe() {
        return accountService.getMe();
    }

    @Operation(
            summary = "Forgot your password?",
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(description = "UnAuthorized", responseCode = "401", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            })
    @PutMapping("/forgotpassword")
    @ResponseStatus(HttpStatus.OK)
    public String putPassword(@RequestParam String mail) {
        return accountService.putPassword(mail);
    }

}
