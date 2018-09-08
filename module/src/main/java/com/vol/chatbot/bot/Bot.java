package com.vol.chatbot.bot;

import com.vol.chatbot.StepExecutorFactory;
import com.vol.chatbot.model.Message;
import com.vol.chatbot.model.Scenario;
import com.vol.chatbot.model.User;
import com.vol.chatbot.model.UserInfo;
import com.vol.chatbot.services.MessageService;
import com.vol.chatbot.services.ScenarioService;
import com.vol.chatbot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.Date;
import java.util.Set;

@Component
public class Bot extends TelegramLongPollingBot {

    private static final String WELCOME_MESSAGE = "Hello %s! Nice to see you here!";
    private static final String USERS = "/USERS";
    private static final String MESSAGES = "/MESSAGES ";

    private UserService userService;
    private MessageService messageService;
    private ScenarioService scenarioService;

    @Autowired
    public Bot(UserService userService, MessageService messageService, ScenarioService scenarioService) {
        this.userService = userService;
        this.messageService = messageService;
        this.scenarioService = scenarioService;
    }

    private User getUser(String signature, org.telegram.telegrambots.meta.api.objects.User telegramUser){
        User user = userService.getBySignature(signature);
        if (user!=null){
            return user;
        }
        user = new User();
        user.setBot(telegramUser.getBot());
        user.setSignature(signature);
        user.setUserFirstName(telegramUser.getFirstName());
        user.setUserLastName(telegramUser.getLastName());
        user.setUserName(telegramUser.getUserName());
        user.setDatecreate(new Date());
        Scenario scenario = new Scenario();
        scenario.setCurrentStepNumber(0);
        UserInfo userInfo = new UserInfo();
        user.setScenario(scenario);
        user.setUserInfo(userInfo);
        userService.save(user);
        return user;
    }



    private String checkCommand(String inputMessage){
        StringBuilder output = new StringBuilder();
        if (inputMessage.toUpperCase().equals(USERS)){
            Set<User> users = userService.users();
            output.append("List of all users:\n");
            for (User user: users){
                output.append(String.format("User id:%s , UserName:%s\n", user.getId(), user.getUserFirstName()));
            }
        }
        if (inputMessage.toUpperCase().startsWith(MESSAGES)){
            String userId = inputMessage.substring(MESSAGES.length());
            User user = userService.getById(Long.valueOf(userId));
            Set<Message> messages = messageService.getMessagesByUser(user);
            output.append(String.format("User %s messages:\n", user.getUserFirstName()));
            for (Message message: messages){
                output.append(String.format("%s\n", message.getMessage()));
            }
        }
        return output.toString();
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(String.format("Get message <%s>\nfrom %s",
                update.getMessage().getText(),
                update.getMessage().getFrom().getFirstName() ));
        User user = getUser(update.getMessage().getFrom().getId().toString(),update.getMessage().getFrom());
        String answer;
        Message message  = messageService.addInboundMessage(user, update.getMessage());
        answer = checkCommand(update.getMessage().getText());
        if (answer.isEmpty()){
            answer = StepExecutorFactory
                    .getFactory()
                    .getStep(scenarioService.getCurrentStep(user.getScenario()))
                    .run(user,message);
        }
        System.out.println(String.format("Sending answer <%s>",answer));
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.enableHtml(true);
        sendMessage.setText(answer);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        messageService.addOutboundMessage(user, answer);
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return "Put you token id here";
    }
}
