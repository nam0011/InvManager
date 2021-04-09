package GGUI;

import javax.swing.*;
import java.awt.event.ActionListener;

abstract class abstractPanel extends JPanel implements ActionListener {

    private DefaultFrame frame;
    abstractPanel(DefaultFrame inFrame){
        frame = inFrame;

    }
    /**
     * Turns on or off the main frame so the user cannot do more than one thing
     * @param enable - true enables the mainframe and false disables it.
     */
    public void setDefaultFrameEnable(boolean enable)
    {
        frame.setEnabled(enable);
    }



}
