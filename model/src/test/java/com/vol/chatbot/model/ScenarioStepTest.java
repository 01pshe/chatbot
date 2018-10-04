package com.vol.chatbot.model;

import com.vol.chatbot.PojoTestUtils;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ScenarioStepTest {

    @Test
    public void testAccesors_shouldAccessProperField() {
        PojoTestUtils.validateAccessors(ScenarioStep.class);
    }

    @Test
    public void toStringMethod() {
        ScenarioStep scenarioStep = new ScenarioStep();
        assertNotNull(scenarioStep.toString());
    }

}