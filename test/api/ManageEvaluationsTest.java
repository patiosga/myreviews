package api;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.*;

public class ManageEvaluationsTest {
    private ManageEvaluations evaluationsManager;
    private Evaluation eval1;
    private Evaluation eval2;
    private Evaluation eval3;
    private Accommodation acc1;
    private Accommodation acc2;
    private Provider provider;
    private SimpleUser user1;
    private SimpleUser user2;

    @Before
    public void setUp() throws Exception {
        evaluationsManager = new ManageEvaluations();
        provider = new Provider("","","provoder1","pass","provider");
        user1 = new SimpleUser("","","user1", "","simpleUser");
        user2 = new SimpleUser("","","user2", "","simpleUser");
        Location loc = new Location("","","");
        acc1 = new Accommodation("Tester100", "","",loc, provider);
        acc2 = new Accommodation("Tester200", "","",loc, provider);
        eval1 = new Evaluation("",5,user1, acc1);
        eval2 = new Evaluation("", 3, user1, acc2);
        eval3 = new Evaluation("", 1, user2, acc1);
    }

//    @Test
//    public void removedAccommodationAlert() {
//        evaluationsManager.addEvaluation("",5,user1, acc1);
//
//
//    }

    @Test
    public void userAlreadyEvaluatedThis() {
        assertFalse(evaluationsManager.userAlreadyEvaluatedThis(user1, acc1));
        assertFalse(evaluationsManager.userAlreadyEvaluatedThis(user2, acc1));
        evaluationsManager.addEvaluation("",5,user1, acc1);
        assertTrue(evaluationsManager.userAlreadyEvaluatedThis(user1, acc1));
        evaluationsManager.addEvaluation("", 1, user2, acc1);
        assertFalse(evaluationsManager.userAlreadyEvaluatedThis(user2, acc2));
        assertTrue(evaluationsManager.userAlreadyEvaluatedThis(user2, acc1));

    }

    @Test
    public void addEvaluation() {
        ArrayList<Evaluation> results = new ArrayList<>();
        assertEquals(results, evaluationsManager.getUserEvaluations(user1));
        assertEquals(results, evaluationsManager.getUserEvaluations(user2));

        assertTrue(evaluationsManager.addEvaluation("",5,user1, acc1));
        results.add(eval1);
        assertEquals(results, evaluationsManager.getUserEvaluations(user1));
        assertFalse(evaluationsManager.addEvaluation("",5,user1, acc1));
        assertEquals(results, evaluationsManager.getUserEvaluations(user1));
        assertTrue(evaluationsManager.addEvaluation("", 3, user1, acc2));
        assertTrue(evaluationsManager.getUserEvaluations(user1).contains(eval1));
        assertTrue(evaluationsManager.getUserEvaluations(user1).contains(eval2));
        assertTrue(evaluationsManager.addEvaluation("", 1, user2, acc1));
        assertFalse(evaluationsManager.addEvaluation("", 1, user2, acc1));
        results = new ArrayList<>();
        results.add(eval3);
        assertEquals(results, evaluationsManager.getUserEvaluations(user2));
    }

    @Test
    public void removeEvaluation() {
        assertTrue(evaluationsManager.addEvaluation("",5,user1, acc1));
        assertTrue(evaluationsManager.addEvaluation("", 3, user1, acc2));
        assertFalse(evaluationsManager.removeEvaluation(eval3));
        assertTrue(evaluationsManager.removeEvaluation(eval1));
        assertTrue(evaluationsManager.removeEvaluation(eval2));
        assertFalse(evaluationsManager.removeEvaluation(eval1));

        assertTrue(evaluationsManager.addEvaluation("", 3, user1, acc2));
        assertTrue(evaluationsManager.addEvaluation("", 1, user2, acc1));
        assertTrue(evaluationsManager.removeEvaluation(eval3));
        ArrayList <Evaluation> results = new ArrayList<>();
        assertEquals(results, evaluationsManager.getUserEvaluations(user2));
        results.add(eval2);
        assertEquals(results, evaluationsManager.getUserEvaluations(user1));

        results = new ArrayList<>();
        evaluationsManager.removedAccommodationAlert(acc2);
        assertEquals(results, evaluationsManager.getAccommodationEvaluations(acc2));
        assertEquals(results, evaluationsManager.getUserEvaluations(user2));
        evaluationsManager.removedAccommodationAlert(acc1);
        assertEquals(results, evaluationsManager.getEvaluations());
    }

