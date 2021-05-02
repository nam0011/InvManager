package GGUI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SelfClearingUsername extends SelfClearingTextField implements KeyListener {
    private abstractDialog dialog;
    public SelfClearingUsername(String title, int width, abstractDialog inDialog) {
        super(title, width);
        dialog = inDialog;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(getText().equals(defaultText)){
            setText(start);
            beenClicked = true;
            setFont(new Font("New Times Roman", Font.PLAIN, 12));
        }
        if(e.getKeyChar() == '"'){
            e.consume();
            getToolkit().beep();
        }

        dialog.filleUN(hasBeenClickedAndFilled());
    }

}
