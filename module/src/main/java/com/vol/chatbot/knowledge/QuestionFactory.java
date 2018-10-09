package com.vol.chatbot.knowledge;

import com.vol.chatbot.Utils;
import com.vol.chatbot.dao.AnswerDao;
import com.vol.chatbot.dao.QuestionDao;
import com.vol.chatbot.model.Answer;
import com.vol.chatbot.model.Question;
import com.vol.chatbot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionFactory {


    private AnswerDao answerDao;
    private QuestionDao questionDao;
    private List<Question> questions;

    @Autowired
    public QuestionFactory(AnswerDao answerDao, QuestionDao questionDao) {
        this.answerDao = answerDao;
        this.questionDao = questionDao;
        this.questions = questionDao.findAll();
    }

    public Question getQuestion(User user) {
        List<Answer> answers = answerDao.findAllByUser(user);
        return questions.get(Utils.getRandomInt(questions.size()));
    }

}
