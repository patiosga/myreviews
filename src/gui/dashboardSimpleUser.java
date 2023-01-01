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

    public dashboardSimpleUser(SimpleUser user, ManageEvaluations evaluationsManager, ManageUsers usersManager, ManageAccommodations accommodationsManager) {

        this.user = user;
        evaluationList = new JList<>();

        JPanel generalPanel = new JPanel(new GridLayout(2,1));

        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        makeEvaluationList(evaluationsManager);
        generalPanel.add(new JScrollPane(evaluationList));

        JButton viewEvaluation = new JButton("Δείτε την αξιολόγηση");
        viewEvaluation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (evaluationList.getSelectedIndex() != -1) {
                    new ViewEvaluation(evaluationsOfUser.get(evaluationList.getSelectedIndex()), user, evaluationsManager, true);
                }

            }
        });

        JButton reload = new JButton("Ανανέωση");
        reload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeEvaluationList(evaluationsManager);
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
