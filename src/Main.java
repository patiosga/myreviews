import api.*;
import gui.ViewAccommodation;
import gui.ViewEvaluation;

public class Main {
    static public void main(String[] args) {
        SimpleUser simpleUser = new SimpleUser("John", "Vet", "vetoulis", "paok", "simpleUser");
        SimpleUser simpleUser2 = new SimpleUser("John2", "Vet2", "vetoulis2", "paok2", "simpleUser");
        Provider provider = new Provider("Mitsos", "Pap", "user1", "pass1", "provider");
        Location place = new Location("Arx Litis 6", "Thess", "12345");
        Accommodation apartment = new Accommodation("Luxury Place", "The best thing in town right now with extra bathroom", "apartment", place, provider);
        Evaluation evaluation = new Evaluation("Greatest of all time", (float) 4.5, simpleUser, apartment);
        ManageEvaluations manageEvaluations = new ManageEvaluations();
        manageEvaluations.addEvaluation("Greatest of all time", 3.5f, simpleUser, apartment);
        manageEvaluations.addEvaluation("Greatest of all time", 5f, simpleUser2, apartment);

        ViewAccommodation accommodationFrame = new ViewAccommodation(apartment, manageEvaluations);
        //ViewEvaluation evaluationFrame = new ViewEvaluation(evaluation);
    }
}
