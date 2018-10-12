package com.vol.chatbot;

import com.vol.chatbot.model.Question;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Utils {
    private static Random random = new Random();

    private Utils() {
        throw new UnsupportedOperationException();
    }

    public static int getRandomInt(int max) {
        return random.nextInt(max);
    }

    public static List<String> getAnswersMix(Question question) {
        List<String> answers = Stream.of(question.getAnswerA(), question.getAnswerB(), question.getAnswerC(), question.getAnswerD())
            .filter(Objects::nonNull)
            .filter(s -> s.length() > 0)
            .collect(Collectors.toList());
        Collections.shuffle(answers);
        return answers;
    }

}
