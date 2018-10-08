package com.vol.chatbot.steps;

import com.vol.chatbot.model.impl.Message;
import com.vol.chatbot.model.impl.User;

public interface StepExecutor {

    String run(User user, Message message);

    String getWelcomeString();
}
