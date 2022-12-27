package gui;

import api.Evaluation;

import javax.swing.*;
import java.awt.*;

public class ViewEvaluation extends JFrame { // θα επιμηκυνθεί με κουμπιά για να την επεξεργάζεται ο χρήστης
    protected Evaluation evaluation;
    protected JTextField gradeField;
    protected JTextField evaluationText;

    public ViewEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;

        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Αξιολόγηση");


        GridLayout generalLayout = new GridLayout(2,1);
        setLayout(generalLayout);
        JPanel informationPanel = new JPanel(new GridLayout(2,2));
        informationPanel.add(new JLabel(evaluation.getUser().getFirstName()));
        informationPanel.add(new JLabel(evaluation.getCurrentDate()));
        informationPanel.add(new JLabel(" Βαθμολογία:"));
        gradeField = new JTextField(Float.toString(evaluation.getGrade()));
        gradeField.setEditable(false);
        informationPanel.add(gradeField);

        add(informationPanel);

        evaluationText = new JTextField(evaluation.getEvaluationText());
        evaluationText.setEditable(false);
        add(evaluationText);

        setVisible(true);

    }
}
