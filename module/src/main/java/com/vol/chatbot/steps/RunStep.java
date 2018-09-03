package com.vol.chatbot.steps;

import com.vol.chatbot.model.Message;
import com.vol.chatbot.model.User;

public interface RunStep {

    String runStep(User user, Message message);

    String getWelcomeString();
}
