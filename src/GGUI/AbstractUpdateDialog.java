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

abstract class AbstractUpdateDialog extends abstractDialog  implements ActionListener{
    public AbstractUpdateDialog(IngredientPanel panel, IngredientItem itemIn) {
        super(panel);
        item = itemIn;
        listTextFields = new ArrayList<>();


        ingredientPanel = panel;



        setLayout(new GridBagLayout());


        buildDialog();
        setSize(400, 400);
        pack();
        //setResizable(true);
        setVisible(true);

    }

    private SelfClearingTextField itemNameTF;

    public SelfClearingNumbField getAmtTF() {
        return amtTF;
    }

    private SelfClearingNumbField amtTF;




    private JTextField unitTF;
    private ArrayList<SelfClearingTextField> listTextFields;

    public JTextField getUnitTF() {
        return unitTF;
    }

    public ArrayList<SelfClearingTextField> getListTextFields() {
        return listTextFields;
    }

    public JButton getoKB() {
        return oKB;
    }

    public JButton getCancel() {
        return cancel;
    }

    public String[] getUnitArray() {
        return unitArray;
    }

    public DefaultTableModel getDTM() {
        return DTM;
    }

    public IngredientPanel getIngredientPanel() {
        return ingredientPanel;
    }

    public IngredientItem getItem() {
        return item;
    }

    private JButton oKB;
    private JButton cancel;





    private String[] unitArray = new String[4];
    private DefaultTableModel DTM;
    private IngredientPanel ingredientPanel;
    private IngredientItem item;

    //TODO JAVADOC

//TODO JAVADOC

    /**
     *
     */
    private void buildDialog() {

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4);

        itemNameTF = new SelfClearingTextField(" ", 30);
        itemNameTF.setText(item.getName());
        itemNameTF.setSize(30, 12);
        itemNameTF.setEditable(false);
        itemNameTF.setFont(new Font("New Times Roman", Font.BOLD, 12));
        amtTF = new SelfClearingNumbField("Amount", 10);



        setListTextFields();

        oKB = new JButton("Ok");
        oKB.addActionListener(this);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);


        unitTF = new JTextField(item.getMeasurementUnit());
        unitTF.setEditable(false);


//Set the grid layout
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = 2;
        add(itemNameTF, gc);


        gc.gridx = 0;
        gc.gridy = 1;
        add(amtTF, gc);


        gc.gridx = 3;
        gc.gridy = 1;
        add(unitTF, gc);

        gc.gridx = 4;
        gc.gridy = 4;
        add(oKB, gc);

        gc.gridx = 5;
        gc.gridy = 4;
        add(cancel, gc);

    }

    public SelfClearingTextField getItemNameTF() {
        return itemNameTF;
    }

    /**
     * I'm sure why I put all the textfields seems unnecessary, but too lazy to change it something else.
     */
    public void setListTextFields() {


        listTextFields.add(amtTF);



    }

    /**
     * This checks if all the text fields are nonblank.
     *
     * @return
     */
    public boolean allFilled() {
        boolean all = true;//stupid name for a variable.
        for (int i = 0; i < listTextFields.size(); i++) {
            all = (listTextFields.get(i).hasBeenClickedAndFilled());
            if (!all) {
                break;
            }
        }
        return all;
    }

    /**
     * This method resets all the blank/nonclicked-on text field to default message.
     * I might need to move this to an utility method so other frames can use this.
     */
    public void resetList() {

        for (int i = 0; i < listTextFields.size(); i++) {
            SelfClearingTextField curr = listTextFields.get(i);
            if (!(curr.hasBeenClickedAndFilled())) {

                curr.reset();
            }
        }


    }

    /**
     * Finds the insertion point.
     * //@param name - name of item for comparison to find index
     *
     * @return index at which to insert item
     */
    public int findInsertionPoint(String name) {
        DTM = ingredientPanel.getDTM();
        int index = DTM.getRowCount();
        for (int i = 0; i < DTM.getRowCount(); i++) {
            if (name.compareTo((String) DTM.getValueAt(i, 0)) == 0) {
                index = i;
                break;
            }

        }
        return index;
    }

}


