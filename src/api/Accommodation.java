package api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * Η κλάση αυτή διαχειρίζεται τις πληροφορίες/χαρακτηριστικά ενός καταλύματος.
 */

public class Accommodation implements Serializable {

    private float avgRating;
    private int totalEvaluations; //avgRating και totalEvaluations αλλάζουν κάθε φορά που προστίθεται αξιολόγηση στο κατάλυμα

    private long singularId; //Για να αποφευχθούν προβλήματα συνωνυμίας  !!!!!!
    private final Provider provider;
    private String name;
    private String description;
    private String stayType; //ξενοδοχείο, διαμέρισμα, μεζονέτα --> hotel, apartment, maisonette
    private Location place;
    private ArrayList<Utility> typesOfUtilities;

    public Accommodation(String name, String description, String stayType, Location location, Provider provider) {
        this.name = name;
        this.description = description;
        this.stayType = stayType;
        totalEvaluations = 0;
        place = location;
        this.provider = provider;
        //Βαθιά αντιγραφή!(?)
//        place = new Location(location.getAddress(), location.getTown(), location.getPostCode());
//        this.provider = new Provider(provider.getFirstName(), provider.getLastName(), provider.getUserName(), provider.getPassword(), "provider");
        singularId = provider.getUserName().hashCode() + name.hashCode(); //Μοναδικό id καταλύματος --> Απαγορεύω στον provider να κάνει δύο καταλύματα με το ίδιο όνομα
        //Μοναδικός κωδικός καταλύματος ακόμα και αν ο ίδιος πάροχος έχει δύο καταλύματα με το ίδιο όνομα
        avgRating = 0;

        typesOfUtilities = new ArrayList<>(); //Για λόγους debugging
        Utility view = new Utility();
        typesOfUtilities.add(view);
        Utility bath = new Utility();
        typesOfUtilities.add(bath);
        Utility washingClothes = new Utility();
        typesOfUtilities.add(washingClothes);
        Utility entertainment = new Utility();
        typesOfUtilities.add(entertainment);
        Utility temperatureControl = new Utility();
        typesOfUtilities.add(temperatureControl);
        Utility internet = new Utility();
        typesOfUtilities.add(internet);
        Utility foodArea = new Utility();
        typesOfUtilities.add(foodArea);
        Utility outsideSpace = new Utility();
        typesOfUtilities.add(outsideSpace);
        Utility parkingSpace = new Utility();
        typesOfUtilities.add(parkingSpace);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStayType() {
        return stayType;
    }

    public void setStayType(String stayType) {
        this.stayType = stayType;
    }

    public Location getLocation() { return place; }

    public void setPlace(Location place) {
        this.place = place;
    }

    public Provider getProvider() {
        return provider;
    }

    public long getSingularId() {
        return singularId;
    }

    /**
     *Η μέθοδος αυτή χρησιμοποιείται για την ανανέωση του μοναδικού κωδικού καταλύματος σε περίπτωση
     * μετανομασίας του.
     */
    public void updateSingularId() {
        singularId = provider.getUserName().hashCode() + name.hashCode();
    }

    public float getAvgRating() {
        return avgRating;
    }

    public int getTotalEvaluations() {
        return totalEvaluations;
    }

    public ArrayList<Utility> getTypesOfUtilities() {
        return typesOfUtilities;
    }

    public void setTypesOfUtilities(ArrayList<Utility> typesOfUtilities) {
        this.typesOfUtilities = typesOfUtilities;
    }

    /**
     * Η μέθοδος αυτή ενημερώνει τον μέσο όρο βαθμολογίας ενός καταλύματος.
     * @param evaluations Οι Βαθμολογίες του καταλύματος.
     */

    public void updateAvgRatingOfAccommodation(ArrayList<Evaluation> evaluations) {
        if (evaluations.size() == 0) {
            avgRating = 0;
            totalEvaluations = 0;
            return;
        }
        float totalSum = 0;
        int numOfEvaluations = 0;
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getAccommodation().getSingularId() == this.singularId) {
                totalSum += evaluation.getGrade();
                numOfEvaluations++;
            }
        }
        if (numOfEvaluations == 0) {
            avgRating = 0;
            totalEvaluations = 0;
            return;
        }
        totalEvaluations = numOfEvaluations;
        avgRating = totalSum / numOfEvaluations;
    }


//    /**
//     * Για να μπορούν να προστεθούν και άλλα types στο μέλλον, π.χ. δωμάτιο σπα. Δεν πρόκειται να χρειαστεί η
//     * αφαίρεση π.χ. της παροχής view οπότε για την ώρα δεν υλοποιείται μέθοδος remove.
//     * @param objUtil ο νέος τύπος παροχής που προστίθεται
//     * @return επιστρέφει true αν το αντικείμενο δεν υπήρχε ήδη και προστέθηκε τώρα
//     */
//    public boolean addUtilityType(Utility objUtil) {
//        if (!typesOfUtilities.isEmpty()) {
//            if (typesOfUtilities.contains(objUtil))
//                return false;
//        }
//        typesOfUtilities.add(objUtil);
//        return true;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accommodation that)) return false;
        return getSingularId() == that.getSingularId();
    }

    @Override
    public String toString() {
        return name + ", " + getLocation().getTown() + ", " + stayType + ", " + avgRating +"(" + totalEvaluations+")";
    }
}
