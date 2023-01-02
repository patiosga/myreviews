package gui;


import api.ManageAccommodations;
import api.ManageEvaluations;
import api.ManageUsers;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

public class StartingPage extends JFrame {

    public StartingPage(ManageUsers usersManager, ManageEvaluations evaluationsManager, ManageAccommodations accommodationsManager) {
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Επιλέξτε τρόπο σύνδεσης");

        JPanel generalPanel = new JPanel(new GridLayout(2, 1));

        JButton login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage(usersManager, evaluationsManager, accommodationsManager);
                dispose();

            }
        });

        JButton signup = new JButton("Sign Up");
        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUpPage(usersManager, evaluationsManager, accommodationsManager);
                dispose();

            }
        });

        generalPanel.add(login);
        generalPanel.add(signup);
        add(generalPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                accommodationsManager.saveToOutputFiles();
                evaluationsManager.saveToOutputFiles();
                usersManager.saveToOutputFiles();
                System.exit(0);
            }
        });


        this.setVisible(true);
    }
}