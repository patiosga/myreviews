package gui;

import api.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewAccommodation extends JFrame {
    protected JList<String> evaluationList; //Έχει τα String των αξιολογήσεων και γίνεται η αντιστοίχιση μέσω getSelectedIndex()
    protected ArrayList<Evaluation> evaluationsOfAccommodation;
    protected JPanel accommodationDetails;
    protected JPanel generalPanel;
    protected JTextField accommodationName;
    protected JLabel accommodationRating;
    protected JTextField description;
    protected JTextField stayType;
    protected JTextField town;
    protected JTextField address;
    protected JTextField postCode;
    protected JButton viewSelectedEvaluation;
    protected JCheckBox viewPool;
    protected JCheckBox viewBeach;
    protected JCheckBox viewSea;
    protected JCheckBox viewPort;
    protected JCheckBox viewMountain;
    protected JCheckBox viewStreet;
    protected JCheckBox hairDryer;
    protected JCheckBox clotheWasher;
    protected JCheckBox dryer;
    protected JCheckBox tv;
    protected JCheckBox firePlace;
    protected JCheckBox airCondition;
    protected JCheckBox centralHeat;
    protected JCheckBox wifi;
    protected JCheckBox ethernet;
    protected JCheckBox kitchen;
    protected JCheckBox fridge;
    protected JCheckBox microwave;
    protected JCheckBox kitchenware;
    protected JCheckBox spoonsKnives;
    protected JCheckBox plateWasher;
    protected JCheckBox coffeeMachine;
    protected JCheckBox balcony;
    protected JCheckBox yard;
    protected JCheckBox freeParkingProperty;
    protected JCheckBox freeParkingStreet;


    public ViewAccommodation(Accommodation accommodation, ManageEvaluations evaluationManager) {

        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Κατάλυμα: " + accommodation.getName());
        setLayout(new BorderLayout());

        generalPanel = new JPanel(new GridLayout(4,1));

        showAccommodationDetails(accommodation);

        makeEvaluationList(evaluationManager,accommodation);

        //we do this to hide the shame brought upon us
        makeEmptyCheckBoxes();
        fillCheckBoxes(accommodation);



        // Ολο αυτο θα δουλευει διαφορετικά για provider και simpleUser στον δευτερο πρεπει να εμφανίζεται η editable μορφή του evaluation
        viewSelectedEvaluation = new JButton("View Evaluation");
        viewSelectedEvaluation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (evaluationList.getSelectedIndex() != -1) {
                    ViewEvaluation view = new ViewEvaluation(evaluationsOfAccommodation.get(evaluationList.getSelectedIndex()));
                }
            }
        });

        add(generalPanel);
        add(viewSelectedEvaluation, BorderLayout.PAGE_END);

        setVisible(true);
    }

    public ViewAccommodation(Provider provider) { //για το stayType θα εχω drop down list με τις δυνατές επιλογές!!!
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Νέο κατάλυμα");

        generalPanel = new JPanel(new GridLayout(3,1)); //3 rows γιατί το θέλω για τη δημιουργία νέου καταλύματος και άρα δεν έχω λίστα αξιολογήσεων

        Location loc = new Location("","","");
        Accommodation accommodation = new Accommodation("","", "",loc, provider);

        showAccommodationDetails(accommodation);;
        makeEmptyCheckBoxes();

        add(generalPanel);

        add(viewSelectedEvaluation, BorderLayout.PAGE_END);
    }


    protected void showAccommodationDetails(Accommodation accommodation) {

        accommodationDetails = new JPanel(new GridLayout(5,2));

        accommodationName = new JTextField(accommodation.getName());
        accommodationName.setEditable(false); // αλλάζει σε true στην υποκλάση ViewAccommodationAsProvider
        accommodationName.setFont(accommodationName.getFont().deriveFont(Font.BOLD, 14f));

        accommodationRating = new JLabel("Βαθμολογία: " + Float.toString(accommodation.getAvgRating()) + " (" + Integer.toString(accommodation.getTotalEvaluations()) + ")");

        description = new JTextField(accommodation.getDescription());
        description.setEditable(false); // αλλάζει σε true στην υποκλάση ViewAccommodationAsProvider

        stayType = new JTextField(accommodation.getStayType());
        stayType.setEditable(false); // αλλάζει σε true στην υποκλάση ViewAccommodationAsProvider

        town = new JTextField(accommodation.getLocation().getTown());
        address = new JTextField(accommodation.getLocation().getAddress());
        postCode = new JTextField(accommodation.getLocation().getPostCode());
        town.setEditable(false);
        address.setEditable(false);
        postCode.setEditable(false); // αλλάζουν σε true στην υποκλάση ViewAccommodationAsProvider


        accommodationDetails.add(accommodationName);
        accommodationDetails.add(accommodationRating);
        accommodationDetails.add(new JLabel(" Τύπος:"));
        accommodationDetails.add(stayType);
        accommodationDetails.add(new JLabel(" Πόλη:"));
        accommodationDetails.add(town);
        accommodationDetails.add(new JLabel(" Διεύθυνση:"));
        accommodationDetails.add(address);
        accommodationDetails.add(new JLabel(" T.K.:"));
        accommodationDetails.add(postCode);
        generalPanel.add(accommodationDetails);

        JPanel descriptionPanel = new JPanel(new FlowLayout());
        TitledBorder border = BorderFactory.createTitledBorder(" Περιγραφή καταλύματος ");
        descriptionPanel.setBorder(border);
        descriptionPanel.add(description);
        generalPanel.add(descriptionPanel);
    }


    protected void makeEvaluationList(ManageEvaluations evaluationManager, Accommodation accommodation) {
        evaluationsOfAccommodation = evaluationManager.getAccommodationEvaluations(accommodation);
        DefaultListModel<String> helperList = new DefaultListModel<>();
        if (evaluationsOfAccommodation != null) {
            for (int i=0; i<evaluationsOfAccommodation.size(); i++) {
                helperList.add(i, evaluationsOfAccommodation.get(i).toString());
            }
        }
        evaluationList = new JList<>(helperList);
        generalPanel.add(new JScrollPane(evaluationList));
    }


    protected void fillCheckBoxes(Accommodation accommodation) {
        ArrayList<String> viewSpecifics = accommodation.getTypesOfUtilities().get(0).getSpecifics();
        if (!viewSpecifics.isEmpty()) {
            viewPool.setSelected(viewSpecifics.contains("Θέα σε πισίνα"));
            viewBeach.setSelected(viewSpecifics.contains("Θέα σε παραλία"));
            viewSea.setSelected(viewSpecifics.contains("Θέα στη θάλασσα"));
            viewPort.setSelected(viewSpecifics.contains("Θέα στο λιμάνι"));
            viewMountain.setSelected(viewSpecifics.contains("Θέα στο βουνό"));
            viewStreet.setSelected(viewSpecifics.contains("Θέα στον δρόμο"));
        }

        ArrayList<String> bathSpecifics = accommodation.getTypesOfUtilities().get(1).getSpecifics();
        if (!bathSpecifics.isEmpty()) {
            if (bathSpecifics.contains("Πιστολάκι μαλλιών"))
                hairDryer.setSelected(true);
        }

        ArrayList<String> washingClothesSpecifics = accommodation.getTypesOfUtilities().get(2).getSpecifics();
        if (!washingClothesSpecifics.isEmpty()) {
            clotheWasher.setSelected(washingClothesSpecifics.contains("Πλυντήριο ρούχων"));
            dryer.setSelected(washingClothesSpecifics.contains("Στεγνωτήριο"));
        }

        ArrayList<String> entertainmentSpecifics = accommodation.getTypesOfUtilities().get(3).getSpecifics();
        if (!entertainmentSpecifics.isEmpty()) {
            tv.setSelected(entertainmentSpecifics.contains("Τηλεόραση"));
        }

        ArrayList<String> temperatureControlSpecifics = accommodation.getTypesOfUtilities().get(4).getSpecifics();
        if (!temperatureControlSpecifics.isEmpty()) {
            firePlace.setSelected(temperatureControlSpecifics.contains("Εσωτερικό τζάκι"));
            airCondition.setSelected(temperatureControlSpecifics.contains("Κλιματισμός"));
            centralHeat.setSelected(temperatureControlSpecifics.contains("Κεντρική θέρμανση"));
        }

        ArrayList<String> internetSpecifics = accommodation.getTypesOfUtilities().get(5).getSpecifics();
        if (!internetSpecifics.isEmpty()) {
            wifi.setSelected(internetSpecifics.contains("Wifi"));
            ethernet.setSelected(internetSpecifics.contains("Ethernet"));
        }

        ArrayList<String> foodAreasSpecifics = accommodation.getTypesOfUtilities().get(6).getSpecifics();
        if (!foodAreasSpecifics.isEmpty()) {
            kitchen.setSelected(foodAreasSpecifics.contains("Κουζίνα"));
            fridge.setSelected(foodAreasSpecifics.contains("Ψυγείο"));
            microwave.setSelected(foodAreasSpecifics.contains("Φούρνος μικροκυμάτων"));
            kitchenware.setSelected(foodAreasSpecifics.contains("Μαγειρικά είδη"));
            spoonsKnives.setSelected(foodAreasSpecifics.contains("Πιάτα και μαχαιροπίρουνα"));
            plateWasher.setSelected(foodAreasSpecifics.contains("Πλυντήριο πιάτων"));
            coffeeMachine.setSelected(foodAreasSpecifics.contains("Καφετιέρα"));
        }

        ArrayList<String> outsideSpaceSpecifics = accommodation.getTypesOfUtilities().get(7).getSpecifics();
        if (!outsideSpaceSpecifics.isEmpty()) {
            balcony.setSelected(outsideSpaceSpecifics.contains("Μπαλκόνι"));
            yard.setSelected(outsideSpaceSpecifics.contains("Αυλή"));
        }

        ArrayList<String> parkingSpaceSpecifics = accommodation.getTypesOfUtilities().get(8).getSpecifics();
        if (!parkingSpaceSpecifics.isEmpty()) {
            freeParkingProperty.setSelected(parkingSpaceSpecifics.contains("Δωρεάν χώρος στάθμευσης στην ιδιοκτησία"));
            freeParkingStreet.setSelected(parkingSpaceSpecifics.contains("Δωρεάν πάρκινγκ στο δρόμο"));
        }
    }


    public void makeEmptyCheckBoxes() {
        JPanel utilities = new JPanel(new GridLayout(9,1));

        JPanel view = new JPanel(new GridLayout(3,2));
        TitledBorder border = BorderFactory.createTitledBorder(" Θέα ");
        view.setBorder(border);
        viewPool = new JCheckBox("Θέα σε πισίνα");
        viewBeach = new JCheckBox("Θέα σε παραλία");
        viewSea = new JCheckBox("Θέα στη θάλασσα");
        viewPort = new JCheckBox("Θέα στο λιμάνι");
        viewMountain = new JCheckBox("Θέα στο βουνό");
        viewStreet = new JCheckBox("Θέα στον δρόμο");
        view.add(viewPool);
        view.add(viewBeach);
        view.add(viewSea);
        view.add(viewPort);
        view.add(viewMountain);
        view.add(viewStreet);
        viewPool.setEnabled(false);
        viewBeach.setEnabled(false);
        viewSea.setEnabled(false);
        viewPort.setEnabled(false);
        viewMountain.setEnabled(false);
        viewStreet.setEnabled(false);

        utilities.add(view);

        JPanel bath = new JPanel(new GridLayout(1,1));
        TitledBorder border1 = BorderFactory.createTitledBorder(" Μπάνιο ");
        bath.setBorder(border1);
        hairDryer = new JCheckBox("Πιστολάκι μαλλιών");
        bath.add(hairDryer);
        hairDryer.setEnabled(false);

        utilities.add(bath);

        JPanel washingClothes = new JPanel(new GridLayout(1,2));
        TitledBorder border2 = BorderFactory.createTitledBorder(" Πλύσιμο ρούχων ");
        washingClothes.setBorder(border2);
        clotheWasher = new JCheckBox("Πλυντήριο ρούχων");
        dryer = new JCheckBox("Στεγνωτήριο");
        washingClothes.add(clotheWasher);
        washingClothes.add(dryer);
        clotheWasher.setEnabled(false);
        dryer.setEnabled(false);

        utilities.add(washingClothes);

        JPanel entertainment = new JPanel(new GridLayout(1,1));
        TitledBorder border3 = BorderFactory.createTitledBorder(" Ψυχαγωγία ");
        entertainment.setBorder(border3);
        tv = new JCheckBox("Τηλεόραση");
        entertainment.add(tv);
        tv.setEnabled(false);

        utilities.add(entertainment);

        JPanel temperatureControl = new JPanel(new GridLayout(2,2));
        TitledBorder border4 = BorderFactory.createTitledBorder(" Θέρμανση και κλιματισμός ");
        temperatureControl.setBorder(border4);
        firePlace = new JCheckBox("Εσωτερικό τζάκι");
        airCondition = new JCheckBox("Κλιματισμός");
        centralHeat = new JCheckBox("Κεντρική θέρμανση");
        temperatureControl.add(firePlace);
        temperatureControl.add(airCondition);
        temperatureControl.add(centralHeat);
        firePlace.setEnabled(false);
        airCondition.setEnabled(false);
        centralHeat.setEnabled(false);

        utilities.add(temperatureControl);

        JPanel internet = new JPanel(new GridLayout(1,2));
        TitledBorder border5 = BorderFactory.createTitledBorder(" Διαδίκτυο ");
        internet.setBorder(border5);
        wifi = new JCheckBox("Wifi");
        ethernet = new JCheckBox("Ethernet");
        internet.add(wifi);
        internet.add(ethernet);
        wifi.setEnabled(false);
        ethernet.setEnabled(false);

        utilities.add(internet);

        JPanel foodAreas = new JPanel(new GridLayout(4,2));
        TitledBorder border6 = BorderFactory.createTitledBorder(" Κουζίνα και τραπεζαρία ");
        foodAreas.setBorder(border6);
        kitchen = new JCheckBox("Κουζίνα");
        fridge = new JCheckBox("Ψυγείο");
        microwave = new JCheckBox("Φούρνος μικροκυμάτων");
        kitchenware = new JCheckBox("Μαγειρικά είδη");
        spoonsKnives = new JCheckBox("Πιάτα και μαχαιροπίρουνα");
        plateWasher = new JCheckBox("Πλυντήριο πιάτων");
        coffeeMachine = new JCheckBox("Καφετιέρα");
        foodAreas.add(kitchen);
        foodAreas.add(fridge);
        foodAreas.add(microwave);
        foodAreas.add(kitchenware);
        foodAreas.add(spoonsKnives);
        foodAreas.add(plateWasher);
        foodAreas.add(coffeeMachine);
        kitchen.setEnabled(false);
        fridge.setEnabled(false);
        microwave.setEnabled(false);
        kitchenware.setEnabled(false);
        spoonsKnives.setEnabled(false);
        plateWasher.setEnabled(false);
        coffeeMachine.setEnabled(false);

        utilities.add(foodAreas);

        JPanel outsideSpace = new JPanel(new GridLayout(1,2));
        TitledBorder border7 = BorderFactory.createTitledBorder(" Εξωτερικός χώρος ");
        outsideSpace.setBorder(border7);
        balcony = new JCheckBox("Μπαλκόνι");
        yard = new JCheckBox("Αυλή");
        outsideSpace.add(balcony);
        outsideSpace.add(yard);
        balcony.setEnabled(false);
        yard.setEnabled(false);

        utilities.add(outsideSpace);

        JPanel parkingSpace = new JPanel(new GridLayout(1,2));
        TitledBorder border8 = BorderFactory.createTitledBorder(" Χώρος στάθμευσης ");
        parkingSpace.setBorder(border8);
        freeParkingProperty = new JCheckBox("Δωρεάν χώρος στάθμευσης στην ιδιοκτησία");
        freeParkingStreet = new JCheckBox("Δωρεάν πάρκινγκ στο δρόμο");
        parkingSpace.add(freeParkingProperty);
        parkingSpace.add(freeParkingStreet);
        freeParkingStreet.setEnabled(false);
        freeParkingProperty.setEnabled(false);

        utilities.add(parkingSpace);


        generalPanel.add(new JScrollPane(utilities));
    }

}


