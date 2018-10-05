package com.vol.chatbot.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Service
public class ConcurrentQueueService implements QueueService {


    private Queue<SendMessage> queue = new ConcurrentLinkedQueue<>();
    private AtomicBoolean suspended = new AtomicBoolean(false);
    private AtomicBoolean shutDown = new AtomicBoolean(false);
    private MessageSenderThread messageSender;

    @Autowired
    public ConcurrentQueueService() {
        this.messageSender = new MessageSenderThread(queue, suspended, shutDown);
    }

    public void setMessageSender(Consumer<SendMessage> sendMessageMethod) {
        this.messageSender.setSendMessageMethod(sendMessageMethod);
    }

    @Override
    public boolean add(SendMessage message) {
        return queue.offer(message);
    }

    @Override
    public void suspend() {
        suspended.set(true);
    }

    @Override
    public void resume() {
        suspended.set(false);
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public void setTps(double tps) {
        this.messageSender.setTps(tps);
    }

    @Override
    public void start() {
        shutDown.set(false);
        suspended.set(false);
        if (this.messageSender.getSendMessageMethod() == null) {
            throw new IllegalStateException("Для обработчика очереди не указан метод отправки сообщений. используйте метод setMessageSender.");
        }
        Thread thread = new Thread(this.messageSender);
        thread.start();
    }

    @Override
    public void stop() {
        shutDown.set(true);
    }
}
