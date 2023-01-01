package gui;

import api.*;

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

    public dashboardProvider(Provider user, ManageAccommodations accommodationsManager ,ManageEvaluations evaluationsManager, ManageUsers usersManager) {

        this.user = user;
        accommodationsList = new JList<>();

        JPanel generalPanel = new JPanel(new GridLayout(2,1));

        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        makeEvaluationList(accommodationsManager);
        generalPanel.add(new JScrollPane(accommodationsList));

        JButton viewEvaluation = new JButton("Δείτε την αξιολόγηση");
        viewEvaluation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (accommodationsList.getSelectedIndex() != -1) {
                    new ViewAccommodation(accommodationsOfProvider.get(accommodationsList.getSelectedIndex()), user, evaluationsManager, accommodationsManager, true);
                }

            }
        });

        JButton reload = new JButton("Ανανέωση");
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeEvaluationList(accommodationsManager);
            }
        });

        JPanel bottomButtonsPanel = new JPanel(new GridLayout(1, 2));
        bottomButtonsPanel.add(viewEvaluation);
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
        add(logOut, BorderLayout.PAGE_START);

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Empty for now"));
        generalPanel.add(searchPanel);
        add(generalPanel);

        setVisible(true);
    }

    private void makeEvaluationList(ManageAccommodations accommodationsManager) {
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
