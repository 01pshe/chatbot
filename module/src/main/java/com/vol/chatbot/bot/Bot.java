package com.vol.chatbot.bot;

import com.vol.chatbot.model.Message;
import com.vol.chatbot.model.User;
import com.vol.chatbot.services.MessageService;
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

    @Autowired
    public Bot(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
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
        userService.save(user);
        return user;
    }

    private User addMessage(User user, org.telegram.telegrambots.meta.api.objects.Message newMessage){
        Message message = new Message();
        message.setMessage(newMessage.getText());
        message.setUser(user);
        message.setDate(newMessage.getDate());
        messageService.save(message);
        return user;
    }

    private String checkCommand(String inputMessage){
        StringBuilder output = new StringBuilder();
        if (inputMessage.toUpperCase().equals(USERS)){
            Set<User> users = userService.users();
            output.append("List of all users:\n");
            for (User user: users){
                output.append(String.format("User id:%s , UserName:%s\n", user.getSysId(), user.getUserFirstName()));
            }
        }
        if (inputMessage.toUpperCase().startsWith(MESSAGES)){
            String userId = inputMessage.substring(MESSAGES.length());
            User user = userService.getById(Long.valueOf(userId));
            Set<Message> messages = messageService.getMessagesByUser(user);
            output.append(String.format("User %s messages:\n", user.getUserFirstName()));
            for (Message message: messages){
                output.append(String.format("-- %s\n", message.getMessage()));
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
        if (messageService.getCountByUser(user)<=0){
            answer = String.format(WELCOME_MESSAGE,update.getMessage().getFrom().getFirstName());
        } else {
            answer = "";
        }
        answer = answer + checkCommand(update.getMessage().getText());
        addMessage(user, update.getMessage());
        answer = answer + String.format("\n Really? What do you mean \"%s\" ?",update.getMessage().getText());
        System.out.println(String.format("Sending answer <%s>",answer));
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.setText(answer);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return "put your token here";
    }
}
