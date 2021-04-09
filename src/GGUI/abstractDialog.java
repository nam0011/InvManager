package GGUI;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

abstract class abstractDialog extends JDialog {
    private IngredientPanel ingredientPanel;
    abstractDialog(IngredientPanel panel){
        super();
        setResizable(false);
        ingredientPanel = panel;
        ingredientPanel.setDefaultFrameEnable(false);
        this.isAlwaysOnTop();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                ingredientPanel.setDefaultFrameEnable(true);
                dispose();

            }
        });
    }

}
