package com.vol.chatbot.dao;

import com.vol.chatbot.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionDao extends JpaRepository<Question, Long> {

}
