package api;

import java.util.ArrayList;

public class Katalima {
    private String name;
    private String description;
    private String stayType; //ξενοδοχείο, διαμέρισμα, μεζονέτα --> hotel, apartment, maisonette
    //private Location place; για όταν δημιουργηθεί αντίστοιχη κλάση
    //private ArrayList<Evaluation> listEvaluations;

    //Παροχές - Προκαθορισμένες σε μορφή checklist σε συνεργασία με το GUI --> ένας θεος ξέρει πως
    private ArrayList<Utilities> typesOfUtilities;


    private int numOfRatings;

    public Katalima(String name, String description, String stayType) {
        this.name = name;
        this.description = description;
        this.stayType = stayType;
        numOfRatings = 0;
        typesOfUtilities = new ArrayList<>();
    }

    //public float getAverageOfRatings() {}!!!!!!!!


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

    public boolean addUtilityType(Utilities objUtil) { //για να μπορούν να προστεθούν και άλλα types στο μέλλον, π.χ. δωμάτιο σπα
        if (!typesOfUtilities.contains(objUtil)) {
            typesOfUtilities.add(objUtil);
            return true;
        }
        return false;
    }

    public void display() {
        //όνομα, τύπος, τοποθεσία, μέσος όρος
    }




}
