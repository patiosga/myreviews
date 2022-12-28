package gui;

import api.Accommodation;
import api.ManageEvaluations;
import api.SimpleUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewEditableEvaluationToCreate extends ViewEvaluation {
    private JButton submitEvaluation;

    public ViewEditableEvaluationToCreate(Accommodation accommodation, SimpleUser user, ManageEvaluations evaluationsManager) {
        super(user);

        setVisible(false);
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

                    else if (evaluationsManager.userAlreadyEvaluatedThis(user,accommodation))
                        JOptionPane.showMessageDialog(getParent(), "Έχετε ήδη αξιολογήσει αυτό το κατάλυμα.");

                    else {
                        evaluationsManager.addEvaluation(evaluationText.getText(), grade, user, accommodation);
                        JOptionPane.showMessageDialog(getParent(), "Επιτυχής υποβολή αξιολόγησης.");
                        dispose();
                    }


                } catch(NumberFormatException e1) {
                    JOptionPane.showMessageDialog(getParent(), "Παρακαλώ εισάγετε αριθμό στο πεδίο της βαθμολογίας.");
                }

            }
        });
        add(submitEvaluation, BorderLayout.PAGE_END);
        setVisible(true);
}
}
