package gui;

import api.*;
import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

        JPanel generalPanel = new JPanel(new GridLayout(1,1));

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
                accommodationsManager.updateAllAvgRatings(evaluationsManager.getEvaluations());
                user.updateAvgRatingOfAllAccom(evaluationsManager.getEvaluations());
                avgRating.setText("Μέση βαθμολογία των καταλυμάτων σας: " + user.getAvgRatingOfAllAccom() + evaluationsManager.getProvidersNumOfEvaluations(user));
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

        add(generalPanel);
        accommodationsManager.updateAllAvgRatings(evaluationsManager.getEvaluations());
        user.updateAvgRatingOfAllAccom(evaluationsManager.getEvaluations());
        avgRating = new JLabel("Μέση βαθμολογία των καταλυμάτων σας: " + user.getAvgRatingOfAllAccom() + "("+evaluationsManager.getProvidersNumOfEvaluations(user) + ")");

        JPanel upperPanel = new JPanel(new GridLayout(1,2));
        upperPanel.add(avgRating);
        upperPanel.add(logOut);
        add(upperPanel, BorderLayout.PAGE_START);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                accommodationsManager.saveToOutputFiles();
                evaluationsManager.saveToOutputFiles();
                usersManager.saveToOutputFiles();
                System.exit(0);
            }
        });

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
