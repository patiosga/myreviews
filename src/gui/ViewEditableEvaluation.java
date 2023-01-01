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
                float grade;
                try {
                    grade = Float.parseFloat(gradeField.getText());
                    if (evaluationsManager.gradeOutOfBounds(grade) || gradeField.getText() == null)
                        JOptionPane.showMessageDialog(getParent(), "Παρακαλώ εισάγετε βαθμολογία μεταξύ του 1 και 5.");

                    else if (evaluationsManager.evaluationTextTooLong(evaluationText.getText()))
                        JOptionPane.showMessageDialog(getParent(), "Το κείμενο της αξιολόγησης δεν πρέπει να υπερβαίνει τους 500 χαρακτήρες");

                    else if (evaluationText.getText() == null)
                        JOptionPane.showMessageDialog(getParent(), "Το κείμενο της αξιολόγησης είναι υποχρεωτικό.");

                    else {
                        if (evaluationsManager.userAlreadyEvaluatedThis(user, accommodation))
                            evaluationsManager.alterEvaluation(oldEvaluation, grade, evaluationText.getText());
                        else evaluationsManager.addEvaluation(evaluationText.getText(), grade, user, accommodation);
                        JOptionPane.showMessageDialog(getParent(), "Επιτυχής υποβολή αξιολόγησης.");
                        dispose();
                    }


                } catch(NumberFormatException e1) {
                    JOptionPane.showMessageDialog(getParent(), "Παρακαλώ εισάγετε αριθμό στο πεδίο της βαθμολογίας.");
                }

            }
        });

        JButton deleteEvaluation = new JButton("Διαγραφή αξιολόγησης");
        deleteEvaluation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!evaluationsManager.removeEvaluation(oldEvaluation))
                    JOptionPane.showMessageDialog(getParent(), "Δεν έχει υποβληθεί η αξιολόγηση για να είναι δυνατή η διαγραφή της.");
                else
                    dispose();
            }
        });

        JPanel buttonsPanel = new JPanel(new GridLayout(1,2));
        buttonsPanel.add(submitEvaluation);
        buttonsPanel.add(deleteEvaluation);
        add(buttonsPanel, BorderLayout.PAGE_END); // Κανονικά εδώ θα υπήρχε η επεξεργασία υποβολής αλλά το κάνει "override"



        setVisible(true);
}
}
