package com.vol.chatbot.model;

import com.vol.chatbot.PojoTestUtils;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UserTest {

    @Test
    public void testAccesors_shouldAccessProperField() {
        PojoTestUtils.validateAccessors(User.class);
    }

    @Test
    public void toStringMethod() {
        User user = new User();
        assertNotNull(user.toString());
    }

}