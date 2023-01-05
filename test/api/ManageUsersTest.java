package api;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ManageUsersTest {
    private ManageUsers usersManager;
    private SimpleUser user1;
    private SimpleUser user2;
    private Provider provider1;
    private Provider provider2;
    private ArrayList<Evaluation> evaluations;
    private ArrayList<Accommodation> accommodations;
    @Before
    public void setUp() throws Exception {
        user1 = new SimpleUser("","","user1", "","");
        user2 = new SimpleUser("","","user2", "","");
        provider1 = new Provider("","","provider1","","");
        provider2 = new Provider("","","provider2","","");
        usersManager = new ManageUsers();

        Location loc = new Location("","","");
        Accommodation acc1 = new Accommodation("Acc1", "","Mezonation", loc, provider1);
        Accommodation acc2 = new Accommodation("Acc2", "", "Diamerismation", loc, provider1);
        Accommodation acc3 = new Accommodation("Acc3", "", "Xenodoxization", loc, provider2);
        Evaluation eval1 = new Evaluation("wow", 3.5f, user1, acc1);
        Evaluation eval2 = new Evaluation("phew", 2f, user1, acc2);
        Evaluation eval3 = new Evaluation("ah", 1, user2, acc2);
        Evaluation eval4 = new Evaluation("errr", 5, user2, acc3);
        accommodations = new ArrayList<>();
        evaluations = new ArrayList<>();
        accommodations.add(acc1);
        accommodations.add(acc2);
        accommodations.add(acc3);
        evaluations.add(eval1);
        evaluations.add(eval2);
        evaluations.add(eval3);
        evaluations.add(eval4);
    }

    @Test
    public void findUserWithUsername() {
        assertEquals(user1 ,usersManager.createSimpleUser("","","user1", ""));
        assertEquals(user1, usersManager.findUserWithUsername("user1"));
        assertNull(usersManager.findUserWithUsername("user0"));
        assertNotEquals(user2, usersManager.findUserWithUsername("user1"));
        assertEquals(user2, usersManager.createSimpleUser("","","user2", ""));
        assertEquals(user2, usersManager.findUserWithUsername("user2"));
        assertEquals(provider1, usersManager.createProvider("","","provider1", ""));
        assertEquals(provider1, usersManager.findUserWithUsername("provider1"));
        assertEquals(provider2, usersManager.createProvider("","","provider2", ""));
        assertEquals(provider2, usersManager.findUserWithUsername("provider2"));
    }

    @Test
    public void isSimpleUser() {
        assertTrue(usersManager.isSimpleUser(user1));
        assertTrue(usersManager.isSimpleUser(user2));
        assertFalse(usersManager.isSimpleUser(provider1));
        assertFalse(usersManager.isSimpleUser(provider2));
    }

    @Test
    public void isProvider() {
        assertFalse(usersManager.isProvider(user1));
        assertFalse(usersManager.isProvider(user2));
        assertTrue(usersManager.isProvider(provider1));
        assertTrue(usersManager.isProvider(provider2));
    }

    @Test
    public void authentication() {
        assertFalse(usersManager.authentication("user1", "pass1"));
        assertFalse(usersManager.authentication("provider2", "pass3"));
        usersManager.createSimpleUser("","","user1", "pass1");
        usersManager.createSimpleUser("","","user2", "pass2");
        usersManager.createProvider("","","provider1", "pass3");
        usersManager.createProvider("","","provider2", "pass4");
        assertTrue(usersManager.authentication("user1", "pass1"));
        assertTrue(usersManager.authentication("user2", "pass2"));
        assertTrue(usersManager.authentication("provider1", "pass3"));
        assertTrue(usersManager.authentication("provider2", "pass4"));
        assertFalse(usersManager.authentication("provider15", "pass3"));
        assertFalse(usersManager.authentication("user4", "pass3"));

    }

    @Test
    public void checkSignUpInaccuracies() {
        assertEquals("Όλα τα πεδία κειμένου είναι υποχρεωτικά για την εγγραφή", usersManager.checkSignUpInaccuracies("","","",""));
        assertEquals("Όλα τα πεδία κειμένου είναι υποχρεωτικά για την εγγραφή", usersManager.checkSignUpInaccuracies("dve","eve","ev",""));
        assertEquals("Όλα τα πεδία κειμένου είναι υποχρεωτικά για την εγγραφή", usersManager.checkSignUpInaccuracies("","er","r",""));
        assertEquals("Όλα τα πεδία κειμένου είναι υποχρεωτικά για την εγγραφή", usersManager.checkSignUpInaccuracies("   ","  ", "dd","ffff"));

        assertNull(usersManager.checkSignUpInaccuracies("gg", "hh", "user1", "pass"));
        assertNull(usersManager.checkSignUpInaccuracies("gg", "hh", "provider1", "pass"));

        usersManager.createSimpleUser("wefv", "wfb", "user1", "pass24");
        usersManager.createProvider("sfv","ev", "provider1", "pass34");
        assertEquals("Το όνομα χρήστη " + "user1" + " υπάρχει ήδη", usersManager.checkSignUpInaccuracies("gg", "hh", "user1", "pass"));
        assertEquals("Το όνομα χρήστη " + "provider1" + " υπάρχει ήδη", usersManager.checkSignUpInaccuracies("gg", "hh", "provider1", "pass"));
    }
}