package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import api.*;

public class SignUpPage extends JFrame{

    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTextField userNameText;
    private JPasswordField passwordText;
    private JRadioButton radioButtonProvider;
    private JRadioButton radioButtonSimpleUser;


    public SignUpPage(ManageUsers usersManager, ManageEvaluations evaluationsManager, ManageAccommodations accommodationsManager){

        setSize(700,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sign up");
        JPanel generalPanel = new JPanel(new GridLayout(5,2));

        JLabel firstNameLabel = new JLabel("Όνομα:");
        generalPanel.add(firstNameLabel);
        firstNameText = new JTextField();
        generalPanel.add(firstNameText);

        JLabel lastNameLabel = new JLabel("Επίθετο:");
        generalPanel.add(lastNameLabel);
        lastNameText = new JTextField();
        generalPanel.add(lastNameText);

        JLabel userNameLabel = new JLabel("Όνομα χρήστη:");
        generalPanel.add(userNameLabel);
        userNameText = new JTextField();
        generalPanel.add(userNameText);


        JLabel passwordLabel = new JLabel("Κωδικός:");
        generalPanel.add(passwordLabel);
        passwordText = new JPasswordField();
        generalPanel.add(passwordText);



        radioButtonSimpleUser = new JRadioButton("Απλός Χρήστης");
        radioButtonProvider = new JRadioButton("Πάροχος");
        ButtonGroup group = new ButtonGroup();
        group.add(radioButtonSimpleUser);
        group.add(radioButtonProvider);
        generalPanel.add(radioButtonSimpleUser);
        generalPanel.add(radioButtonProvider);


        generalPanel.add(radioButtonSimpleUser);
        generalPanel.add(radioButtonProvider);



        JButton signUpButton = new JButton("Εγγραφή");
        generalPanel.add(signUpButton);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String checkMessage = usersManager.checkSignUpInaccuracies(firstNameText.getText(), lastNameText.getText(), userNameText.getText(), String.copyValueOf(passwordText.getPassword()));
                User user;
                if (checkMessage == null) {
                    if (radioButtonSimpleUser.isSelected()) {
                        user = usersManager.createSimpleUser(firstNameText.getText(), lastNameText.getText(), userNameText.getText(), new String(passwordText.getPassword()));
                        new dashboardSimpleUser((SimpleUser) user, evaluationsManager, usersManager, accommodationsManager);
                        dispose();
                    }
                    else if (radioButtonProvider.isSelected()) {
                        user = usersManager.createProvider(firstNameText.getText(), lastNameText.getText(), userNameText.getText(), new String(passwordText.getPassword()));
                        new dashboardProvider((Provider) user, accommodationsManager, evaluationsManager, usersManager);
                        dispose();
                    }
                    else
                        JOptionPane.showMessageDialog(getParent(), "Επιλέξτε το είδος χρήστη που θέλετε να δημιουργήσετε");

                }
                else {
                    JOptionPane.showMessageDialog(getParent(), checkMessage);
                }
            }
        });

        add(generalPanel);
        add(signUpButton, BorderLayout.PAGE_END);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                accommodationsManager.saveToOutputFiles();
                evaluationsManager.saveToOutputFiles();
                usersManager.saveToOutputFiles();
                System.exit(0);
            }
        });


        setVisible(true);
    }
}