package api;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class LocationTest {

    Location loc;

    @BeforeEach
    public void setUp() {
        loc = new Location("Flower Street", "Thessaloniki", "12345");
    }

    @After
    public void tearDown() {
    }


    @Test
    void getAddress() {
        assertEquals(loc.getAddress(), "Flower Street");
    }

    @Test
    void setAddress() {
        loc.setAddress("Main");
        assertEquals(loc.getAddress(), "Main");
    }

    @Test
    void getTown() {
        assertEquals(loc.getTown(), "Thessaloniki");
    }

    @Test
    void setTown() {
        loc.setTown("Thessaloniki");
        assertEquals(loc.getTown(), "Thessaloniki");
    }

    @Test
    void getPostCode() {
        assertEquals(loc.getPostCode(), "12345");
    }

    @Test
    void setPostCode() {
        loc.setPostCode("54321");
        assertEquals(loc.getPostCode(), "54321");
    }
}