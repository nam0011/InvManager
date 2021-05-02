package GGUI;
import com.company.AccessControl;
import com.company.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.text.ParseException;

public class loginGUI extends JFrame implements ActionListener {
    private JPanel loginPanel;
    private JLabel userLabel;
    private JLabel pwLabel;//for now on pw == password.
    private SelfClearingTextField userTextField;
    private SelfClearingPasswordTextfield passwordTextField;
    private JButton loginButton;
    private String inUN;
    private int inPW;
    private AccessControl ac = AccessControl.getAccessInstance();


    /**
     * This is the method set to be used as first point of build and user contact
     */
    public loginGUI() throws IOException, ParseException {

        loginPanel = new JPanel();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon("src/GGUI/Restaurant.jpg");
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        this.setIconImage(img.getImage());
        userLabel = new JLabel("User Name");
        userTextField = new SelfClearingTextField("Username",50);
        userTextField.setColumns(10);
        pwLabel = new JLabel("Password");
        passwordTextField = new SelfClearingPasswordTextfield("password", 10, this);

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
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.SOUTHWEST;
        gc.insets = new Insets(40,200,0,2);
        loginPanel.add(loginButton,gc);

        setTitle("Please Login Here !");
        setSize(600, 350);

        add(loginPanel);
        setVisible(true);
        pack();
    }
    public void login(){
        inPW = passwordTextField.getHashPasscode();
        inUN = userTextField.getText();


        Account account = ac.giveAccess(inUN, inPW);
        if (account !=null) {

            //loginButton.removeActionListener(this);


            try {
                new DefaultFrame(account);

            } catch (IOException | ParseException ioException) {
                ioException.printStackTrace();
            }
            dispose();

        }
        else{
            //error message
            JOptionPane.showMessageDialog(this, "Incorrect UserName/Password", "Error", 0);

        }

    }

    /**
     * Method determines if user can actually log in by calling on some UX functions
     * @param e the user clicking the login button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        login();


    }



    //end of loginGUI class
}