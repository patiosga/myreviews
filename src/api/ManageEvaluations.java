package api;

import java.util.HashSet;

public class ManageEvaluations {
    HashSet<Evaluation> evaluations;

    public ManageEvaluations() {
        evaluations = new HashSet<>();
    }

    private boolean userAlreadyEvaluatedThis(SimpleUser user, Accomodation accomodation) {
        if (evaluations.isEmpty())
            return false;
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getUser().getUserName() == user.getUserName() && evaluation.getAccomodation().getSingularId() == accomodation.getSingularId())
                return true;
        }
        return false;
    }

    public boolean addEvaluation()
}
