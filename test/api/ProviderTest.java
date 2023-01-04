package api;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ProviderTest {
    private Accommodation accommodation1;
    private Accommodation accommodation2;
    private ArrayList<Evaluation> evaluations;
    private Provider provider;
    private Evaluation eval1;
    private Evaluation eval2;
    private Evaluation eval3;
    private Evaluation eval4;
    @Before
    public void setUp() throws Exception {
        provider = new Provider("","","provider1", "","provider");
        accommodation1 = new Accommodation("Acc1", "", "Mezonaki", new Location("","",""),provider);
        accommodation2 = new Accommodation("Acc2", "", "Diamersmia", new Location("","",""), provider);
        SimpleUser user1 = new SimpleUser("","","user1", "","");
        SimpleUser user2 = new SimpleUser("","","user2", "","");
        eval1 = new Evaluation("wow", 5f, user1, accommodation1);
        eval2 = new Evaluation("phew", 2f, user1, accommodation1);
        eval3 = new Evaluation("ah", 1, user2, accommodation2);
        eval4 = new Evaluation("errr", 2, user2, accommodation2);
        evaluations = new ArrayList<>();
        evaluations.add(eval1);
        evaluations.add(eval2);
        evaluations.add(eval3);
        evaluations.add(eval4);
    }

    @Test
    public void updateAvgRatingOfAllAccom() {
        assertEquals(provider.getAvgRatingOfAllAccom(), 0, 0.01);
        assertEquals(provider.getTotalRatings(), 0);
        provider.updateAvgRatingOfAllAccom(evaluations);
        assertEquals(provider.getAvgRatingOfAllAccom(), 2.5, 0.01);
        assertEquals(provider.getTotalRatings(), 4);
        evaluations.remove(eval1);
        evaluations.remove(eval3);
        provider.updateAvgRatingOfAllAccom(evaluations);
        assertEquals(provider.getAvgRatingOfAllAccom(), 2, 0.01);
        assertEquals(provider.getTotalRatings(), 2);
        evaluations.remove(eval2);
        evaluations.remove(eval4);
        provider.updateAvgRatingOfAllAccom(evaluations);
        assertEquals(provider.getAvgRatingOfAllAccom(), 0, 0.01);
        assertEquals(provider.getTotalRatings(), 0);
    }
}