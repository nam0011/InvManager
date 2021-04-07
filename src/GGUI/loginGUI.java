package GGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginGUI extends JFrame implements ActionListener {
    private JPanel loginPanel;
    private JLabel userLabel;
    private JLabel pwLabel;//for now on pw == password.
    private JTextField userTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton;
    private JLabel invalidLabel;
    private String inPW, inUN;
    private AccessControl ac = new AccessControl();

    public loginGUI() {
        loginPanel = new JPanel(new GridLayout(3, 1));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        userLabel = new JLabel("User Name");
        userTextField = new JTextField("jlew92");
        pwLabel = new JLabel("Password");
        passwordTextField = new JPasswordField("password123");
        invalidLabel = new JLabel("");
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        add(loginPanel, BorderLayout.CENTER);
        loginPanel.add(userLabel);
        loginPanel.add(userTextField);
        loginPanel.add(pwLabel);
        loginPanel.add(passwordTextField);
        loginPanel.add(invalidLabel);
        loginPanel.add(loginButton);
        setTitle("Please Login Here !");
        setSize(450, 350);
        add(loginPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inPW = passwordTextField.getText();
        inUN = userTextField.getText();
        if (ac.giveAccess(inUN, inPW)) {

            loginButton.removeActionListener(this);

            dispose();
            new DefaultFrame();

        }
        else{
            //error message

            invalidLabel.setText("incorrect password");
        }
    }
}