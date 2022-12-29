package gui;


import api.ManageUsers;
import api.User;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JButton login,signup;
    private ManageUsers usersManager;




    public Login() {
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Authentication");
        JPanel generalPanel = new JPanel(new GridLayout(2, 1));
        login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage(ManageUsers usersManager);
                Login.super.dispose();

            }
        });
        signup = new JButton("Sign Up");


        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new SignUpPage;
                Login.super.dispose();

            }
        });
        this.setVisible(true);
    }




}