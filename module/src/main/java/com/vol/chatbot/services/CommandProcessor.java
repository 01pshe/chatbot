package com.vol.chatbot.services;

import com.vol.chatbot.bot.BotService;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.User;
import com.vol.chatbot.services.propertiesservice.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Qualifier("commandProcessor")
public class CommandProcessor implements BotService {

    private PropertiesService propertiesService;

    @Autowired
    public CommandProcessor(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }


    public enum Command {
        PROPERTY
    }

    @Override
    public SendMessage createResponse(User user, Update update) {
        String msg = update.getMessage().getText().substring(1).toUpperCase();
        int pos = msg.indexOf(' ');
        String com = "";
        String args = "";
        if (pos<0){
            com = msg.substring(0, pos);
            args = msg.substring(pos + 1);
        } else {
            com = msg;
        }
        Command command = Command.valueOf(com);
        SendMessage answer = new SendMessage();
        answer.setChatId(update.getMessage().getChatId());

        switch (command) {
            case PROPERTY:
                Properties prop = Properties.valueOf(args);
                answer.setText(propertiesService.getAsString(prop));
                break;
            default:
                answer.setText("Команда не поддерживается");
                break;
        }
        return answer;
    }
}
