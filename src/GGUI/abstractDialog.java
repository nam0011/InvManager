package GGUI;

import com.company.InventoryManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

abstract class abstractDialog extends JDialog{
    protected InventoryManager IM = InventoryManager.getInventoryManager();
    private abstractPanel Panel;

    abstractDialog(abstractPanel panel){
        super();
        setSize(300, 300);
        setVisible(true);
        Panel = panel;
        Panel.setDefaultFrameEnable(false);
        this.isAlwaysOnTop();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        ImageIcon img = (ImageIcon) Panel.getIcon();
        this.setIconImage(img.getImage());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Panel.setDefaultFrameEnable(true);
                dispose();



            }
        });

    }



    public void close()
    {
        Panel.setDefaultFrameEnable(true);
        dispose();
    }

    public abstract void matching(boolean b);
    public abstract void filleUN(boolean x);
}
