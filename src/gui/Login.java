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



    public Login(User user) {
        setSize(300, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");
        JPanel generalPanel = new JPanel(new GridLayout(1, 2));
        this.add(new JButton("Login"));
        this.add(new JButton("Sign up"));
        this.setVisible(true);



    }
}