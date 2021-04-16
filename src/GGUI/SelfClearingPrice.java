package GGUI;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class SelfClearingPrice extends SelfClearingNumbField {
    public SelfClearingPrice(String title, int width) {
        super(title, width);
        start ="$";
        last= "$";

    }
    public double getValue() {
        double value;
        String input = getText();
        input = input.substring(1);
        value = Double.parseDouble(input);
        return value;
    }


    public void keyTyped(KeyEvent e) {

        if(getText().equals(defaultText)){
            setText(start);
            setCaretPosition(1);
            setFont(new Font("New Times Roman", Font.PLAIN, 12));
        }


        char c = e.getKeyChar();
        int pos = this.getCaretPosition();
        if(pos == 0)
        {
            e.consume();
            getToolkit().beep();
            last = last.trim();
            this.setText(last);
            return;
        }else if((this.getSelectedText() != null && this.getSelectedText().contains("$"))){
            last = last.stripTrailing();
            this.setText(last);
            getToolkit().beep();
            return;

        }


            last = getText().substring(0, pos) + c + getText().substring(pos);

            if(last.charAt(1) == '.'){

                last = last.substring(0, 1) +"0"+ last.substring(1);
            }



            try {
                Double.parseDouble(last.substring(1));
            } catch (NumberFormatException numberFormatException) {

                e.consume();
                getToolkit().beep();
            }
            //For whatever reason, the parseDouble is allowing d and f to parse in certain locations of the text
            if (c == 'd' || c == 'f') {

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
