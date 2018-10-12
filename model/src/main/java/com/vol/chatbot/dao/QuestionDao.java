package com.vol.chatbot.dao;

import com.vol.chatbot.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionDao extends JpaRepository<Question, Long> {

    List<Question> findQuestionByUseDay(Integer useDay);
}
