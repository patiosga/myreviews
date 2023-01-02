package gui;

import api.*;
import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class dashboardProvider extends JFrame {
    private ArrayList<Accommodation> accommodationsOfProvider;
    private final Provider user;
    private JList<String> accommodationsList;
    private JLabel avgRating;

    public dashboardProvider(Provider user, ManageAccommodations accommodationsManager ,ManageEvaluations evaluationsManager, ManageUsers usersManager) {

        this.user = user;
        accommodationsList = new JList<>();

        JPanel generalPanel = new JPanel(new GridLayout(2,1));

        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Αρχική σελίδα παρόχου");

        makeAccommodationList(accommodationsManager);
        generalPanel.add(new JScrollPane(accommodationsList));

        JButton viewAccommodation = new JButton("Δείτε το κατάλυμα");
        viewAccommodation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (accommodationsList.getSelectedIndex() != -1) {
                    new ViewAccommodation(accommodationsOfProvider.get(accommodationsList.getSelectedIndex()), user, evaluationsManager, accommodationsManager, true);
                }
            }
        });

        JButton addAccommodation = new JButton("Προσθήκη καταλύματος");
        addAccommodation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Location loc = new Location("","","");
                Accommodation newAccommodation = new Accommodation("", "Εισάγετε μια περιγραφή για το νέο σας κατάλυμα", "", loc, user);

                new ViewEditableAccommodation(newAccommodation, user, evaluationsManager, accommodationsManager);
            }
        });

        JButton reload = new JButton("Ανανέωση");
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.updateAvgRatingOfAllAccom(evaluationsManager.getEvaluations());
                avgRating.setText("Μέση βαθμολογία των καταλυμάτων σας: " + user.getAvgRatingOfAllAccom());
                makeAccommodationList(accommodationsManager);
            }
        });

        JPanel bottomButtonsPanel = new JPanel(new GridLayout(1, 3));
        bottomButtonsPanel.add(viewAccommodation);
        bottomButtonsPanel.add(addAccommodation);
        bottomButtonsPanel.add(reload);
        add(bottomButtonsPanel, BorderLayout.PAGE_END);

        JButton logOut = new JButton("Αποσύνδεση");
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StartingPage(usersManager, evaluationsManager, accommodationsManager);
                dispose();
            }
        });

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Empty for now"));
        generalPanel.add(searchPanel);
        add(generalPanel);

        avgRating = new JLabel("Μέση βαθμολογία των καταλυμάτων σας: " + user.getAvgRatingOfAllAccom());

        JPanel upperPanel = new JPanel(new GridLayout(1,2));
        upperPanel.add(avgRating);
        upperPanel.add(logOut);
        add(upperPanel, BorderLayout.PAGE_START);

        setVisible(true);
    }

    private void makeAccommodationList(ManageAccommodations accommodationsManager) {
        accommodationsOfProvider = accommodationsManager.getProvidersAccommodations(user);
        Vector<String> model = new Vector<>();
        if (!accommodationsOfProvider.isEmpty()) {
            for (Accommodation accommodation : accommodationsOfProvider) {
                model.addElement(accommodation.toString());
            }
        }
        accommodationsList.setListData(model);
    }
}
