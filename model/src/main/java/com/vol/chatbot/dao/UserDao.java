package com.vol.chatbot.dao;

import com.vol.chatbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserDao extends JpaRepository<User, Long> {
    User findBySignature(String signature);

    Set<User> getAllBySysIdNotNull();

    User findBySysId(Long id);
}
