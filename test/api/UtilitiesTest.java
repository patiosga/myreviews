package api;

import static org.junit.Assert.*;

import org.junit.After;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UtilitiesTest {
    Utilities comforts;

    @BeforeEach
    public void setUp() {
        comforts = new Utilities();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addSpecificUtility() {
        comforts.addSpecificUtility("roof");
        comforts.addSpecificUtility("flippers");
        assertTrue(comforts.getSpecifics().contains("roof"));
        assertTrue(comforts.getSpecifics().contains("flippers"));
        assertFalse(comforts.getSpecifics().contains("sayonara"));
    }

    @Test
    public void removeSpecificUtility() {
        comforts.addSpecificUtility("roof");
        comforts.addSpecificUtility("flippers");
        assertFalse(comforts.removeSpecificUtility("sayonara"));
        assertTrue(comforts.removeSpecificUtility("roof"));
        assertFalse(comforts.getSpecifics().contains("roof"));
    }
}