package com.vol.chatbot;

import com.vol.chatbot.bot.Bot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Service
public class ApplicationService {

    public ApplicationService() {
//        System.getProperties().put( "proxySet", "true" );
//        System.getProperties().put( "socksProxyHost", "127.0.0.1" );
//        System.getProperties().put( "socksProxyPort", "9150" );

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        Bot bot = new Bot();
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