    @Test
    public void getAccommodationEvaluations() {
        ArrayList<Evaluation> results = new ArrayList<>();
        assertEquals(results, evaluationsManager.getAccommodationEvaluations(acc1));
        assertEquals(results, evaluationsManager.getAccommodationEvaluations(acc2));
        evaluationsManager.addEvaluation("",5,user1, acc1);
        evaluationsManager.addEvaluation("", 3, user1, acc2);
        results.add(eval1);
        assertEquals(results, evaluationsManager.getAccommodationEvaluations(acc1));
        evaluationsManager.addEvaluation("", 1, user2, acc1);
        results.add(eval3);
        assertEquals(results, evaluationsManager.getAccommodationEvaluations(acc1));
        evaluationsManager.removeEvaluation(eval1);
        results.remove(eval1);
        assertEquals(results, evaluationsManager.getAccommodationEvaluations(acc1));
        results = new ArrayList<>();
        results.add(eval2);
        assertEquals(results, evaluationsManager.getAccommodationEvaluations(acc2));
    }

    @Test
    public void alterEvaluation() {
        ArrayList<Evaluation> results = new ArrayList<>();
        evaluationsManager.addEvaluation("",5,user1, acc1);
        evaluationsManager.addEvaluation("", 3, user1, acc2);
        assertFalse(evaluationsManager.alterEvaluation(eval3, 4, "changed"));
        assertTrue(evaluationsManager.alterEvaluation(eval1, 4, "changed"));
        assertEquals(evaluationsManager.getUserEvaluations(user1).get(1).getGrade(), 4, 0.01);
        eval1 = new Evaluation("",4,user1, acc1);
        assertTrue(evaluationsManager.removeEvaluation(eval1));
        assertFalse(evaluationsManager.alterEvaluation(eval1, 4, "changed"));
    }

    @Test
    public void getProvidersNumOfEvaluations() {
        assertEquals(evaluationsManager.getProvidersNumOfEvaluations(acc1.getProvider()), 0);

        evaluationsManager.addEvaluation("",5,user1, acc1);
        evaluationsManager.addEvaluation("", 3, user1, acc2);
        evaluationsManager.addEvaluation("", 1, user2, acc1);
        assertEquals(evaluationsManager.getProvidersNumOfEvaluations(acc1.getProvider()), 3);
        assertEquals(evaluationsManager.getProvidersNumOfEvaluations(acc2.getProvider()), 3);

    }

    @Test
    public void isUsersEvaluation() {
        assertFalse(evaluationsManager.isUsersEvaluation(user1, eval3));
        assertTrue(evaluationsManager.isUsersEvaluation(user1, eval2));
        assertTrue(evaluationsManager.isUsersEvaluation(user2, eval3));
    }

    @Test
    public void evaluationTextTooLong() {
        assertFalse(evaluationsManager.evaluationTextTooLong("Text"));
        assertFalse(evaluationsManager.evaluationTextTooLong("Textkwhdbvoadsbvoadbvaodbhvaosdbv"));
        //504 χαρακτήρες
        assertTrue(evaluationsManager.evaluationTextTooLong("oehbodbvoweibfvoisbefjiovbsozjidfvoiabdfhvboaidfviosbdfbiosbdfbsljdflbsdoifvslkdjvnslkdfnvsldkf sldfjbvwifbvoiefbvfbvsdkfjvsbldkfvbslkdfvsblkdfvbslkdfvbslkdfjvbslkdfvbslkdfvsblkdfjvbslkdfvbieusvbsldkfjvblieurvbeiruvbwlierubweiruvbweirvbwiebvwirubwerughoehbodbvoweibfvoisbefjiovbsozjidfvoiabdfhvboaidfviosbdfbiosbdfbsljdflbsdoifvslkdjvnslkdfnvsldkf sldfjbvwifbvoiefbvfbvsdkfjvsbldkfvbslkdfvsblkdfvbslkdfvbslkdfjvbslkdfvbslkdfvsblkdfjvbslkdfvbieusvbsldkfjvblieurvbeiruvbwlierubweiruvbweirvbwiebvwirubwerugh"));
        //500 χαρακτήρες
        assertFalse(evaluationsManager.evaluationTextTooLong("odbvoweibfvoisbefjiovbsozjidfvoiabdfhvboaidfviosbdfbiosbdfbsljdflbsdoifvslkdjvnslkdfnvsldkf sldfjbvwifbvoiefbvfbvsdkfjvsbldkfvbslkdfvsblkdfvbslkdfvbslkdfjvbslkdfvbslkdfvsblkdfjvbslkdfvbieusvbsldkfjvblieurvbeiruvbwlierubweiruvbweirvbwiebvwirubwerughoehbodbvoweibfvoisbefjiovbsozjidfvoiabdfhvboaidfviosbdfbiosbdfbsljdflbsdoifvslkdjvnslkdfnvsldkf sldfjbvwifbvoiefbvfbvsdkfjvsbldkfvbslkdfvsblkdfvbslkdfvbslkdfjvbslkdfvbslkdfvsblkdfjvbslkdfvbieusvbsldkfjvblieurvbeiruvbwlierubweiruvbweirvbwiebvwirubwerugh"));
    }

