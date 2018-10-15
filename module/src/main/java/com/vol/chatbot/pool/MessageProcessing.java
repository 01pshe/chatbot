package com.vol.chatbot.pool;

import com.vol.chatbot.model.Properties;
import com.vol.chatbot.services.propertiesservice.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

@Service
public class MessageProcessing implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessing.class);
    private ForkJoinPool pool;
    private PropertiesService propertiesService;


    public MessageProcessing(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    @PostConstruct
    private void initPool() {
        int handlerCount = propertiesService.getAsInteger(Properties.MESSAGE_HANDLER_COUNT);
        this.pool = new ForkJoinPool(handlerCount);
    }

    public void execute(RecursiveAction task) {
        pool.execute(task);
    }

    @Override
    public void close() {
        if (this.pool != null && !this.pool.isShutdown()) {
            this.pool.shutdown();
        }
    }
}
