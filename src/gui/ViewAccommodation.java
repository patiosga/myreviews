package gui;

import api.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class ViewAccommodation extends JFrame {
    protected JList<String> evaluationList; //Έχει τα String των αξιολογήσεων και γίνεται η αντιστοίχιση με τον evaluationsOfAccommodation μέσω getSelectedIndex()
    protected JList<String> resultingAccommodations; //Για τα String των καταλυμάτων στα αποτελέσματα της αναζήτησης του χρήστη
    protected ArrayList<Evaluation> evaluationsOfAccommodation;
    protected ArrayList<Utility> generalUtilities; //Για την επεξεργασία των checkBoxes σε παιδιά κλάσεις
    protected JPanel accommodationDetails;
    protected JPanel generalPanel;
    protected JTextField accommodationName;
    protected JLabel accommodationRating;
    protected JTextArea description;
    protected JTextField stayType;
    protected JTextField town;
    protected JTextField address;
    protected JTextField postCode;
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


    public ViewAccommodation(Accommodation accommodation, User user, ManageEvaluations evaluationsManager, ManageAccommodations accommodationsManager, boolean withButtons) {
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Κατάλυμα: " + accommodation.getName());

        generalPanel = new JPanel(new GridLayout(4,1));
        evaluationList = new JList<>();

        showAccommodationDetails(accommodation);

        accommodationRating.setText("Βαθμολογία: " + accommodation.getAvgRating() + " (" + accommodation.getTotalEvaluations() + ")");
        makeEvaluationList(evaluationsManager,accommodation);
        generalPanel.add(new JScrollPane(evaluationList));
        //we do this to hide the shame brought upon us
        makeEmptyCheckBoxes();
        fillCheckBoxes(accommodation);


        JButton viewSelectedEvaluation = new JButton("Δείτε την αξιολόγηση");
        viewSelectedEvaluation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (evaluationList.getSelectedIndex() != -1) {
                    ViewEvaluation view = new ViewEvaluation(evaluationsOfAccommodation.get(evaluationList.getSelectedIndex()), user, evaluationsManager, accommodationsManager, true);
                }
            }
        });

        JButton editAccommodation = new JButton("Επεξεργασία καταλύματος");
        editAccommodation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewEditableAccommodation(accommodation, (Provider) user, evaluationsManager, accommodationsManager);
            }
        });

        add(generalPanel);

        JPanel bottomButtonsPanel = new JPanel(new GridLayout(1,1)); // 1 ή 2 columns ανάλογα με το αν ανήκει στον χρήστη το κατάλυμα
        if (accommodationsManager.isProvidersAccommodation(user, accommodation)) { //Το κουμπί επεξεργασίας εμφανίζεται μόνο αν το κατάλυμα είναι του χρήστη
            bottomButtonsPanel = new JPanel(new GridLayout(1,2));
            bottomButtonsPanel.add(editAccommodation);
        }

        bottomButtonsPanel.add(viewSelectedEvaluation);
        if (withButtons)
            add(bottomButtonsPanel, BorderLayout.PAGE_END);

        JButton evaluateAccommodation = new JButton("Αξιολογήστε το κατάλυμα");
        evaluateAccommodation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (evaluationsManager.userAlreadyEvaluatedThis((SimpleUser) user, accommodation))
                    JOptionPane.showMessageDialog(getParent(), "Εχετε ήδη αξιολογήσει αυτό το κατάλυμα");
                else {
                    Evaluation tempEvaluation = new Evaluation("", 0, (SimpleUser) user, accommodation);
                    new ViewEditableEvaluation(tempEvaluation, accommodation, (SimpleUser) user, evaluationsManager, accommodationsManager);
                }
            }
        });

        JButton reload = new JButton("Ανανέωση");
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeEvaluationList(evaluationsManager, accommodation);
                accommodationRating.setText("Βαθμολογία: " + Float.toString(accommodation.getAvgRating()) + " (" + Integer.toString(accommodation.getTotalEvaluations()) + ")");
                accommodationName.setText(accommodation.getName());
                description.setText(accommodation.getDescription());
                town.setText(accommodation.getLocation().getTown());
                postCode.setText(accommodation.getLocation().getPostCode());
                address.setText(accommodation.getLocation().getAddress());
                fillCheckBoxes(accommodation);
            }
        });

        JPanel upperButtonsPanel = new JPanel(new GridLayout(1,1)); // 1 ή 2 columns ανάλογα με το αν είναι SimpleUser και άρα μπορεί να αξιολογήσει το κατάλυμα
        if (user instanceof SimpleUser) { //Το κουμπί προσθήκης αξιολόγησης εμφανίζεται μόνο αν το βλέπει SimpleUser
            upperButtonsPanel = new JPanel(new GridLayout(1,2));
            upperButtonsPanel.add(evaluateAccommodation);
        }
        upperButtonsPanel.add(reload);
        add(upperButtonsPanel, BorderLayout.PAGE_START);


        setVisible(true);
    }

    public ViewAccommodation(SimpleUser user, ManageEvaluations evaluationsManager, ManageAccommodations accommodationsManager) { //Για επαναχρησιμοποίηση φόρμας σε αναζήτηση καταλυμάτων
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Αναζήτηση καταλύματος...");

        generalPanel = new JPanel(new GridLayout(3,1)); // 3 rows. 1: Πεδία string, 2: Επιθυμητές παροχές, 3: Λίστα αποτελεσμάτων
        resultingAccommodations = new JList<>();

        accommodationDetails = new JPanel(new GridLayout(3,2));

        accommodationName = new JTextField();
        accommodationName.setFont(accommodationName.getFont().deriveFont(Font.BOLD, 14f));
        stayType = new JTextField();
        town = new JTextField();

        accommodationDetails.add(new JLabel(" Όνομα:"));
        accommodationDetails.add(accommodationName);
        accommodationDetails.add(new JLabel(" Τύπος:"));
        accommodationDetails.add(stayType);
        accommodationDetails.add(new JLabel(" Πόλη:"));
        accommodationDetails.add(town);
        generalPanel.add(accommodationDetails);

        makeEmptyCheckBoxes();

        generalPanel.add(new JScrollPane(resultingAccommodations));
        add(generalPanel);
    }


    protected void showAccommodationDetails(Accommodation accommodation) {

        accommodationDetails = new JPanel(new GridLayout(5,2));

        accommodationName = new JTextField(accommodation.getName());
        accommodationName.setEditable(false); // αλλάζει σε true στην υποκλάση ViewEditableAccommodation
        accommodationName.setFont(accommodationName.getFont().deriveFont(Font.BOLD, 14f));

        accommodationRating = new JLabel(); // συμπληρώνεται ανάλογα με την περίσταση

        description = new JTextArea(accommodation.getDescription());
        description.setEditable(false); // αλλάζει σε true στην υποκλάση ViewEditableAccommodationAsProvider


        stayType = new JTextField(accommodation.getStayType());
        stayType.setEditable(false); // αλλάζει σε true στην υποκλάση ViewEditableAccommodationAsProvider

        town = new JTextField(accommodation.getLocation().getTown());
        address = new JTextField(accommodation.getLocation().getAddress());
        postCode = new JTextField(accommodation.getLocation().getPostCode());
        town.setEditable(false);
        address.setEditable(false);
        postCode.setEditable(false); // αλλάζουν σε true στην υποκλάση ViewEditableAccommodation


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
        Vector<String> helperList = new Vector<>();
        if (!evaluationsOfAccommodation.isEmpty()) {
            for (Evaluation evaluation : evaluationsOfAccommodation) {
                helperList.addElement(evaluation.toString());
            }
        }
        evaluationList.setListData(helperList);
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

    protected ArrayList<Utility> sortOutCheckBoxHell() {
        generalUtilities = new ArrayList<>();
        Utility view = new Utility();
        if (viewPool.isSelected())
            view.addSpecificUtility("Θέα σε πισίνα");
        if (viewBeach.isSelected())
            view.addSpecificUtility("Θέα σε παραλία");
        if (viewSea.isSelected())
            view.addSpecificUtility("Θέα στη θάλασσα");
        if (viewPort.isSelected())
            view.addSpecificUtility("Θέα στο λιμάνι");
        if (viewMountain.isSelected())
            view.addSpecificUtility("Θέα στο βουνό");
        if (viewStreet.isSelected())
            view.addSpecificUtility("Θέα στον δρόμο");
        generalUtilities.add(view);

        Utility bath = new Utility();
        if (hairDryer.isSelected())
            bath.addSpecificUtility("Πιστολάκι μαλλιών");
        generalUtilities.add(bath);

        Utility washingClothes = new Utility();
        if (clotheWasher.isSelected())
            washingClothes.addSpecificUtility("Πλυντήριο ρούχων");
        if (dryer.isSelected())
            washingClothes.addSpecificUtility("Στεγνωτήριο");
        generalUtilities.add(washingClothes);

        Utility entertainment = new Utility();
        if (tv.isSelected())
            entertainment.addSpecificUtility("Τηλεόραση");
        generalUtilities.add(entertainment);

        Utility temperatureControl = new Utility();
        if (firePlace.isSelected())
            temperatureControl.addSpecificUtility("Εσωτερικό τζάκι");
        if (airCondition.isSelected())
            temperatureControl.addSpecificUtility("Κλιματισμός");
        if (centralHeat.isSelected())
            temperatureControl.addSpecificUtility("Κεντρική θέρμανση");
        generalUtilities.add(temperatureControl);

        Utility internet = new Utility();
        if (wifi.isSelected())
            internet.addSpecificUtility("Wifi");
        if (ethernet.isSelected())
            internet.addSpecificUtility("Ethernet");
        generalUtilities.add(internet);

        Utility foodAreas = new Utility();
        if (kitchen.isSelected())
            foodAreas.addSpecificUtility("Κουζίνα");
        if (fridge.isSelected())
            foodAreas.addSpecificUtility("Ψυγείο");
        if (microwave.isSelected())
            foodAreas.addSpecificUtility("Φούρνος μικροκυμάτων");
        if (kitchenware.isSelected())
            foodAreas.addSpecificUtility("Μαγειρικά είδη");
        if (spoonsKnives.isSelected())
            foodAreas.addSpecificUtility("Πιάτα και μαχαιροπίρουνα");
        if (plateWasher.isSelected())
            foodAreas.addSpecificUtility("Πλυντήριο πιάτων");
        if (coffeeMachine.isSelected())
            foodAreas.addSpecificUtility("Καφετιέρα");
        generalUtilities.add(foodAreas);

        Utility outsideSpace = new Utility();
        if (balcony.isSelected())
            outsideSpace.addSpecificUtility("Μπαλκόνι");
        if (yard.isSelected())
            outsideSpace.addSpecificUtility("Αυλή");
        generalUtilities.add(outsideSpace);

        Utility parkingSpace = new Utility();
        if (freeParkingProperty.isSelected())
            parkingSpace.addSpecificUtility("Δωρεάν χώρος στάθμευσης στην ιδιοκτησία");
        if (freeParkingStreet.isSelected())
            parkingSpace.addSpecificUtility("Δωρεάν πάρκινγκ στο δρόμο");
        generalUtilities.add(parkingSpace);

        return generalUtilities;
    }

}


