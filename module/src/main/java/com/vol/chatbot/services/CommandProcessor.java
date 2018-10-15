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

import java.util.Arrays;
import java.util.Iterator;

@Service
@Qualifier("commandProcessor")
public class CommandProcessor implements BotService {

    private PropertiesService propertiesService;

    @Autowired
    public CommandProcessor(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }


    public enum Command {
        START,
        PROPERTY,
        UNSUPPORTED
    }

    @Override
    public SendMessage createResponse(User user, Update update) {
        String msg = update.getMessage().getText().substring(1).toUpperCase();
        int pos = msg.indexOf(' ');
        String stringCommand;
        String args = "";
        if (pos>0){
            stringCommand = msg.substring(0, pos);
            args = msg.substring(pos + 1);
        } else {
            stringCommand = msg;
        }
        Command command = getCommandFromString(stringCommand);

        return processCommand(command, args);

    }

    private Command getCommandFromString(String commandString){
        Command command = Command.UNSUPPORTED;
        Iterator<Command> iter = Arrays.asList(Command.values()).iterator();
        while (iter.hasNext()&&command==Command.UNSUPPORTED){
            Command tmp = iter.next();
            if (tmp.name().equals(commandString)){
                command = tmp;
            }
        }
        return command;
    }

    private SendMessage processCommand(Command command, String args) {
        SendMessage answer = new SendMessage();
        switch (command) {
            case PROPERTY:
                if (Properties.exist(args)) {
                    Properties prop = Properties.valueOf(args);
                    answer.setText(propertiesService.getAsString(prop));
                } else {
                    answer.setText("Указанной настройки не существует.");
                }
                break;
            case START:
                answer.setText(propertiesService.getAsString(Properties.WELCOME_TEXT));
                break;
            default:
                answer.setText("Команда не поддерживается.");
                break;
        }
        return answer;
    }
}
