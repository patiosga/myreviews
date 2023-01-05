package api;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class AccommodationTest {
    private Accommodation accommodation;
    private ArrayList<Evaluation> evaluations;
    private Evaluation eval1;
    private Evaluation eval2;
    private Evaluation eval3;

    @Before
    public void setUp() throws Exception {
        evaluations = new ArrayList<>();
        SimpleUser user = new SimpleUser("","","","","");
        Provider provider = new Provider("John", "Pap", "user1", "pass", "provider");
        accommodation = new Accommodation("Tester", "Testing", "Ξενοδοχείο", new Location("","",""), provider);
        eval1 = new Evaluation("",5, user, accommodation);
        eval2 = new Evaluation("", 1,user,accommodation);
        eval3 = new Evaluation("", 3, user, accommodation);
    }

    @Test
    public void getSingularId() {
        assertEquals("user1".hashCode() + "Tester".hashCode(), accommodation.getSingularId());
    }

    @Test
    public void updateSingularId() {
        accommodation.setName("Tester2");
        accommodation.updateSingularId();
        assertEquals("Tester2".hashCode() + "user1".hashCode(), accommodation.getSingularId());
    }

    @Test
    public void updateAvgRatingOfAccommodation() {
        assertEquals(accommodation.getAvgRating(), 0, 0.01);
        assertEquals(accommodation.getTotalEvaluations(), 0);
        evaluations.add(eval1);
        accommodation.updateAvgRatingOfAccommodation(evaluations);
        assertEquals(accommodation.getTotalEvaluations(), 1);
        assertEquals(accommodation.getAvgRating(), 5, 0.01);
        evaluations.add(eval2);
        accommodation.updateAvgRatingOfAccommodation(evaluations);
        assertEquals(accommodation.getTotalEvaluations(), 2);
        assertEquals(accommodation.getAvgRating(), 3, 0.01);
        evaluations.add(eval3);
        accommodation.updateAvgRatingOfAccommodation(evaluations);
        assertEquals(accommodation.getTotalEvaluations(), 3);
        assertEquals(accommodation.getAvgRating(), 3, 0.01);
    }

    @Test
    public void testEquals() {
        Accommodation accommodation1 = new Accommodation("Tester", "Amazing", "Mezonation", new Location("", "",""), new Provider("","","user1","pass","provider"));
        assertEquals(accommodation1, accommodation);
    }


}