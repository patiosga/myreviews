package gui;


import api.User;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

protected User user;
protected JTextField firstName;
protected JTextField lastName;
protected JLabel username;
protected JLabel password;
protected JRadioButton type;


public Login(User user){
    setSize(300,300);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Login");
    JPanel generalPanel = new JPanel(new GridLayout(2,1));

    JButton login = new JButton("Log in");
    JButton signup = new JButton("Sign up");



    }