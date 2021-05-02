package GGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;

public class SelfClearingPasswordTextfield extends JPasswordField implements MouseListener, KeyListener {
    protected boolean beenClicked;
    protected String start;
    protected String defaultText;

    public SelfClearingPasswordTextfield(String title, int size, JFrame frame){

       unmask();
       defaultText = title;
       setText(defaultText);
       this.setColumns(size);
       addMouseListener(this);
       addKeyListener(this);

    }

    public SelfClearingPasswordTextfield(String password, int size, AddUserDialog addUserDialog, Object o) {
    }

    public int getHashPasscode(){

    return this.getText().hashCode();
}

public void unmask(){
    this.setEchoChar('\u0000');
    this.setFont(new Font("New Times Roman" , Font.ITALIC, 12));
    beenClicked = false;



}
public void mask(){
    setEchoChar('•');
    beenClicked = true;
}


    @Override
    public void keyTyped(KeyEvent e) {
        if(!beenClicked) {
            setText("");
            mask();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!beenClicked) {
            setText("");
            mask();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}