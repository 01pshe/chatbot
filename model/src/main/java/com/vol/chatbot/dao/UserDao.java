package com.vol.chatbot.dao;

import com.vol.chatbot.model.impl.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserDao extends JpaRepository<User, Long> {
    User findBySignature(String signature);

    Set<User> getAllByIdNotNull();

    User getById(Long id);
}
