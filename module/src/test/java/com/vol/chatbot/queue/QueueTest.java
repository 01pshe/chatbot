package com.vol.chatbot.queue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.junit.Assert.assertTrue;


public class QueueTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueueTest.class);

    private void write(BotApiMethod method) {
        if (method instanceof SendMessage) {
            String text = ((SendMessage) method).getText();
            LOGGER.info("message: {}", text);
        }

    }

    @Test
    public void test() throws Exception {


        QueueService queueService = new ConcurentQueueService();
        queueService.setMessageSender(this::write);
        LOGGER.info("Queue created.");
        LOGGER.info("Set TPS to 1.");
        queueService.setTps(1.0);
        for (int i = 1; i <= 15; i++) {
            SendMessage message = new SendMessage();
            message.setText(String.valueOf(i));
            queueService.add(message);
        }
        assertTrue(15 == queueService.size());
        LOGGER.info("15 messages added to queue.");
        queueService.start();
        LOGGER.info("QueueWorkers started waiting 5 seconds.");
        Thread.sleep(5000);
        LOGGER.info("suspending 5 seconds.");
        queueService.suspend();
        Thread.sleep(5000);
        LOGGER.info("Resume for 5 seconds.");
        queueService.resume();
        Thread.sleep(5000);
        LOGGER.info("Stop workers for 5 seconds.");
        queueService.stop();
        Thread.sleep(5000);
        queueService.setTps(10.0);
        LOGGER.info("Set TPS to 10.");
        for (int i = 16; i <= 30; i++) {
            SendMessage message = new SendMessage();
            message.setText(String.valueOf(i));
            queueService.add(message);
        }
        LOGGER.info("Added 15 messages");
        LOGGER.info("Start.");
        queueService.start();
        Thread.sleep(2100);
        assertTrue(0 == queueService.size());
    }
}
