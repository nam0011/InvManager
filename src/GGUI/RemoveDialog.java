package GGUI;

import com.company.IngredientDictionary;
import com.company.IngredientItem;
import com.company.InventoryManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RemoveDialog extends abstractDialog implements ActionListener {
    private IngredientPanel ingredientPanel;
    private ArrayList<String> removeArray;
    private JList removeList;
    private JLabel removeMsg;
    private JButton okB;
    private JButton cancelB;
    private DefaultListModel removeModel;
    private JScrollPane listPane;
    private JPanel mainPanel;
    private DefaultTableModel DTM;
    private InventoryManager IM = InventoryManager.getInventoryManager();

    /**
     * Builds the remove dialog window
     * @param panel the panel outline we want
     * @param array the array of selected items
     */
    public RemoveDialog(IngredientPanel panel, ArrayList<String> array)
    {
        super(panel);
        ingredientPanel = panel;
        ingredientPanel.setDefaultFrameEnable(false);
        this.isAlwaysOnTop();

        DTM = ingredientPanel.getDTM();
        removeArray = array;
        setSize(300, 300);

        setTitle("Remove Item(s)");
        removeMsg = new JLabel("Are you sure you want to remove the following item(s)");

        buildList();
        buildScrollPane();
        arrangeMainPanel();

        setSize(350, 300);
        this.setVisible(true);


    }

    private void arrangeMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        okB = new JButton("OK");
        okB.addActionListener(this);

        cancelB = new JButton("Cancel");
        cancelB.addActionListener(this);

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(0,5,15,2);

        gc.gridy = 0;
        gc.gridx = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridwidth = 6;
        gc.gridheight = 1;
        mainPanel.add(removeMsg, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.ipady = 75;
        gc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(listPane, gc);



        gc.ipady = 1; //reset to default.
        gc.fill = 1;
        gc.gridx = 4;
        gc.gridy = 2;
        gc.gridheight = 1;
        gc.anchor = GridBagConstraints.SOUTHEAST;
        gc.insets = new Insets(40,200,0,2);
        gc.fill = 0;
        gc.gridwidth = 1;

        mainPanel.add(okB, gc);

        gc.insets = new Insets(20,5,0,2);
        gc.gridx = 5;
        gc.gridy = 2;
        mainPanel.add(cancelB, gc);
        pack();
        add(mainPanel);
    }

    private void buildScrollPane() {

        listPane = new JScrollPane(removeList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listPane.setPreferredSize(new Dimension(125, 40));
        listPane.setSize(300, 175);
        listPane.setVisible(true);

        add(listPane);

    }

    public void matching(boolean x){
        return;
    }

    @Override
    public void filleUN(boolean x) {

    }

    /**
     * Building of the list of items that are selected to be removed
     */
    public void buildList()
    {
        removeModel = new DefaultListModel();
        int n = removeArray.size();

        for(int i = 0; i < n; i++)
        {
            removeModel.addElement(removeArray.get(i));
        }
        removeList = new JList(removeModel);
        removeList.setLayoutOrientation(JList.VERTICAL);


    }

    private int findIndexDTM(String name) {
        int index = DTM.getRowCount();
        for (int i = 0; i < DTM.getRowCount(); i++) {
            if (name.compareTo((String) DTM.getValueAt(i, 0)) == 0) {
                index = i;
                break;
            }

        }
        return index;
    }

    /**
     * checking for specific user button action calls UX methods based on user decision
     * @param e user pressing a choice of button
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == okB){
            int ii;

            IngredientItem temp;
            for(int i = 0; i < removeArray.size(); i++){
                ii = findIndexDTM(removeArray.get(i));
                DTM.removeRow(ii);
                temp = IM.getIngredientItem(removeArray.get(i));
                IM.removeIngredientFromList(temp);

            }

            ingredientPanel.setDefaultFrameEnable(true);
            dispose();
        }
        else if (e.getSource() ==cancelB){

            ingredientPanel.setDefaultFrameEnable(true);
            dispose();
        }

    }
}
