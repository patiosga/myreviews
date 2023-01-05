package api;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SimpleUserTest {
    private Accommodation accommodation1;
    private Accommodation accommodation2;
    private ArrayList<Evaluation> evaluations;
    private Provider provider;
    private Evaluation eval1;
    private Evaluation eval2;
    private Evaluation eval3;
    private Evaluation eval4;
    SimpleUser user1;

    @Before
    public void setUp() throws Exception {
        provider = new Provider("","","provider1", "","provider");
        accommodation1 = new Accommodation("Acc1", "", "Mezonaki", new Location("","",""),provider);
        accommodation2 = new Accommodation("Acc2", "", "Diamersmia", new Location("","",""), provider);
        user1 = new SimpleUser("","","user1", "","");
        eval1 = new Evaluation("wow", 5f, user1, accommodation1);
        eval2 = new Evaluation("phew", 2f, user1, accommodation1);
        eval3 = new Evaluation("ah", 1, user1, accommodation2);
        eval4 = new Evaluation("errr", 2, user1, accommodation2);
        evaluations = new ArrayList<>();

    }

    @Test
    public void updateAvgRatingOfUser() {
        assertEquals(user1.getAvgRating(), 0, 0.01);
        evaluations.add(eval1);
        evaluations.add(eval2);
        user1.updateAvgRatingOfUser(evaluations);
        assertEquals(user1.getAvgRating(), 3.5, 0.01);
        evaluations.add(eval3);
        user1.updateAvgRatingOfUser(evaluations);
        assertEquals(user1.getAvgRating(), 2.66666, 0.01);
        evaluations.add(eval4);
        user1.updateAvgRatingOfUser(evaluations);
        assertEquals(user1.getAvgRating(), 2.5, 0.01);
        evaluations = new ArrayList<>();
        user1.updateAvgRatingOfUser(evaluations);
        assertEquals(user1.getAvgRating(), 0, 0.01);


    }
}