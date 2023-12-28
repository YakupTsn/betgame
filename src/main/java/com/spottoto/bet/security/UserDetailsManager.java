package com.spottoto.bet.security;


import com.spottoto.bet.exceptions.UserNotFoundException;
import com.spottoto.bet.user.entity.User;
import com.spottoto.bet.user.entity.UserRole;
import com.spottoto.bet.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsManager implements UserDetailsService {

    private  final UserRepository userRepository;

    @PostConstruct
    public void init(){
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()) {
            User admin = new User();
            admin.setMail("admin@admin.gmail.com");
            admin.setUserRole(UserRole.ROLE_ADMIN);
            admin.setPassword("12345");
            userRepository.save(admin);
            }
        }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByMail(mail);
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException("Not found user."));
        return new UserCustomDetails(user);
    }
}
