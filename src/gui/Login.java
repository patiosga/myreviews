package gui;


import api.User;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JLabel username;
    private JLabel password;
    JButton login,signup;




    public Login() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");
        JPanel generalPanel = new JPanel(new GridLayout(1, 2));
        login = new JButton("Login");
        signup = new JButton("Sign Up");
        login.addActionListener(e);
        signup.addActionListener((ActionListener) this);
        this.setVisible(true);
    }

    login.addActionListener(new ActionListener()) {
        @Override
        public void actionPerformed(ActionEvent e) {
            new LoginPage();
            super.dispose();
        }
    }


}