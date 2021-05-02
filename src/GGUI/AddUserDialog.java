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

public class AddUserDialog extends abstractDialog implements ActionListener{

    private SelfClearingUsername userNameTF;
    private JLabel userNameLbl, pwLbl, confirmLbl,userPrivLbl;
    private SelfClearingPasswordTextfield passwordTF;
    private SelfClearingPasswordTextfield confirmTF;
    private JComboBox userTypeCB;
    private JButton confirmB;
    private JButton cancelB;
    private JLabel good;
    private boolean matchPW;
    private boolean fillUN;
    private AccessControl AC;

    public AddUserDialog(abstractPanel panel) throws IOException, ParseException {
        super(panel);
        AC = AccessControl.getAccessInstance();
        setTitle("Add a user");
        setLayout(new GridBagLayout());
        setSize(new Dimension(550, 550));
        intializeObjects();
        buildLayout();
    }



    private void intializeObjects(){
        userNameLbl = new JLabel("userName");
        userNameTF = new SelfClearingUsername("UserName", 60, this);


        String strAryType [] = {"admin", "manager", "user"};
        userTypeCB = new JComboBox(strAryType);
        userTypeCB.addActionListener(this);


        pwLbl = new JLabel("password");
        passwordTF = new SelfClearingPasswordTextfield("Password", 30, this,null);

        confirmLbl = new JLabel("confirm password");
        confirmTF = new SelfClearingPasswordTextfield("Confirm Password", 30, this, passwordTF);

        ImageIcon img = new ImageIcon("src/GGUI/Restaurant.jpg");

        setResizable(false);



        confirmB = new JButton("Confirm");
        confirmB.addActionListener(this);
        confirmB.setEnabled(true);
        cancelB = new JButton("Cancel");
        cancelB.addActionListener(this);




    }
    private void buildLayout(){
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 0, 10, 5);
        gc.gridx = 0;
        gc.gridy = 0;

        add(userNameLbl, gc);
        gc.gridx = 1;
        userNameTF.setColumns(10);
        add(userNameTF, gc);

        gc.gridy = 0;
        gc.gridx = 2;
        add(userTypeCB, gc);

        gc.gridy = 1;
        gc.gridx = 0;
        add(pwLbl, gc);
        gc.gridx = 1;

        passwordTF.setColumns(10);
        add(passwordTF, gc);

        gc.gridy = 2;
        gc.gridx = 0;
        add(confirmLbl, gc);
        gc.gridx = 1;
        confirmTF.setColumns(10);
        add(confirmTF, gc);
        gc.insets = new Insets(10, 0, 10, 0);



        gc.anchor = GridBagConstraints.SOUTHWEST;
        gc.gridy = 3;
        gc.gridx = 2;


        add(confirmB, gc);
        gc.gridx = 3;
       add(cancelB, gc);
       pack();





    }
    public void matching(boolean match){

        matchPW = match;
        confirmB.setEnabled(true);
    }

    @Override
    public void filleUN(boolean x) {
        fillUN = x;
        confirmB.setEnabled(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
         Object t = e.getSource();
        if(t == confirmB){
            String userNameIN = userNameTF.getText();
            int hashPWIN = passwordTF.getHashPasscode();

            if(!AC.checkUNexist(userNameIN)){
                Account account = new Account(userNameIN, hashPWIN, (String) userTypeCB.getSelectedItem());
                try {
                    AC.addAccount(account);
                    close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
            else{
                JOptionPane.showMessageDialog(this, "The username already exists", "Username already exists", JOptionPane.ERROR_MESSAGE);
            }

        }
        else if(t == cancelB){
            this.close();
        }

    }

}
