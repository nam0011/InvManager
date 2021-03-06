package GGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SelfClearingNumbField extends SelfClearingTextField implements KeyListener {
    private int countPoint;

    @Override
    public void setTransferHandler(TransferHandler newHandler) {
        super.setTransferHandler(null);
    }

    protected String last;
    protected String defaultText;


    public SelfClearingNumbField(String title, int width) {
        super(title, width);
        defaultText = title;
        last = start;

        addKeyListener(this);

    }

    public boolean hasBeenClickedAndFilled() {
        boolean filled = true;
        String x = getText();
        if(x.equals(start)){
            filled = false;
        }
        return (beenClicked && filled);
    }

    //TODO catch exception to not allow any string to be input here
    public double getValue() {
        double value;
        value = Double.parseDouble(getText());
        return value;
    }


    @Override
    public void keyTyped(KeyEvent e) {

        if(getText().equals(defaultText)){
            setText(start);

            setFont(new Font("New Times Roman", Font.PLAIN, 12));
        }
        char c = e.getKeyChar();
        last = getText() + c;
        if(last.charAt(0) == '.'){
            last = "0" + last;
        }
        try {
            Double.parseDouble(last);
        } catch (NumberFormatException numberFormatException) {

            e.consume();
            getToolkit().beep();
        }
        //For whatever reason, the parseDouble is allowing d and f to parse in certain locations of the text
        if(c == 'd' || c == 'f'){

            e.consume();
            getToolkit().beep();

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == InputEvent.CTRL_DOWN_MASK){

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
