package com.vol.chatbot;

import java.util.Random;

public final class Utils {
    private static Random random = new Random();

    private Utils() {
        throw new UnsupportedOperationException();
    }

    public static int getRandomInt
        (int max) {
        return random.nextInt(max);
    }

}
