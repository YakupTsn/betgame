package com.spottoto.bet.user.repository;

import com.spottoto.bet.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByMail(String mail);

    boolean existsByMail(String mail);
}
