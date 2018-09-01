package com.vol.chatbot.dao;

import com.vol.chatbot.model.Message;
import com.vol.chatbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MessageDao extends JpaRepository<Message,Long> {
    Set<Message> findAllByUserSignature(String signature);

    Long countByUser(User user);
}
