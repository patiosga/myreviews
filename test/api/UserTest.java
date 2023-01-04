package api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    private SimpleUser user;
    private Provider provider;

    @Before
    public void setUp() throws Exception {
        user = new SimpleUser("John", "Pap", "user1", "pass1","simpleUser");
        provider = new Provider("George", "Giorp", "provider1", "pass2", "provider");
    }

    @Test
    public void getUserName() {
        assertEquals(user.getUserName(), "user1");
        assertEquals(provider.getUserName(), "provider1");
    }

    @Test
    public void getPassword() {
        assertEquals(user.getPassword(), "pass1");
        assertEquals(provider.getPassword(), "pass2");
        user.setPassword("pass");
        provider.setPassword("pass");
        assertEquals(user.getPassword(), "pass");
        assertEquals(provider.getPassword(), "pass");
    }

    @Test
    public void getType() {
        assertEquals(user.getType(), "simpleUser");
        assertEquals(provider.getType(), "provider");
    }

    @Test
    public void getFirstName() {
        assertEquals(user.getFirstName(), "John");
        assertEquals(provider.getFirstName(), "George");
    }

    @Test
    public void getLastName() {
        assertEquals(user.getLastName(), "Pap");
        assertEquals(provider.getLastName(), "Giorp");
    }

    @Test
    public void testEquals() {
        assertFalse(user.equals(provider));
        SimpleUser user2 = new SimpleUser("","","user1","","");
        SimpleUser user3 = new SimpleUser("","","user2","","");
        assertTrue(user.equals(user2));
        assertFalse(user.equals(user3));
        Provider provider1 = new Provider("","","provider1","","");
        assertTrue(provider.equals(provider1));
        assertTrue(provider.equals(provider));

    }
}