    @Test
    public void gradeOutOfBounds() {
        assertFalse(evaluationsManager.gradeOutOfBounds(1));
        assertFalse(evaluationsManager.gradeOutOfBounds(2.5f));
        assertFalse(evaluationsManager.gradeOutOfBounds(3));
        assertFalse(evaluationsManager.gradeOutOfBounds(4.9876f));
        assertFalse(evaluationsManager.gradeOutOfBounds(5));
        assertTrue(evaluationsManager.gradeOutOfBounds(0.9f));
        assertTrue(evaluationsManager.gradeOutOfBounds(0));
        assertTrue(evaluationsManager.gradeOutOfBounds(10));
        assertTrue(evaluationsManager.gradeOutOfBounds(5.1f));
        assertTrue(evaluationsManager.gradeOutOfBounds(-1));
    }

    @Test
    public void checkSubmissionInaccuracies() {
        assertEquals("Παρακαλώ εισάγετε αριθμό στο πεδίο της βαθμολογίας.", evaluationsManager.checkSubmissionInaccuracies("wow", "aaa"));
        assertEquals("Παρακαλώ εισάγετε αριθμό στο πεδίο της βαθμολογίας.", evaluationsManager.checkSubmissionInaccuracies("wow", ""));
        assertEquals("Παρακαλώ εισάγετε αριθμό στο πεδίο της βαθμολογίας.", evaluationsManager.checkSubmissionInaccuracies("wow", "0!"));
        assertEquals("Παρακαλώ εισάγετε βαθμολογία μεταξύ του 1 και 5.", evaluationsManager.checkSubmissionInaccuracies("wow", "0.9"));
        assertEquals("Παρακαλώ εισάγετε βαθμολογία μεταξύ του 1 και 5.", evaluationsManager.checkSubmissionInaccuracies("wow", "10.56"));
        assertEquals("Παρακαλώ εισάγετε βαθμολογία μεταξύ του 1 και 5.", evaluationsManager.checkSubmissionInaccuracies("wow", "-5"));
        assertEquals(evaluationsManager.checkSubmissionInaccuracies("oehbodbvoweibfvoisbefjiovbsozjidfvoiabdfhvboaidfviosbdfbiosbdfbsljdflbsdoifvslkdjvnslkdfnvsldkf sldfjbvwifbvoiefbvfbvsdkfjvsbldkfvbslkdfvsblkdfvbslkdfvbslkdfjvbslkdfvbslkdfvsblkdfjvbslkdfvbieusvbsldkfjvblieurvbeiruvbwlierubweiruvbweirvbwiebvwirubwerughoehbodbvoweibfvoisbefjiovbsozjidfvoiabdfhvboaidfviosbdfbiosbdfbsljdflbsdoifvslkdjvnslkdfnvsldkf sldfjbvwifbvoiefbvfbvsdkfjvsbldkfvbslkdfvsblkdfvbslkdfvbslkdfjvbslkdfvbslkdfvsblkdfjvbslkdfvbieusvbsldkfjvblieurvbeiruvbwlierubweiruvbweirvbwiebvwirubwerugh", "4"), "Το κείμενο της αξιολόγησης δεν πρέπει να υπερβαίνει τους 500 χαρακτήρες");
        assertEquals("Το κείμενο της αξιολόγησης είναι υποχρεωτικό.", evaluationsManager.checkSubmissionInaccuracies("", "3.5"));
        assertEquals("Το κείμενο της αξιολόγησης είναι υποχρεωτικό.", evaluationsManager.checkSubmissionInaccuracies("    ", "3.5"));
    }
}