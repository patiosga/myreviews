package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import api.ManageUsers;

public class SignUpPage extends JFrame implements ActionListener {

    private ManageUsers usersManage;

    private JRadioButton provider,simpleUser;



    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public SignUpPage(ManageUsers usersManage){
        JPanel panel = new JPanel();
        setSize(700,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sign up");
        this.add(panel);

        panel.setLayout(null);

        JLabel firstNameLabel = new JLabel();
        firstNameLabel.setBounds(10,20,80,25);
        panel.add(firstNameLabel);

        JTextField firstNameText = new JTextField();
        firstNameText.setBounds(100, 20, 165, 25 );
        panel.add(firstNameText);



        JLabel lastNameLabel = new JLabel();
        lastNameLabel.setBounds(10,50,80, 25);
        panel.add(lastNameLabel);

        JTextField lastNameText = new JTextField();
        lastNameText.setBounds(100, 50, 165, 25);
        panel.add(lastNameText);



        JLabel userNameLabel = new JLabel();
        userNameLabel.setBounds(10,80,80, 25);
        panel.add(userNameLabel);

        JTextField userNameText = new JTextField();
        userNameText.setBounds(100, 80, 165, 25);
        panel.add(userNameText);


        JLabel passwordLabel = new JLabel();
        passwordLabel.setBounds(10,110,80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField( );
        passwordText.setBounds(100,110,165, 25);
        panel.add(passwordText);



        JRadioButton radioButtonSimpleUser = new JRadioButton("Simple User");
        JRadioButton radioButtonProvider = new JRadioButton("Provider");

        ButtonGroup group = new ButtonGroup();
        group.add(radioButtonSimpleUser);
        group.add(radioButtonProvider);
        //radioButtonSimpleUser.setBounds();
        //radioButtonProvider.setBounds();
        panel.add(radioButtonSimpleUser);
        panel.add(radioButtonProvider);


        this.add(radioButtonSimpleUser);
        this.add(radioButtonProvider);
        this.pack();
        this.setVisible(true);






        JLabel typeLabel = new JLabel();




    }
}
