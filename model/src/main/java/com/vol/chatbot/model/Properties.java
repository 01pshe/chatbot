package com.vol.chatbot.model;

import java.util.Arrays;
import java.util.Iterator;

public enum Properties {

    SUSPEND_MODE("false"),
    CURRENT_DAY("1"),
    REFRESH_QUESTION_TIME("60"),
    RESULT_EXCELLENT_PCT("80"),
    RESULT_GOOD_PCT("50"),
    RESULT_BAD_PCT("0"),
    MESSAGE_HANDLER_COUNT("16"),
    DAY_QUESTION_CNT("20"),
    DIFFICULT_QUESTION_CNT("5"),
    MEDIUM_QUESTION_CNT("7"),
    EASY_QUESTION_CNT("8"),
    EXCELLENT_RESULT("Молодец *%s* \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n" +
        "\n" +
        " ✅ Твой результат - %.2f процентов!\n" +
        "\n" +
        "Приходи на стенд СберТеха, покажи свой результат и получи приз. \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n" +
        "\n" +
        "А еще ты можешь принять участие в розыгрыше главного приза\uD83D\uDC4C. \n" +
        "Розыгрыш пройдёт на стенде СберТеха *20 октября в 17:10*!"),


    GOOD_RESULT("Молодец *%s* \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n" +
        "\n" +
        " ✅ Твой результат - %.2f процентов!\n" +
        "\n" +
        "Приходи на стенд СберТеха, покажи свой результат и получи приз.\uD83C\uDF81\uD83C\uDF81\uD83C\uDF81"),

    BAD_RESULT("Молодец *%s* \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n"+
        "\n" +
        " ✅ Твой результат - %.2f процентов!\n" +
        "\n" +
        "Спасибо что принял участие в нашем тесте! \uD83D\uDE09"),

    SUSPEND_TEXT("На сегодня тестирование окончено, \n" +
        "пора сделать перерыв ✨\uD83D\uDE34\uD83C\uDF19 \n"+
        " Завтра появятся новые вопросы! "),

    SUSPEND_TEXT_END("Тестирование окончено, \n" +
        "спасибо за участие! "),

    WELCOME_TEXT("Привет, *%s*! \uD83D\uDC4B \n" +
        "Здорово, что ты решился пройти тест на знание *Java*. \uD83D\uDCDD \n"+
        "Тест проводится два дня, каждый день новые вопросы и возможность получить приз. \n" +
        "Набери максимум баллов и ты сможешь участвовать в финальном розыгрыше главного приза. \n"+
        "Будь лучшим, удачи.\uD83C\uDF81 \n"
    );


    private String defaultVal;

    Properties(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public static boolean exist(String prop){
        boolean found = false;
        Iterator<Properties> iter = Arrays.asList(Properties.values()).iterator();
        while (iter.hasNext()&&!found){
            Properties tmp = iter.next();
            if (tmp.name().equals(prop)){
                found= true;
            }
        }
        return found;
    }
}
