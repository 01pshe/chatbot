package com.vol.chatbot.knowledge;

import com.vol.chatbot.Constant;

import java.util.*;

public class Task {
    private static int MAX = 100;
    private static int MIN = 10;
    private static Random rn = new Random();

    private String uuid;
    private String question;
    private String answer;
    private int correct;
    private List<Integer> buttonViews = new ArrayList<>(Constant.BUTTON_COUNT);

    public Task() {
        this.uuid = UUID.randomUUID().toString();
        int left = geInteger();
        int right = geInteger();
        this.correct = left + right;
        initButtonViews(left, right);
        this.question = String.valueOf(left) + " + " + String.valueOf(right) + " = ";
    }

    private void initButtonViews(int left, int right) {
        buttonViews.add(left + right);
        buttonViews.add(left + geInteger());
        buttonViews.add(right + geInteger());
        buttonViews.add(left - geInteger());
        buttonViews.add(right - geInteger());
        Collections.sort(buttonViews);
    }

    public String getUuid() {
        return uuid;
    }

    private int geInteger() {
        return rn.nextInt(MAX - MIN + 1) + MIN;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getCorrect() {
        return correct;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public List<Integer> getButtonViews() {
        return buttonViews;
    }
}
