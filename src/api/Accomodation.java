package api;

import java.util.ArrayList;

public class Accomodation {
    private String name;
    private String description;
    private String stayType; //ξενοδοχείο, διαμέρισμα, μεζονέτα --> hotel, apartment, maisonette
    private Location place;
    private ArrayList<Evaluation> evaluations;

    //Παροχές - Προκαθορισμένες σε μορφή checklist σε συνεργασία με το GUI --> ένας θεος ξέρει πως
    private ArrayList<Utilities> typesOfUtilities;


    private int numOfRatings;

    public Accomodation(String name, String description, String stayType, Location location) {
        this.name = name;
        this.description = description;
        this.stayType = stayType;
        numOfRatings = 0;
        place = location;
        typesOfUtilities = new ArrayList<>();
        evaluations = new ArrayList<>();
    }


    public float getAverageOfRatings() {
        if (getNumOfRatings() == 0)
            return 0;
        float totalGrades = 0;
        for (Evaluation evaluation : evaluations) {
            totalGrades += evaluation.getGrade();
        }
        return totalGrades/getNumOfRatings();
    }


    public int getNumOfRatings() {
        return numOfRatings;
    }

    public void setNumOfRatings(int numOfRatings) {
        this.numOfRatings = numOfRatings;
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

    public Location getPlace() {
        return place;
    }

    public void setPlace(Location place) {
        this.place = place;
    }

    /**
     * Για να μπορούν να προστεθούν και άλλα types στο μέλλον, π.χ. δωμάτιο σπα. Δεν πρόκειται να χρειαστεί η
     * αφαίρεση π.χ. της παροχής view οπότε για την ώρα δεν υλοποιείται μέθοδος remove
     * @param objUtil ο νέος τύπος παροχής που προστίθεται
     * @return επιστρέφει true αν το αντικείμενο δεν υπήρχε ήδη και προστέθηκε τώρα
     */
    public boolean addUtilityType(Utilities objUtil) {
        if (!typesOfUtilities.contains(objUtil)) {
            typesOfUtilities.add(objUtil);
            return true;
        }
        return false;
    }

    public boolean addEvaluation(Evaluation rating) {
        //Έλεγχος αν ο ίδιος χρήστης έχει αξιολογήσει ξανά αυτό το κατάλυμα αξιοποιώντας τη μοναδικότητα του username του
        for (Evaluation evaluation : evaluations) {
            if (rating.getUser().getUserName() == evaluation.getUser().getUserName())
                return false;
        }
        evaluations.add(rating);
        numOfRatings++;
        return true;
    }

    public boolean removeEvaluation(Evaluation rating) {
        for (Evaluation evaluation : evaluations) {
            if (rating.getUser().getUserName() == evaluation.getUser().getUserName()) {
                evaluations.remove(rating);
                numOfRatings--;
                return true;
            }
        }
        return false;
    }




    public void display() {
        //όνομα, τύπος, τοποθεσία, μέσος όρος
    }




}
