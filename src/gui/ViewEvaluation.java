package gui;

import api.*;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Calendar;

public class ViewEvaluation extends JFrame {
    protected JTextField gradeField;
    protected JTextArea evaluationText;

    public ViewEvaluation(Evaluation evaluation, User user, ManageEvaluations evaluationsManager, ManageAccommodations accommodationsManager, boolean withButtons) {

        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Αξιολόγηση");


        JPanel generalPanel = new JPanel(new GridLayout(2,1));
        JPanel informationPanel = new JPanel(new GridLayout(2,2));
        informationPanel.add(new JLabel(evaluation.getUser().getFirstName()));
        informationPanel.add(new JLabel(evaluation.getCurrentDate()));
        informationPanel.add(new JLabel(" Βαθμολογία:"));
        gradeField = new JTextField(Float.toString(evaluation.getGrade()));
        gradeField.setEditable(false);
        informationPanel.add(gradeField);

        generalPanel.add(informationPanel);

        evaluationText = new JTextArea(evaluation.getEvaluationText());
        evaluationText.setEditable(false);
        generalPanel.add(evaluationText);

        add(generalPanel);

        JButton editEvaluation = new JButton("Επεξεργασία αξιολόγησης");
        editEvaluation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewEditableEvaluation editableEvaluation = new ViewEditableEvaluation(evaluation, evaluation.getAccommodation(), (SimpleUser) user, evaluationsManager, accommodationsManager);
            }
        });

        JButton seeAccommodation = new JButton("Δείτε το αξιολογούμενο κατάλυμα");
        seeAccommodation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewAccommodation(evaluation.getAccommodation(), user, evaluationsManager, accommodationsManager, true);
            }
        });
        add(seeAccommodation, BorderLayout.PAGE_START);

        if (evaluationsManager.isUsersEvaluation(user, evaluation) && withButtons) { //Το κουμπί επεξεργασίας εμφανίζεται μόνο αν η αξιολόγηση είναι του χρήστη
            add(editEvaluation, BorderLayout.PAGE_END);
        }


        setVisible(true);

    }
}
