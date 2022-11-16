package api;

import java.util.ArrayList;
import java.util.HashSet;

public class Katalima {
    private String name;
    private String description;
    private String stayType; //ξενοδοχείο, διαμέρισμα, μεζονέτα --> hotel, apartment, maisonette
    //private Topothesia place; για οταν δημιουργηθεί αντίστοιχη κλάση
    //private ArrayList<Axiologiseis> listAxiologiseis;

    //Παροχές - Προκαθορισμένες σε μορφή checklist σε συνεργασία με το GUI --> ένας θεος ξέρει πως
    private HashSet<String> view;
    private HashSet<String> bathUtilities;
    private HashSet<String> clothWashing;
    private HashSet<String> entertainment;
    private HashSet<String> temperatureControl;
    private HashSet<String> internet;
    private HashSet<String> kitchenStuff;
    private HashSet<String> outsideSpace;
    private HashSet<String> parkingSpace;

    private int numOfRatings;

    public Katalima(String name, String description, String stayType) {
        this.name = name;
        this.description = description;
        this.stayType = stayType;
        numOfRatings = 0;
        view = new HashSet<>();
        bathUtilities = new HashSet<>();
        clothWashing = new HashSet<>();
        entertainment = new HashSet<>();
        temperatureControl = new HashSet<>();
        internet = new HashSet<>();
        kitchenStuff = new HashSet<>();
        outsideSpace = new HashSet<>();
        parkingSpace = new HashSet<>();
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

    public void addView(String extraView) {
        view.add(extraView);
    }
    public void removeView(String extraView) {
        view.remove(extraView);
    }

    public void addBathUtility(String extraBathUtility) {
        bathUtilities.add(extraBathUtility);
    }
    public void removeBathUtility(String extraBathUtility) {
        bathUtilities.remove(extraBathUtility);
    }


}
