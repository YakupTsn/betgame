package com.spottoto.bet.mail;

public interface MailService {
    String sendMailPassword(String password, String mail);

    void couponResult(String mail, String result);
}
