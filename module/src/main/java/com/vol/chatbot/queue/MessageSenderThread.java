package com.vol.chatbot.queue;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class MessageSenderThread implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSenderThread.class);

    private Queue<SendMessage> queue;
    private AtomicBoolean suspended;
    private AtomicBoolean shutDown;
    private Consumer<SendMessage> sendMessageMethod;

    private double tps = 20.0;

    public MessageSenderThread(Queue<SendMessage> queue,
                               AtomicBoolean suspended,
                               AtomicBoolean shutDown) {
        this.queue = queue;
        this.suspended = suspended;
        this.shutDown = shutDown;
    }

    public void setSendMessageMethod(Consumer<SendMessage> sendMessageMethod) {
        this.sendMessageMethod = sendMessageMethod;
    }

    public Consumer<SendMessage> getSendMessageMethod() {
        return sendMessageMethod;
    }

    private void sendSingleMessage() {
        Optional message = Optional.ofNullable(this.queue.poll());
        if (message.isPresent()) {
            LOGGER.debug("sending message:{}", ((SendMessage) message.get()).getText());
            sendMessageMethod.accept((SendMessage) message.get());
        }
    }

    public void setTps(double tps) {
        this.tps = tps;
    }

    @Override
    public void run() {
        final RateLimiter rateLimiter = RateLimiter.create(this.tps);
        while (!shutDown.get()) {
            rateLimiter.acquire();
            if (!suspended.get()) {
                sendSingleMessage();
            }
        }

    }
}
