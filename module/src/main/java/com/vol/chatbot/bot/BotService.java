package com.vol.chatbot.bot;

import com.vol.chatbot.model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotService {
    SendMessage createResponse(User user, Update update);
}
