package GGUI;

import com.company.IngredientDictionary;
import com.company.InventoryManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class DefaultFrame extends JFrame {

        private JPanel mainPanel;
        private JTabbedPane tabbedPane;
        private InventoryManager IM;
        private ImageIcon img;

   private JTabbedPane ingredientsTab;

    /**
     *  Builds the frame adds the tabs. At the moment IngredientPanel is the only operational frame.
     */
    public DefaultFrame()
    {
        mainPanel = new JPanel();

        mainPanel.setSize(500, 600);
        setTitle("Inventory Manager");
        img = new ImageIcon("src/GGUI/Restaurant.jpg");

        this.setIconImage(img.getImage());
        tabbedPane = new JTabbedPane();
        tabbedPane.setSize(500,600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        IngredientPanel ingredientPanel = new IngredientPanel(this);
        ingredientPanel.setSize(500, 600);
        tabbedPane.add("Ingredients", ingredientPanel);

        tabbedPane.add("Reports", new ReportsPanel(this));
        JLabel logout =  new JLabel("LOGOUT");



        setResizable(false);
        mainPanel.add(tabbedPane);
        add(mainPanel);

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
