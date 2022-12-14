package api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {


    Location location;

    @Before
    public void setUp() throws Exception {
        location = new Location("Fagkli","Salougka", "12345");
    }

    @Test
    public void getAddress() {
        assertEquals("Fagkli",location.getAddress());
    }



    @Test
    public void getTown() {
        assertEquals("Salougka",location.getTown());
    }



    @Test
    public void getPostCode() {
        assertEquals("12345", location.getAddress());
    }


}