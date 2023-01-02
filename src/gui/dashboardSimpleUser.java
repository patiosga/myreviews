package gui;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class dashboardSimpleUser extends JFrame{
    private ArrayList<Evaluation> evaluationsOfUser;
    private final SimpleUser user;
    private JList<String> evaluationList;
    private JLabel avgRating;

    public dashboardSimpleUser(SimpleUser user, ManageEvaluations evaluationsManager, ManageUsers usersManager, ManageAccommodations accommodationsManager) {

        this.user = user;
        evaluationList = new JList<>();

        JPanel generalPanel = new JPanel(new GridLayout(2,1));

        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Αρχική σελίδα χρήστη");

        makeEvaluationList(evaluationsManager);
        generalPanel.add(new JScrollPane(evaluationList));

        JButton viewEvaluation = new JButton("Δείτε την αξιολόγηση");
        viewEvaluation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (evaluationList.getSelectedIndex() != -1) {
                    new ViewEvaluation(evaluationsOfUser.get(evaluationList.getSelectedIndex()), user, evaluationsManager, accommodationsManager, true);
                }
            }
        });

        JButton search = new JButton("Αναζητήστε καταλύματα");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new searchAccommodations(user, evaluationsManager, accommodationsManager);
            }
        });

        JButton reload = new JButton("Ανανέωση");
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.updateAvgRatingOfUser(evaluationsManager.getEvaluations());
                avgRating.setText("Μέση βαθμολογία αξιολογήσεων: " + user.getAvgRating());
                makeEvaluationList(evaluationsManager);
            }
        });

        JPanel bottomButtonsPanel = new JPanel(new GridLayout(1, 3));
        bottomButtonsPanel.add(viewEvaluation);
        bottomButtonsPanel.add(search);
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

        avgRating = new JLabel("Μέση βαθμολογία αξιολογήσεων: " + user.getAvgRating());

        JPanel upperPanel = new JPanel(new GridLayout(1,2));
        upperPanel.add(avgRating);
        upperPanel.add(logOut);
        add(upperPanel, BorderLayout.PAGE_START);


        //Αναζήτηση καταλυμάτων


        setVisible(true);
    }

    private void makeEvaluationList(ManageEvaluations evaluationsManager) {
        evaluationsOfUser = evaluationsManager.getUserEvaluations(user);
        Vector<String> model = new Vector<>();
        if (!evaluationsOfUser.isEmpty()) {
            for (Evaluation evaluation : evaluationsOfUser) {
                model.addElement(evaluation.toString());
            }
        }
        evaluationList.setListData(model);
    }
}
