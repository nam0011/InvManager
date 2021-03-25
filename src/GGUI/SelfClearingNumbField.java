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

    public double getValue() {
        double value;
        value = Double.parseDouble(getText());
        return value;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        String selected = getSelectedText();
        if (!((c >= 48) && (c <= 57) || c == KeyEvent.VK_ENTER || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || c == '.')) {
            getToolkit().beep();
            e.consume();
        }
        if ((c == '.' && last.contains("."))) {
            getToolkit().beep();
            e.consume();
        }
        if (selected != null) {
            if (selected.contains(".")) {
                last = "";
            }
        } else {
            last = getText() + c;
        }
        int mod = e.getModifiersEx();
        if (c == '\u0016' && mod == 128) {
            try {
                Double.parseDouble(getText());
            } catch (NumberFormatException numberFormatException) {
                this.removeAll();
            }

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
