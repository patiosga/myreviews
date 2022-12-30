import api.*;
import gui.SignUpPage;
import gui.ViewAccommodation;

import java.sql.Array;
import java.util.ArrayList;


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




        ArrayList<Utility> generalUtilities = new ArrayList<>();
        Utility view = new Utility();
        view.addSpecificUtility("Θέα σε πισίνα");
        view.addSpecificUtility("Θέα σε παραλία");
        view.addSpecificUtility("Θέα στη θάλασσα");
        view.addSpecificUtility("Θέα στο λιμάνι");
        view.addSpecificUtility("Θέα στο βουνό");
        view.addSpecificUtility("Θέα στον δρόμο");
        generalUtilities.add(view);

        Utility bath = new Utility();
        bath.addSpecificUtility("Πιστολάκι μαλλιών");
        generalUtilities.add(bath);

        Utility washingClothes = new Utility();
        washingClothes.addSpecificUtility("Πλυντήριο ρούχων");
        washingClothes.addSpecificUtility("Στεγνωτήριο");
        generalUtilities.add(washingClothes);

        Utility entertainment = new Utility();
        entertainment.addSpecificUtility("Τηλεόραση");
        generalUtilities.add(entertainment);

        Utility temperatureControl = new Utility();
        temperatureControl.addSpecificUtility("Εσωτερικό τζάκι");
        temperatureControl.addSpecificUtility("Κλιματισμός");
        temperatureControl.addSpecificUtility("Κεντρική θέρμανση");
        generalUtilities.add(temperatureControl);

        Utility internet = new Utility();
        internet.addSpecificUtility("Wifi");
        internet.addSpecificUtility("Ethernet");
        generalUtilities.add(internet);

        Utility foodAreas = new Utility();
        foodAreas.addSpecificUtility("Κουζίνα");
        foodAreas.addSpecificUtility("Ψυγείο");
        foodAreas.addSpecificUtility("Φούρνος μικροκυμάτων");
        foodAreas.addSpecificUtility("Μαγειρικά είδη");
        foodAreas.addSpecificUtility("Πιάτα και μαχαιροπίρουνα");
        foodAreas.addSpecificUtility("Πλυντήριο πιάτων");
        foodAreas.addSpecificUtility("Καφετιέρα");
        generalUtilities.add(foodAreas);

        Utility outsideSpace = new Utility();
        outsideSpace.addSpecificUtility("Μπαλκόνι");
        outsideSpace.addSpecificUtility("Αυλή");
        generalUtilities.add(outsideSpace);

        Utility parkingSpace = new Utility();
        parkingSpace.addSpecificUtility("Δωρεάν χώρος στάθμευσης στην ιδιοκτησία");
        parkingSpace.addSpecificUtility("Δωρεάν πάρκινγκ στο δρόμο");
        generalUtilities.add(parkingSpace);

        apartment.setTypesOfUtilities(generalUtilities);

        ViewAccommodation accommodationFrame = new ViewAccommodation(apartment, manageEvaluations);
        //ViewEditableEvaluationToCreate evaluationFrame = new ViewEditableEvaluationToCreate(apartment, simpleUser, manageEvaluations);


    }
}
