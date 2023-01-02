package gui;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;


public class searchAccommodations extends ViewAccommodation {
    private ArrayList<Accommodation> results;

    public searchAccommodations(SimpleUser user, ManageEvaluations evaluationsManager, ManageAccommodations accommodationsManager){
        super();

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

        JButton search = new JButton("Αναζήτηση");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortOutCheckBoxHell();
                makeResultingAccommodationList(accommodationsManager);
            }
        });

        JButton showAccommodation = new JButton("Δείτε το κατάλυμα");
        showAccommodation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (resultingAccommodations.getSelectedIndex() != -1) {
                    new ViewAccommodation(results.get(resultingAccommodations.getSelectedIndex()), user, evaluationsManager, accommodationsManager, true);
                }
            }
        });

        JPanel bottomButtons = new JPanel(new GridLayout(1,2));
        bottomButtons.add(search);
        bottomButtons.add(showAccommodation);

        add(bottomButtons, BorderLayout.PAGE_END);

        setVisible(true);
    }

    private void makeResultingAccommodationList(ManageAccommodations accommodationsManager) {
        results = accommodationsManager.searchAccommodations(accommodationName.getText(), stayType.getText(), town.getText(), generalUtilities);
        Vector<String> model = new Vector<>();
        if (!results.isEmpty()) {
            for (Accommodation accommodation : results) {
                model.addElement(accommodation.toString());
            }
        }
        resultingAccommodations.setListData(model);
    }
}
