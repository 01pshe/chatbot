package com.vol.chatbot.services;

import com.vol.chatbot.bot.BotService;
import com.vol.chatbot.messagesender.MessageSender;
import com.vol.chatbot.model.Properties;
import com.vol.chatbot.model.User;
import com.vol.chatbot.queue.QueueService;
import com.vol.chatbot.services.propertiesservice.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.EntityManagerFactory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
@Qualifier("commandProcessor")
public class CommandProcessor implements BotService {

    private PropertiesService propertiesService;
    private MessageSender messageSender;
    private QueueService queueService;
    private BotService answerCollectService;
    private EntityManagerFactory entityManagerFactory;


    @Autowired
    public CommandProcessor(@Qualifier("answerCollectService") BotService answerCollectService,
                            PropertiesService propertiesService,
                            MessageSender messageSender,
                            QueueService queueService,
                            EntityManagerFactory entityManagerFactory) {
        this.propertiesService = propertiesService;
        this.messageSender = messageSender;
        this.queueService = queueService;
        this.answerCollectService = answerCollectService;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public SendMessage createResponse(User user, Update update) {
        String msg = update.getMessage().getText().substring(1).toUpperCase();
        int pos = msg.indexOf(' ');
        String stringCommand;
        String args = "";
        if (pos > 0) {
            stringCommand = msg.substring(0, pos);
            args = msg.substring(pos + 1);
        } else {
            stringCommand = msg;
        }
        Command command = getCommandFromString(stringCommand);

        return processCommand(command, args, update, user);

    }

    private Command getCommandFromString(String commandString) {
        Command command = Command.UNSUPPORTED;
        Iterator<Command> iter = Arrays.asList(Command.values()).iterator();
        while (iter.hasNext() && command == Command.UNSUPPORTED) {
            Command tmp = iter.next();
            if (tmp.name().equals(commandString)) {
                command = tmp;
            }
        }
        return command;
    }

    private SendMessage processCommand(Command command, String args, Update update, User user) {
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
            case SEND_MESSAGE_TO_ALL:
                messageSender.sendAll(args);
                answer.setText("message was sent");
                break;
            case SEND_MESSAGE_BY_NAMES:
                String text = args.substring(0, args.indexOf(';'));
                String namesString = args.substring(text.length() + 1);
                Set<String> names = new HashSet<>(Arrays.asList(namesString.split(",")));
                messageSender.sendAllByNic(text, names);
                answer.setText("message was sent");
                break;
            case START:
                try (AnswerHelper ah = new AnswerHelper(user, propertiesService.getAsInteger(Properties.CURRENT_DAY), update, entityManagerFactory)) {


                    if (ah.startFirst()) {
//                    if (ah.getPassedQuestions().isEmpty()) {
                        SendMessage message = new SendMessage();
                        message.enableMarkdown(true);
                        message.setChatId(update.getMessage().getChatId());
                        String preparedText = String.format(
                            propertiesService.getAsString(Properties.WELCOME_TEXT),
                            user.getUserFirstName());
                        message.setText(preparedText);
                        queueService.add(message);
                        answer = answerCollectService.createResponse(user, update);
                    } else if (ah.getExpectedAnswers().size() == 1) {
                        answer = ah.getQuestionLastMessage();
                    } else {
                        answer = null;
                    }
                }
                break;
            default:
                answer.setText("Команда не поддерживается.");
                break;
        }
        return answer;
    }

    public enum Command {
        START,
        SEND_MESSAGE_TO_ALL,
        SEND_MESSAGE_BY_NAMES,
        PROPERTY,
        UNSUPPORTED
    }
}
