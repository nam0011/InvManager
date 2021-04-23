package GGUI;

import java.awt.*;
import java.awt.event.KeyEvent;

public class SelfClearingPasswordTextfield extends SelfClearingTextField {
    private String inputPW;
    private  String maskPW;
    private loginGUI gui;

    public SelfClearingPasswordTextfield(String title, int width , loginGUI inGui) {
        super(title, width);
        gui = inGui;
        inputPW = "";
        maskPW = "";

    }
    public int getHashPasscode(){

        return inputPW.hashCode();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER){
            gui.login();
        }
        if(e.isActionKey()){}

        if (getText().equals(defaultText)) {
            setText(start);
            beenClicked = true;
            setFont(new Font("New Times Roman", Font.PLAIN, 12));
        }
        int pos = this.getCaretPosition();
        char c = e.getKeyChar();



    if(c != KeyEvent.VK_BACK_SPACE) {
      inputPW = inputPW.substring(0, pos) + c + inputPW.substring(pos);
        maskPW = maskPW + '*';

    }else {
        inputPW = inputPW.substring(0,pos);
        maskPW = maskPW.substring(0, inputPW.length());
    }

       inputPW = inputPW.trim();
       e.consume();


       setText(maskPW);

    }
}