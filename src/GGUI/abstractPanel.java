package GGUI;

import javax.swing.*;

abstract class abstractPanel extends JPanel {

    private DefaultFrame frame;
    abstractPanel(DefaultFrame inFrame){
        frame = inFrame;

    }
    /**
     * Turns on or off the main frame so the user cannot do more than one thing
     * @param enable
     */
    public void setDefaultFrameEnable(boolean enable)
    {
        frame.setEnabled(enable);
    }



}
