package gui;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewEditableEvaluation extends ViewEvaluation {
    private JButton submitEvaluation;

    public ViewEditableEvaluation(Evaluation oldEvaluation, Accommodation accommodation, SimpleUser user, ManageEvaluations evaluationsManager, ManageAccommodations accommodationsManager) {
        super(oldEvaluation, user, evaluationsManager, accommodationsManager, false);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        gradeField.setEditable(true);
        evaluationText.setEditable(true);
        submitEvaluation = new JButton("Υποβολή");

        submitEvaluation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String checkMessage = evaluationsManager.checkSubmissionInaccuracies(evaluationText.getText(), gradeField.getText());

                if (checkMessage != null)
                    JOptionPane.showMessageDialog(getParent(), checkMessage);

                else {
                    float grade;
                    grade = Float.parseFloat(gradeField.getText());
                    if (evaluationsManager.userAlreadyEvaluatedThis(user, accommodation))
                        evaluationsManager.alterEvaluation(oldEvaluation, grade, evaluationText.getText());
                    else evaluationsManager.addEvaluation(evaluationText.getText(), grade, user, accommodation);
                    JOptionPane.showMessageDialog(getParent(), "Επιτυχής υποβολή αξιολόγησης.");
                    dispose();
                }
            }
        });

        JButton deleteEvaluation = new JButton("Διαγραφή αξιολόγησης");
        deleteEvaluation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!evaluationsManager.removeEvaluation(oldEvaluation))
                    JOptionPane.showMessageDialog(getParent(), "Δεν έχει υποβληθεί η αξιολόγηση για να είναι δυνατή η διαγραφή της.");
                else {
                    dispose();
                }
            }
        });

        JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
        buttonsPanel.add(submitEvaluation);
        buttonsPanel.add(deleteEvaluation);
        add(buttonsPanel, BorderLayout.PAGE_END); // Κανονικά εδώ θα υπήρχε η επεξεργασία υποβολής αλλά το κάνει "override"



        setVisible(true);
}
}
