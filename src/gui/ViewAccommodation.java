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

        JPanel generalPanel = new JPanel(new GridLayout(4,1));

        accommodationDetails = new JPanel(new GridLayout(5,2,5,5));

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

        generalPanel.add(description);

        evaluationsOfAccommodation = evaluationManager.getAccommodationEvaluations(accommodation);
        DefaultListModel<String> helperList = new DefaultListModel<>();
        if (evaluationsOfAccommodation != null) {
                for (int i=0; i<evaluationsOfAccommodation.size(); i++) {
                    helperList.add(i, evaluationsOfAccommodation.get(i).toString());
                }
        }
        evaluationList = new JList<>(helperList);
        generalPanel.add(new JScrollPane(evaluationList));



        //Check boxes hell
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

        ArrayList<String> viewSpecifics = accommodation.getTypesOfUtilities().get(0).getSpecifics();
        if (!viewSpecifics.isEmpty()) {
            if (viewSpecifics.contains("Θέα σε πισίνα"))
                viewPool.setSelected(true);
            if (viewSpecifics.contains("Θέα σε παραλία"))
                viewBeach.setSelected(true);
            if (viewSpecifics.contains("Θέα σε θάλασσα"))
                viewSea.setSelected(true);
            if (viewSpecifics.contains("Θέα στο λιμάνι"))
                viewPort.setSelected(true);
            if (viewSpecifics.contains("Θέα στο βουνό"))
                viewMountain.setSelected(true);
            if (viewSpecifics.contains("Θέα στον δρόμο"))
                viewStreet.setSelected(true);
        }

        utilities.add(view);

        JPanel bath = new JPanel(new GridLayout(1,1));
        TitledBorder border1 = BorderFactory.createTitledBorder(" Μπάνιο ");
        bath.setBorder(border1);
        hairDryer = new JCheckBox("Πιστολάκι μαλλιών");
        bath.add(hairDryer);

        ArrayList<String> bathSpecifics = accommodation.getTypesOfUtilities().get(0).getSpecifics();
        if (!viewSpecifics.isEmpty()) {
            if (viewSpecifics.contains("Θέα σε πισίνα"))
                viewPool.setSelected(true);
            if (viewSpecifics.contains("Θέα σε παραλία"))
                viewBeach.setSelected(true);
            if (viewSpecifics.contains("Θέα σε θάλασσα"))
                viewSea.setSelected(true);
            if (viewSpecifics.contains("Θέα στο λιμάνι"))
                viewPort.setSelected(true);
            if (viewSpecifics.contains("Θέα στο βουνό"))
                viewMountain.setSelected(true);
            if (viewSpecifics.contains("Θέα στον δρόμο"))
                viewStreet.setSelected(true);
        }

        utilities.add(bath);

        JPanel washingClothes = new JPanel(new GridLayout(1,2));
        TitledBorder border2 = BorderFactory.createTitledBorder(" Πλύσιμο ρούχων ");
        washingClothes.setBorder(border2);
        clotheWasher = new JCheckBox("Πλυντήριο ρούχων,");
        dryer = new JCheckBox("Στεγνωτήριο");
        washingClothes.add(clotheWasher);
        washingClothes.add(dryer);

        utilities.add(washingClothes);

        JPanel entertainment = new JPanel(new GridLayout(1,1));
        TitledBorder border3 = BorderFactory.createTitledBorder(" Ψυχαγωγία ");
        entertainment.setBorder(border3);
        tv = new JCheckBox("Τηλεόραση");
        entertainment.add(tv);

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

        utilities.add(temperatureControl);

        JPanel internet = new JPanel(new GridLayout(1,2));
        TitledBorder border5 = BorderFactory.createTitledBorder(" Διαδίκτυο ");
        internet.setBorder(border5);
        wifi = new JCheckBox("Wifi");
        ethernet = new JCheckBox("Ethernet");
        internet.add(wifi);
        internet.add(ethernet);

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
        foodAreas.add(viewStreet);

        utilities.add(foodAreas);

        JPanel outsideSpace = new JPanel(new GridLayout(1,2));
        TitledBorder border7 = BorderFactory.createTitledBorder(" Εξωτερικός χώρος ");
        outsideSpace.setBorder(border7);
        balcony = new JCheckBox("Μπαλκόνι");
        yard = new JCheckBox("Αυλή");
        outsideSpace.add(balcony);
        outsideSpace.add(yard);

        utilities.add(outsideSpace);

        JPanel parkingSpace = new JPanel(new GridLayout(1,2));
        TitledBorder border8 = BorderFactory.createTitledBorder(" Χώρος στάθμευσης ");
        parkingSpace.setBorder(border8);
        freeParkingProperty = new JCheckBox("Δωρεάν χώρος στάθμευσης στην ιδιοκτησία");
        freeParkingStreet = new JCheckBox("Δωρεάν πάρκινγκ στο δρόμο");
        parkingSpace.add(freeParkingProperty);
        parkingSpace.add(freeParkingStreet);

        utilities.add(parkingSpace);





        generalPanel.add(new JScrollPane(utilities));
        //End of this hell-hole



        // ΟΛΟ αυτο θα δουλευει διαφορετικά για provider και simpleUser στον δευτερο πρεπει να εμφανίζεται η editable μορφή του evaluation άρα μπαίνει στις υποκλάσεις
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

    public ViewAccommodation(Provider provider) {
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Νέο κατάλυμα");

        JPanel generalPanel = new JPanel(new GridLayout(3,1)); //ΠΡΕΠΕΙ ΝΑ ΠΡΟΣΤΕΘΕΙ 4ο ΠΑΡΟΧΕΣ ΜΕ CHECKBOXES
        //GridLayout gridLayoutGeneral = new GridLayout(3,1);
        //setLayout(gridLayoutGeneral);

        accommodationDetails = new JPanel(new GridLayout(5,2,5,5));

        accommodationName = new JTextField("");
        accommodationName.setEditable(false); // αλλάζει σε true στην υποκλάση ViewAccommodationAsProvider
        accommodationName.setFont(accommodationName.getFont().deriveFont(Font.BOLD, 14f));

        accommodationRating = new JLabel("Βαθμολογία: -") ;

        description = new JTextField("");
        description.setEditable(false); // αλλάζει σε true στην υποκλάση ViewAccommodationAsProvider

        stayType = new JTextField("");
        stayType.setEditable(false); // αλλάζει σε true στην υποκλάση ViewAccommodationAsProvider

        town = new JTextField("");
        address = new JTextField("");
        postCode = new JTextField("");
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


        generalPanel.add(description);



        //Check boxes hell
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
        clotheWasher = new JCheckBox("Πλυντήριο ρούχων,");
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
        //End of this hell-hole

        add(generalPanel);

        add(viewSelectedEvaluation, BorderLayout.PAGE_END);
    }

}


