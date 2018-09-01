package com.vol.chatbot.dao;

import com.vol.chatbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findBySignature(String signature);
}
