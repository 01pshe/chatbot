package com.vol.chatbot;

import com.vol.chatbot.bot.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@SpringBootApplication
@ImportResource("classpath:spring-context.xml")
public class BotStarter implements CommandLineRunner {

    @Autowired
    private Bot bot;

    public static void main(String[] args) {


        //Comment this before make deploy on remote  --- Start ----
        System.getProperties().put( "proxySet", "true" );
        System.getProperties().put( "socksProxyHost", "127.0.0.1" );
        System.getProperties().put( "socksProxyPort", "9150" );
        //Comment this before make deploy on remote  --- End ----

        ApiContextInitializer.init();
        SpringApplication.run(BotStarter.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(bot);
    }
}
