package com.spottoto.bet.security;


import com.spottoto.bet.exceptions.UnAuthorizedException;
import com.spottoto.bet.user.entity.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextUtil {


    public User getCurrentUser(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication.getPrincipal() instanceof AnonymousAuthenticationToken) {
            throw new UnAuthorizedException("please log in");
        }
        UserCustomDetails userDetails = (UserCustomDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
