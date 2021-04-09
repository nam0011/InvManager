package GGUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SelfClearingNumbField extends SelfClearingTextField implements KeyListener {
    private int countPoint;

    private String last;


    public SelfClearingNumbField(String title, int width) {
        super(title, width);
        last = "";
        countPoint = 0;
        addKeyListener(this);

    }

    //TODO catch exception to not allow any string to be input here
    public double getValue() {
        double value;
        value = Double.parseDouble(getText());
        return value;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        last = getText() + c;
        try {
            Double.parseDouble(last);
        } catch (NumberFormatException numberFormatException) {

            e.consume();
            getToolkit().beep();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
