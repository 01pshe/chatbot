package com.vol.chatbot.dao;

import com.vol.chatbot.model.Answer;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.model.impl.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerDao extends JpaRepository<Answer, Long> {

    List<Answer> findAllByUser(User user);

    List<Answer> findAllByUserAndQuestion(User user, Question question);

}
