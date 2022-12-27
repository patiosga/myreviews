package gui;

import api.Accommodation;
import api.Evaluation;
import api.ManageEvaluations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewAccommodation extends JFrame {
    protected Accommodation accommodation;
    protected JPanel accommodationDetails;
    protected JList<String> evaluationList; //Έχει τα String των αξιολογήσεων και γίνεται η αντιστοίχιση μέσω getSelectedIndex()
    protected ArrayList<Evaluation> evaluationsOfAccommodation;
    protected ManageEvaluations evaluationManager;
    protected JTextField accommodationName;
    protected JLabel accommodationRating;
    protected JTextField description;
    protected JTextField stayType;
    protected JTextField town;
    protected JTextField address;
    protected JTextField postCode;
    protected JButton viewSelectedEvaluation;


    public ViewAccommodation(Accommodation accommodation, ManageEvaluations evaluationManager) {
        this.accommodation = accommodation;
        this.evaluationManager = evaluationManager;

        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Κατάλυμα: " + accommodation.getName());

        JPanel generalPanel = new JPanel(new GridLayout(3,1)); //ΠΡΕΠΕΙ ΝΑ ΠΡΟΣΤΕΘΕΙ 4ο ΠΑΡΟΧΕΣ ΜΕ CHECKBOXES
        //GridLayout gridLayoutGeneral = new GridLayout(3,1);
        //setLayout(gridLayoutGeneral);

        accommodationDetails = new JPanel(new GridLayout(5,2,5,5));

        accommodationName = new JTextField(accommodation.getName());
        accommodationName.setEditable(false); // αλλάζει σε true στην υποκλάση ViewAccommodationAsProvider
        accommodationName.setFont(accommodationName.getFont().deriveFont(Font.BOLD, 14f));

        accommodationRating = new JLabel("Βαθμολογία: " + Float.toString(accommodation.getAvgRating()) + " (" + Integer.toString(accommodation.getTotalEvaluations()) + ")");

        description = new JTextField(accommodation.getDescription());
        description.setEditable(false); // αλλάζει σε true στην υποκλάση ViewAccommodationAsProvider

        stayType = new JTextField(accommodation.getStayType());
        stayType.setEditable(false); // αλλάζει σε true στην υποκλάση ViewAccommodationAsProvider

        town = new JTextField(accommodation.getLocation().getTown());
        address = new JTextField(accommodation.getLocation().getAddress());
        postCode = new JTextField(accommodation.getLocation().getPostCode());
        town.setEditable(false);
        address.setEditable(false);
        postCode.setEditable(false); // αλλάζουν σε true στην υποκλάση ViewAccommodationAsProvider


        accommodationDetails.add(accommodationName);
        accommodationDetails.add(accommodationRating);
        accommodationDetails.add(new JLabel(" Τύπος:"));
        accommodationDetails.add(stayType);
        accommodationDetails.add(new JLabel(" Πόλη:"));
        accommodationDetails.add(town);
        accommodationDetails.add(new JLabel(" Διεύθυνση:"));
        accommodationDetails.add(address);
        accommodationDetails.add(new JLabel(" T.K.:"));
        accommodationDetails.add(postCode);
        generalPanel.add(accommodationDetails);


        generalPanel.add(description);

        evaluationsOfAccommodation = evaluationManager.getAccommodationEvaluations(accommodation);
        DefaultListModel<String> helperList = new DefaultListModel<>();
        if (evaluationsOfAccommodation != null) {
                for (int i=0; i<evaluationsOfAccommodation.size(); i++) {
                    helperList.add(i, evaluationsOfAccommodation.get(i).toString());
                }
        }
        evaluationList = new JList<>(helperList);
        JScrollPane scrollPane = new JScrollPane(evaluationList);
        generalPanel.add(scrollPane);




        // ΟΛΟ αυτο θα δουλευει διαφορετικά για provider και simpleUser στον δευτερο πρεπει να εμφανίζεται η editable μορφή του evaluation άρα μπαίνει στις υποκλάσεις
        viewSelectedEvaluation = new JButton("View");
        viewSelectedEvaluation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (evaluationList.getSelectedIndex() != -1) {
                    ViewEvaluation view = new ViewEvaluation(evaluationsOfAccommodation.get(evaluationList.getSelectedIndex()));
                }
            }
        });

        add(generalPanel);
        add(viewSelectedEvaluation, BorderLayout.PAGE_END);






        setVisible(true);


    }
}

//    public static void main(String[] args) {
//        ViewAccommodation frame = new ViewAccommodation(new Accommodation("Test accomm", ));
//    }


