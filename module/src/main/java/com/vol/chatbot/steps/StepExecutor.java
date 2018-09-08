package com.vol.chatbot.steps;

import com.vol.chatbot.model.Message;
import com.vol.chatbot.model.User;

public interface StepExecutor {

  String run(User user, Message message);

  String getWelcomeString();
}
