package gui;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewEditableAccommodation extends ViewAccommodation {
    ArrayList<Utility> generalUtilities;
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

        submitAccommodation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String checkText = accommodationsManager.checkSubmissionInaccuracies(accommodationName.getText(), description.getText(), stayType.getText(), town.getText(), address.getText(), postCode.getText());
                if (checkText != null)
                    JOptionPane.showMessageDialog(getParent(), checkText);
                else {
                    generalUtilities = sortOutCheckBoxHell();
                    ArrayList<Utility> eternalUtilities = new ArrayList<>(); //Utilities που μένουν μετά το κλείσιμο του παραθύρου
                    for (Utility utility : generalUtilities) {
                        Utility tempUtility = new Utility();
                        for (String specific : utility.getSpecifics()) {
                            tempUtility.addSpecificUtility(specific);
                        }
                        eternalUtilities.add(tempUtility);
                    }

                    if (accommodationsManager.accommodationExists(accommodation)) {
                        accommodationsManager.alterAccommodationDetails(accommodation, accommodationName.getText(), description.getText(),
                                stayType.getText(), address.getText(), town.getText(), postCode.getText());
                        accommodationsManager.alterAccommodationUtilities(accommodation, eternalUtilities);
                    }
                    else {
                        accommodationsManager.addAccommodation(accommodationName.getText(), description.getText(), stayType.getText(),
                                address.getText(), town.getText(), postCode.getText(), eternalUtilities, provider);
                    }
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
                else {
                    evaluationsManager.removedAccommodationAlert(accommodation);
                    dispose();
                }
            }
        });

        JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
        buttonsPanel.add(submitAccommodation);
        buttonsPanel.add(deleteAccommodation);
        add(buttonsPanel, BorderLayout.PAGE_END);

        setVisible(true);

    }
}
