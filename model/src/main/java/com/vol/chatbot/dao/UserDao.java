package com.vol.chatbot.dao;

import com.vol.chatbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface UserDao extends JpaRepository<User, Long> {
    User findBySignature(String signature);

    Set<User> getAllByIdNotNull();

    User getById(Long id);

    @Query("select id from User")
    Set<Long> getAllUserIds();
}
