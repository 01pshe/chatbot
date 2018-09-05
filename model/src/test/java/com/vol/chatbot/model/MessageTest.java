package com.vol.chatbot.model;

import com.vol.chatbot.PojoTestUtils;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


public class MessageTest {

    @Test
    public void testAccesors_shouldAccessProperField() {

        PojoTestUtils.validateAccessors(Message.class);
    }

    @Test
    public void toStringMethod() {
        Message message = new Message();
        assertNotNull(message.toString());
    }
}