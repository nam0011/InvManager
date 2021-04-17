package GGUI;

import com.company.IngredientDictionary;
import com.company.InventoryManager;
import GGUI.loginGUI;

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
        private loginGUI login;
        private ImageIcon img;
        private JButton logoutButton;

   private JTabbedPane ingredientsTab;

    /**
     *  Builds the frame adds the tabs. At the moment IngredientPanel is the only operational frame.
     */
    public DefaultFrame() throws IOException {

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(0,50,100));


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
        IM.UpdateBackups();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // call terminate
                if (IM.anyChanges()) {
                    Object[] options = {"Yes", "No", "Cancel"};
                    int n = JOptionPane.showOptionDialog(getItself(), "Do you want to save before closing?", "WARNING", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

                    if (n == 0) {
                        try {
                            IM.UpdateJSONFile();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        System.exit(1);
                    }
                    else if(n==1){System.exit(1);}

                }
                else{

                    System.exit(1);
                }

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
    public DefaultFrame getItself(){

        return this;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() ==  logoutButton) {

            Object[] options = {"Yes", "No"};
            int n = JOptionPane.showOptionDialog(this,
                    "Are you sure you want to log out? Your current progress will be saved",
                    "Confirm", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if(n == 0) {
                dispose();
                new loginGUI();
            }
        }

    }
}
