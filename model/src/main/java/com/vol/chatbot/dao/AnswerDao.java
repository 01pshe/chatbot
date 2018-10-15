package com.vol.chatbot.dao;

import com.vol.chatbot.model.Answer;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface AnswerDao extends JpaRepository<Answer, Long> {

    List<Answer> findAllByUser(User user);

    List<Answer> findAllByUserAndQuestionAndDayAnswer(User user, Question question,Integer day);

    List<Answer> findAllByUserAndUserAnswerAndDayAnswer(User user,String userAnswer, Integer day);

    default List<Question> findQuestionAllByUserAndDayAnswer(User user,Integer day) {
        return findAllByUser(user).stream()
            .filter(answer -> answer.getDayAnswer().equals(day))
            .map(Answer::getQuestion)
            .collect(Collectors.toList());
    }

    Stream<Answer> findAllByUserAndDayAnswer(User user, Integer day);

}
