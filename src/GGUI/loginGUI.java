package GGUI;
import java.util.ArrayList;

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


    /**
     * This is the method set to be used as first point of build and user contact
     */
    public loginGUI() {

        loginPanel = new JPanel();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon("src/GGUI/Restaurant.jpg");
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        this.setIconImage(img.getImage());
        userLabel = new JLabel("User Name");
        userTextField = new SelfClearingTextField("",50);
        userTextField.setColumns(10);
        pwLabel = new JLabel("Password");
        passwordTextField = new JPasswordField("");
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


    /**
     * Method determines if user can actually log in by calling on some UX functions
     * @param e the user clicking the login button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        inPW = passwordTextField.getText();
        inUN = userTextField.getText();

        AccessControl user1 = new AccessControl();
        AccessControl user2 = new AccessControl();
        AccessControl user3 = new AccessControl();

        user1.buildUser("jonathan", "123");
        user2.buildUser("nathan", "456");
        user3.buildUser("jay", "789");

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
            else if(!inUN.equals(user1.getActUN()) || !inPW.equals(user1.getActPW())){
                invalidLabel.setText("incorrect username or password");
            }
            else if(!inUN.equals(user2.getActUN()) || !inPW.equals(user2.getActPW())){
                invalidLabel.setText("incorrect username or password");
            }
            else if(!inUN.equals(user3.getActUN()) || !inPW.equals(user3.getActPW())){
                invalidLabel.setText("incorrect username or password");
            }
        }
    }

    //end of loginGUI class
}