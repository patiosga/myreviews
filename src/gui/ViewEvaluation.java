package gui;

import api.Accommodation;
import api.Evaluation;
import api.SimpleUser;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.Calendar;

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


        JPanel generalPanel = new JPanel(new GridLayout(2,1));
        JPanel informationPanel = new JPanel(new GridLayout(2,2));
        informationPanel.add(new JLabel(evaluation.getUser().getFirstName()));
        informationPanel.add(new JLabel(evaluation.getCurrentDate()));
        informationPanel.add(new JLabel(" Βαθμολογία:"));
        gradeField = new JTextField(Float.toString(evaluation.getGrade()));
        gradeField.setEditable(false);
        informationPanel.add(gradeField);

        generalPanel.add(informationPanel);

        evaluationText = new JTextField(evaluation.getEvaluationText());
        evaluationText.setEditable(false);
        generalPanel.add(evaluationText);

        add(generalPanel);


        setVisible(true);

    }

    public ViewEvaluation(SimpleUser user) {
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Αξιολόγηση");


        JPanel generalPanel = new JPanel(new GridLayout(2,1));
        JPanel informationPanel = new JPanel(new GridLayout(2,2));
        informationPanel.add(new JLabel(user.getFirstName()));

        DateFormat Date = DateFormat.getDateInstance();
        Calendar cals = Calendar.getInstance();
        informationPanel.add(new JLabel(Date.format(cals.getTime())));

        informationPanel.add(new JLabel(" Βαθμολογία:"));
        gradeField = new JTextField("");
        gradeField.setEditable(false);
        informationPanel.add(gradeField);

        generalPanel.add(informationPanel);

        evaluationText = new JTextField("");
        evaluationText.setEditable(false);
        generalPanel.add(evaluationText);

        add(generalPanel);

        //setVisible στις υποκλάσεις
    }
}
