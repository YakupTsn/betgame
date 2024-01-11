package com.spottoto.bet.account.controller.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String mail;
    private String password;
}
