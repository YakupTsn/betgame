package com.spottoto.bet.account.controller;
import com.spottoto.bet.exceptions.GeneralException;
import java.util.regex.Pattern;

public class EmailValidation {

   static String regexPattern ="^(.+)@(\\S+)$";
    public static void patternMatches(String emailAddress) {
        if(! Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches()){
            throw new GeneralException("Invalid mail");
        }

    }
}