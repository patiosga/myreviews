package api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {
    private Location location;

    @Before
    public void setUp() throws Exception {
        location = new Location("Fagkli","Salonika", "12345");
    }

    @Test
    public void getAddress() {
        assertEquals("Fagkli",location.getAddress());
    }



    @Test
    public void getTown() {
        assertEquals("Salonika",location.getTown());
    }



    @Test
    public void getPostCode() {
        assertEquals("12345", location.getPostCode());
    }


    @Test
    public void setAddress() {
        location.setAddress("Egnatia");
        assertEquals("Egnatia", location.getAddress());
    }

    @Test
    public void setTown() {
        location.setTown("Drama");
        assertEquals("Drama", location.getTown());
    }

    @Test
    public void setPostCode() {
        location.setPostCode("54321");
        assertEquals("54321", location.getPostCode());
    }
}