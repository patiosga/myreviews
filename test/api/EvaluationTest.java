package api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EvaluationTest {

    private Evaluation eval;
    private SimpleUser user;
    private Provider provider;
    private Accommodation accommodation;

    @Before
    public void setUp() throws Exception {
        user = new SimpleUser("John", "Papad", "user1", "pass", "simpleUser");
        provider = new Provider("Zorz", "pap", "provider1", "pass", "provider");
        accommodation = new Accommodation("TestAccommodation", "Testing", "Διαμέρισμα", new Location("Εγνατία", "Δράμα", "12344"), provider);
        eval = new Evaluation("mageires kai kswtika emfanzotan apo pantou",4.5f,user,accommodation);
    }

    @Test
    public void getEvaluationText() {
        assertEquals("mageires kai kswtika emfanzotan apo pantou",eval.getEvaluationText());
    }

    @Test
    public void setEvaluationText() {
        eval.setEvaluationText("Testing");
        assertEquals("Testing", eval.getEvaluationText());
    }

    @Test
    public void getGrade() {
        assertEquals(eval.getGrade(),4.5, 0.01);
    }


    @Test
    public void getUser() {
        assertEquals(user, eval.getUser());
    }

    @Test
    public void getAccommodation() {
        assertEquals(accommodation, eval.getAccommodation());
    }

    @Test
    public void testEquals() {
        Evaluation eval1 = new Evaluation("",4, user, accommodation);
        assertEquals(eval1, eval);
        SimpleUser user2 = new SimpleUser("","","user2","pass", "simpleUser");
        Evaluation eval2 = new Evaluation("",3,user2,accommodation);
        assertNotEquals(eval2, eval);
    }
}