package com.vol.chatbot.model;

import com.vol.chatbot.PojoTestUtils;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class StepTest {

    @Test
    public void testAccesors_shouldAccessProperField() {
        PojoTestUtils.validateAccessors(Step.class);
    }

    @Test
    public void toStringMethod() {
        Step step = new Step();
        assertNotNull(step.toString());
    }

}