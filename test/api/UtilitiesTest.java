package api;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

class UtilitiesTest {
    Utility comforts;

    @Before
    public void setUp() {
        comforts = new Utility();
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