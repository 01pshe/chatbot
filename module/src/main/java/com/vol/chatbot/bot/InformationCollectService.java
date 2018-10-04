package com.vol.chatbot.bot;

import com.vol.chatbot.StepExecutorFactory;
import com.vol.chatbot.model.Message;
import com.vol.chatbot.model.User;
import com.vol.chatbot.services.MessageService;
import com.vol.chatbot.services.ScenarioService;
import com.vol.chatbot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Set;

@Component
public class InformationCollectService implements BotService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InformationCollectService.class);

    private static final String USERS = "/USERS";
    private static final String MESSAGES = "/MESSAGES ";

    private UserService userService;
    private MessageService messageService;
    private ScenarioService scenarioService;

    @Autowired
    public InformationCollectService(UserService userService, MessageService messageService, ScenarioService scenarioService) {
        this.userService = userService;
        this.messageService = messageService;
        this.scenarioService = scenarioService;
    }

    @Override
    public SendMessage getMessage(Update update) {
        LOGGER.info("Get message <{}>\nfrom {}",
            update.getMessage().getText(),
            update.getMessage().getFrom().getFirstName());
        User user = userService.getUser(update.getMessage().getFrom().getId().toString(), update.getMessage().getFrom());
        String answer;
        Message message = messageService.addInboundMessage(user, update.getMessage());
        answer = checkCommand(update.getMessage().getText());
        if (answer.isEmpty()) {
            answer = StepExecutorFactory
                .getFactory()
                .getStep(scenarioService.getCurrentStep(user.getScenario()))
                .run(user, message);
        }
        LOGGER.info("Sending answer <{}>", answer);
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.enableHtml(true);
        sendMessage.setText(answer);

        return sendMessage;
    }


    private String checkCommand(String inputMessage) {
        StringBuilder output = new StringBuilder();
        if (inputMessage.equalsIgnoreCase(USERS)) {
            Set<User> users = userService.users();
            output.append("List of all users:\n");
            for (User user : users) {
                output.append(String.format("User id:%s , UserName:%s%n", user.getId(), user.getUserFirstName()));
            }
        }
        if (inputMessage.toUpperCase().startsWith(MESSAGES)) {
            String userId = inputMessage.substring(MESSAGES.length());
            User user = userService.getById(Long.valueOf(userId));
            Set<Message> messages = messageService.getMessagesByUser(user);
            output.append(String.format("User %s messages:%n", user.getUserFirstName()));
            for (Message message : messages) {
                output.append(String.format("%s%n", message.getMessageText()));
            }
        }
        return output.toString();
    }
}
