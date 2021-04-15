package GGUI;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class loginGUI extends JFrame implements ActionListener {
    private JPanel loginPanel;
    private JLabel userLabel;
    private JLabel pwLabel;//for now on pw == password.
    private SelfClearingTextField userTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton;
    private JLabel invalidLabel;
    private String inPW, inUN;
    private AccessControl ac = new AccessControl();

    public loginGUI() {

        loginPanel = new JPanel();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon("src/GGUI/Restaurant.jpg");
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        this.setIconImage(img.getImage());
        userLabel = new JLabel("User Name");
        userTextField = new SelfClearingTextField("jlew92",50);
        userTextField.setColumns(10);
        pwLabel = new JLabel("Password");
        passwordTextField = new JPasswordField("password123");
        invalidLabel = new JLabel("");
        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        gc.insets = new Insets(10,2,10,2);

        gc.gridy = 0;
        gc.gridx = 0;
        loginPanel.add(userLabel,gc);
        gc.gridy = 0;
        gc.gridx = 1;
        loginPanel.add(userTextField,gc);
        gc.gridy = 1;
        gc.gridx = 0;

        loginPanel.add(pwLabel,gc);
        gc.gridy = 1;
        gc.gridx = 1;
        passwordTextField.setColumns(10);
        loginPanel.add(passwordTextField,gc);

        gc.gridy = 2;
        gc.gridx = 0;
        loginPanel.add(invalidLabel,gc);

        gc.gridy = 2;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.SOUTHWEST;
        gc.insets = new Insets(40,200,0,2);
        loginPanel.add(loginButton,gc);

        setTitle("Please Login Here !");
        setSize(600, 350);

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
            try {
                new DefaultFrame();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
        else{
            //error message
            if(inUN.equals("") || inPW.equals("")) {
                invalidLabel.setText("both fields must be filled");
            }
            else if(!inUN.equals(ac.getActUN())) {
                invalidLabel.setText("incorrect username");
            }
            else if (!inPW.equals(ac.getActPW())){
                invalidLabel.setText("incorrect password");
            }

        }
    }
}