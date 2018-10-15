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
    EXCELLENT_RESULT("Молодец\uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n" +
        "\n" +
        " ✅ Твой результат - %.2f процентов!\n" +
        "\n" +
        "Приходи на стенд СберТеха, покажи свой результат и получи приз. \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n" +
        "\n" +
        "А еще ты можешь принять участие в розыгрыше главного приза\uD83D\uDC4C. \n" +
        "Розыгрыш пройдёт на стенде СберТеха 20 октября в 17:10!"),
    GOOD_RESULT("Молодец\uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n" +
        "\n" +
        " ✅ Твой результат - %.2f процентов!\n" +
        "\n" +
        "Приходи на стенд СберТеха, покажи свой результат и получи приз.\uD83C\uDF81\uD83C\uDF81\uD83C\uDF81"),
    BAD_RESULT("Молодец\uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n"+
        "\n" +
        " ✅ Твой результат - %.2f процентов!\n" +
        "\n" +
        "Спасибо что принял участие в нашем тесте! \uD83D\uDE09"),
    SUSPEND_TEXT("На сегодня тестирование окончено, \n" +
        "пора сделать перерыв ✨\uD83D\uDE34\uD83C\uDF19 \n"+
        " Завтра появятся новые вопросы! "),
    WELCOME_TEXT("Приветствую тебя! \uD83D\uDC4B \n" +
        "Спасибо что решил заглянуть в наш тест! \uD83D\uDCDD \n"+
        "Тест будет проходить два дня, часть вопросов в первый день, часть во второй. " +
        "За какждый день ты можешь получить отдельный приз. \n"+
        "Если тебе удастся набрать достаточное количество баллов, ты получишь ценный приз!!!\uD83C\uDF81 \n"+
        "Если тебе удастся пройти тест одним из лучших, то ты сможешь принять участие в розыгрыше главного приза!!!\uD83C\uDF89"
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
