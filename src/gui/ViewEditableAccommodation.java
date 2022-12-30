package gui;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewEditableAccommodation extends ViewAccommodation{
    public ViewEditableAccommodation(Accommodation accommodation, Provider provider, ManageEvaluations evaluationsManager, ManageAccommodations accommodationsManager) {
        super(accommodation, provider, evaluationsManager, accommodationsManager, false);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        viewPool.setEnabled(true);
        viewBeach.setEnabled(true);
        viewSea.setEnabled(true);
        viewPort.setEnabled(true);
        viewMountain.setEnabled(true);
        viewStreet.setEnabled(true);
        hairDryer.setEnabled(true);
        clotheWasher.setEnabled(true);
        dryer.setEnabled(true);
        tv.setEnabled(true);
        firePlace.setEnabled(true);
        airCondition.setEnabled(true);
        centralHeat.setEnabled(true);
        wifi.setEnabled(true);
        ethernet.setEnabled(true);
        kitchen.setEnabled(true);
        fridge.setEnabled(true);
        microwave.setEnabled(true);
        kitchenware.setEnabled(true);
        spoonsKnives.setEnabled(true);
        plateWasher.setEnabled(true);
        coffeeMachine.setEnabled(true);
        balcony.setEnabled(true);
        yard.setEnabled(true);
        freeParkingProperty.setEnabled(true);
        freeParkingStreet.setEnabled(true);

        accommodationName.setEditable(true);
        description.setEditable(true);
        stayType.setEditable(true);
        town.setEditable(true);
        address.setEditable(true);
        postCode.setEditable(true);

        JButton submitAccommodation = new JButton("Υποβολή");
        String checkText = checkSubmissionInaccuracies();

        submitAccommodation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String checkText = checkSubmissionInaccuracies();
                if (checkText != null)
                    JOptionPane.showMessageDialog(getParent(), checkText);
                else {
                    if (accommodationsManager.accommodationExists(accommodation)) {
                        accommodationsManager.alterAccommodationDetails(accommodation, accommodationName.getText(), description.getText(),
                                stayType.getText(), address.getText(), town.getText(), postCode.getText());
                    }
                    else {
                        accommodationsManager.addAccommodation(accommodationName.getText(), description.getText(), stayType.getText(),
                                address.getText(), town.getText(), postCode.getText(), provider);
                    }
                    sortOutCheckBoxHell(accommodation, accommodationsManager);
                    dispose();
                }

            }
        });

        JButton deleteAccommodation = new JButton("Διαγραφή καταλύματος");
        deleteAccommodation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!accommodationsManager.removeAccommodation(accommodation))
                    JOptionPane.showMessageDialog(getParent(), "Δεν έχει υποβληθεί το κατάλυμα για να είναι δυνατή η διαγραφή του.");
                else
                    dispose();
            }
        });

        JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
        buttonsPanel.add(submitAccommodation);
        buttonsPanel.add(deleteAccommodation);
        add(buttonsPanel, BorderLayout.PAGE_END);

        setVisible(true);

    }

    private String checkSubmissionInaccuracies() { //επιστρέφει null αν όλα καλά
        if (accommodationName.getText().length() == 0 || stayType.getText().length() == 0 || description.getText().length() == 0 || town.getText().length() == 0 || postCode.getText().length() == 0 || address.getText().length() == 0)
            return "Τα πεδία κειμένου είναι υποχρεωτικά για να υποβάλετε επιτυχώς το νέο σας κατάλυμα.";
        else if (!stayType.getText().equals("Ξενοδοχείο")  && !stayType.getText().equals("Διαμέρισμα") && !stayType.getText().equals("Μεζονέτα"))
            return "Παρακαλώ δηλώστε τον τύπο του καταλύματος ως Ξενοδοχείο, Διαμέρισμα ή Μεζονέτα." + stayType.getText() + ".";
        float postC;
        try {
            postC = Float.parseFloat(postCode.getText());
            if (postC <= 0)
                return "Εισάγετε έγκυρο ταχυδρομικό κώδικα";
        } catch(NumberFormatException e1) {
            return "Παρακαλώ εισάγετε αριθμό στο πεδίο του ταχυδρομικού κώδικα.";
        }
        return null; //περίπτωση του όλα καλά
    }

    private void sortOutCheckBoxHell(Accommodation accommodation, ManageAccommodations accommodationsManager) {
        ArrayList<Utility> generalUtilities = new ArrayList<>();
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

        accommodationsManager.alterAccommodationUtilities(accommodation, generalUtilities);
    }
}
