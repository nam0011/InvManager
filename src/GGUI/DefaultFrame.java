package GGUI;

import com.company.IngredientDictionary;
import com.company.InventoryManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class DefaultFrame extends JFrame {
        private JPanel recepPanel;
        private JPanel formPanel;
        private JTabbedPane tabbedPane;
        private InventoryManager IM;

   private JTabbedPane ingredientsTab;

    /**
     *  Builds the frame adds the tabs. At the moment IngredientPanel is the only operational frame.
     */
    public DefaultFrame()
    {
        tabbedPane = new JTabbedPane();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        tabbedPane.add("Ingredients", new IngredientPanel(this));

        tabbedPane.add("Reports", new ReportsPanel(this));


        setResizable(false);
        add(tabbedPane);
        IM = InventoryManager.getInventoryManager();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // call terminate
                try {
                    IM.UpdateJSONFile();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                dispose();
                System.exit(0);
            }
        });

        setSize(500, 600);
        setVisible(true);
    }



}
