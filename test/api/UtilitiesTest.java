package api;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UtilitiesTest {
    private Utility comforts;

    @Before
    public void setUp() {
        comforts = new Utility();
        comforts.addSpecificUtility("Πιστολάκι");
    }

    @Test
    public void addSpecificUtility() {
        comforts.addSpecificUtility("roof");
        comforts.addSpecificUtility("flippers");
        assertFalse(comforts.addSpecificUtility("Πιστολάκι"));
        assertTrue(comforts.getSpecifics().contains("roof"));
        assertTrue(comforts.getSpecifics().contains("flippers"));
        assertTrue(comforts.getSpecifics().contains("Πιστολάκι"));
        assertFalse(comforts.getSpecifics().contains("sayonara"));
    }

    @Test
    public void removeSpecificUtility() {
        comforts.addSpecificUtility("roof");
        assertFalse(comforts.removeSpecificUtility("sayonara"));
        assertTrue(comforts.removeSpecificUtility("roof"));
        assertTrue(comforts.removeSpecificUtility("Πιστολάκι"));
        assertFalse(comforts.getSpecifics().contains("Πιστολάκι"));
        assertFalse(comforts.getSpecifics().contains("roof"));
        assertFalse(comforts.removeSpecificUtility("roof")); //κενή λίστα
    }
}