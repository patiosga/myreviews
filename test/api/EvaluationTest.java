package api;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EvaluationTest {

    Evaluation eval;
    SimpleUser user;
    Accommodation accom;

    @Before
    public void setUp() throws Exception {
        eval = new Evaluation("mageires kai kswtika emfanzotan apo pantou",4.5f,user,accom);
    }

    @Test
    public void getEvaluationText() {
        assertEquals("mageires kai kswtika petagotan apo pantou",eval.getEvaluationText());
    }

    @Test
    public void setEvaluationText() {
    }

    @Test
    public void getGrade() {
        assertEquals(eval.getGrade(),4.5, 0.01);
    }


    @Test
    public void getUser() {
        assertEquals(user,eval.getUser());
    }

    @Test
    public void getAccomodation() {
        assertEquals(accom,eval.getAccommodation());
    }

}