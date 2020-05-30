package com.teltov.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class TestFeatureOne {
    @Test
    public void passTestExample() {
        assertTrue(true);
    }

    @Test
    public void failTestExample() {
        assertTrue(false);
    }
}
