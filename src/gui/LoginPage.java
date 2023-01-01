package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import api.*;


public class LoginPage extends JFrame {

    public LoginPage(ManageUsers usersManager, ManageEvaluations evaluationsManager, ManageAccommodations accommodationsManager){
        setSize(350,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");

        JPanel generalPanel = new JPanel(new GridLayout(2,2));

        JLabel userLabel = new JLabel("Username");
        generalPanel.add(userLabel);
        JTextField userText = new JTextField(20);
        generalPanel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        generalPanel.add(passwordLabel);
        JPasswordField passwordText = new JPasswordField();
        generalPanel.add(passwordText);

        JButton button = new JButton("Login");
        add(button, BorderLayout.PAGE_END);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user;
                if ((user = usersManager.findUserWithUsername(userText.getText())) == null) {
                    JOptionPane.showMessageDialog(getParent(), "Δεν βρέθηκε χρήστης με αυτό το όνομα");
                }
                else if (!usersManager.authentication(userText.getText(), new String(passwordText.getPassword()))) {
                    JOptionPane.showMessageDialog(getParent(), "Λανθασμένος κωδικός πρόσβασης");

                }
                else {
                    if (usersManager.isSimpleUser(user))
                        new dashboardSimpleUser((SimpleUser) user, evaluationsManager, usersManager, accommodationsManager);
                    else
                        new dashboardProvider((Provider) user, accommodationsManager, evaluationsManager, usersManager);
                    dispose();
                }
            }
        });
        add(generalPanel);
        setVisible(true);
    }

}
