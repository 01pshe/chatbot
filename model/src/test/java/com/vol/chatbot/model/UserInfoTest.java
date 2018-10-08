package com.vol.chatbot.model;

import com.vol.chatbot.PojoTestUtils;
import com.vol.chatbot.model.impl.UserInfo;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UserInfoTest {

    @Test
    public void testAccesors_shouldAccessProperField() {
        PojoTestUtils.validateAccessors(UserInfo.class);
    }

    @Test
    public void toStringMethod() {
        UserInfo userInfo = new UserInfo();
        assertNotNull(userInfo.toString());
    }

}