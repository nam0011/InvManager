package GGUI;

import com.company.IngredientDictionary;
import com.company.InventoryManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class DefaultFrame extends JFrame implements ActionListener {

        private JPanel mainPanel;
        private JTabbedPane tabbedPane;
        private InventoryManager IM;
        private ImageIcon img;
        private JButton logoutButton;

   private JTabbedPane ingredientsTab;

    /**
     *  Builds the frame adds the tabs. At the moment IngredientPanel is the only operational frame.
     */
    public DefaultFrame()
    {
        mainPanel = new JPanel(new GridBagLayout());

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

        //Builds the logout button
        {
            logoutButton = new JButton("Logout");
            logoutButton.addActionListener(this);


        }


        setResizable(false);


        buildLayout();

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

    private void buildLayout()
    {
        GridBagConstraints gc = new  GridBagConstraints();
        gc.gridx =  0;
        gc.gridy = 0;
        mainPanel.add(tabbedPane, gc);
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.SOUTHWEST;
        gc.insets = new Insets(10,0,0,0);
        mainPanel.add(logoutButton, gc);
        add(mainPanel);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() ==  logoutButton) {
            dispose();
            //TODO add a "are you sure dialog"
            new loginGUI();
        }
    }
}
