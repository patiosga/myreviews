package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import api.ManageUsers;





public class LoginPage extends JFrame implements ActionListener {

    private ManageUsers usersManager;

    private JButton userName,password;

    @Override
    public void actionPerformed(ActionEvent e) {
    }


    public LoginPage(ManageUsers usersManager){
        JPanel panel = new JPanel();
        setSize(350,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");
        this.add(panel);

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);
        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25 );
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);
        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        JButton button = new JButton("Login");
        button.setBounds(10,80,80,25);
        panel.add(button);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (usersManager.findUserWithUsername(userText.getText()) == null)
                        JOptionPane.showMessageDialog(getParent(), "Δεν υπάρχει αυτος ο χρήστης");
                    if(usersManager.authentication(userText.getText(),passwordText.getTE) || passwordText.getPassword())
                        JOptionPane.showMessageDialog(getParent(),"Κάποιο απο τα στοιχεία είναι λάθος");
                } finally {

                }
            }
        });






        this.setVisible(true);

    }

}
