package com.spottoto.bet.account.controller.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private String mail;
    private String password;

}
