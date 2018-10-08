package com.vol.chatbot.model;

import com.vol.chatbot.PojoTestUtils;
import com.vol.chatbot.model.impl.Scenario;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ScenarioTest {

    @Test
    public void testAccesors_shouldAccessProperField() {
        PojoTestUtils.validateAccessors(Scenario.class);
    }

    @Test
    public void toStringMethod() {
        Scenario scenario = new Scenario();
        assertNotNull(scenario.toString());
    }

}