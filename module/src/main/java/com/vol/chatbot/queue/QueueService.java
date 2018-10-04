package com.vol.chatbot.queue;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface QueueService {

    /**
     * Поместить сообщение в очередь для отправки
     *
     * @param message - сообщение для отправки
     * @return - true в случае успеха, false - если поместить неудалось
     */
    boolean add(SendMessage message);

    /**
     * Возвращает количество сообщений в очереди
     *
     * @return - количество сообщений в очереди
     */
    int size();

    /**
     * Устанавливает метод обработки сообщений в очереди
     *
     * @param sendMessageMethod - метод обработки сообщений(лямда)
     */
    void setMessageSender(SendMessageMethod sendMessageMethod);


    /**
     * Устанавливает количество обработок в секунду
     *
     * @param tps - количество сообщений в секунду
     */
    void setTps(double tps);


    /**
     * Запускает процесс отправки сообщений
     */
    void start();

    /**
     * Останавливает процесс отправки сообщений
     */
    void stop();

    /**
     * Приостанавливает отправку сообщений,
     * т.е. процесс не убивается, но сообщения не отправляет
     */
    void suspend();

    /**
     * Возобнавляет процесс отправки сообщений
     */
    void resume();


}
