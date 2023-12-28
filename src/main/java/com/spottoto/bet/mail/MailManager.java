package com.spottoto.bet.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailManager implements MailService {
    private JavaMailSender mailSender;
    @Autowired
    public MailManager(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public String sendMailPassword(String password, String mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("supertoto@yutsoft.com");
        message.setTo(mail);
        message.setText("New password: " + password);
        message.setSubject("Did you forget your password?");
        mailSender.send(message);
        return "New password has been sent to your mail.";
    }

    @Override
    public void couponResult(String mail, String result) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("supertoto@yutsoft.com");
        message.setTo(mail);
        message.setText(result);
        message.setSubject("Aren't you curious about the coupon result?");
        mailSender.send(message);
    }
}
