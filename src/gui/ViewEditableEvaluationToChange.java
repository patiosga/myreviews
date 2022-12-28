package gui;

import api.Evaluation;
import api.ManageEvaluations;
import api.SimpleUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewEditableEvaluationToChange extends ViewEvaluation {
    private JButton saveChanges;
    private ManageEvaluations evaluationsManager;

    public ViewEditableEvaluationToChange(Evaluation evaluation, ManageEvaluations evaluationsManager) {
        super(evaluation);

        setVisible(false);
        gradeField.setEditable(true);
        evaluationText.setEditable(true);
        saveChanges = new JButton("Αποθήκευσε αλλαγές");

        saveChanges.addActionListener(new ActionListener() {
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

                    else evaluationsManager.alterEvaluation(evaluation, grade, evaluationText.getText());

                } catch(NumberFormatException e1) {
                    JOptionPane.showMessageDialog(getParent(), "Παρακαλώ εισάγετε αριθμό στο πεδίο της βαθμολογίας.");
                }

            }
        });
        add(saveChanges);
        setVisible(true);
    }

    private boolean emptyTextField(String text) {return text.length() == 0;}
}
