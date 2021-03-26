package GGUI;

import com.company.IngredientDictionary;
import com.company.IngredientItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UpdateDialog extends JDialog implements ActionListener {
    private SelfClearingTextField itemNameTF;
    private SelfClearingNumbField amtPurchaseTF;
    private SelfClearingNumbField amtUsedTF;
    private SelfClearingNumbField priceTF;
    private JComboBox unitDropDownBox;
    private ArrayList<SelfClearingTextField> listTextFields;
    private JButton oKB;
    private JButton cancel;
    private IngredientDictionary ID;
    private String getUnit;
    private String[] unitArray = new String[4];
    private DefaultTableModel DTM;
    private IngredientPanel indgredentPanel;

    public UpdateDialog(IngredientPanel panel) {
        //This freezes the parent panel.

        setTitle("Add Item");
        indgredentPanel = panel;

        ID = IngredientDictionary.getIngredientDictionary();
        setLayout(new GridBagLayout());
        JDialog j = new JDialog();

        buildDialog();
        setSize(300, 300);
        pack();
        //setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    }

    private void buildDialog() {

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 4, 4, 4);

        itemNameTF = new SelfClearingTextField("Item Name", 30);
        itemNameTF.setSize(30, 12);
        itemNameTF.setFont(new Font("New Times Roman", Font.ITALIC, 12));
        amtPurchaseTF = new SelfClearingNumbField("Amount Purchased", 10);
        amtUsedTF = new SelfClearingNumbField("Amount Used", 10);
        priceTF = new SelfClearingNumbField("price", 30);

        setListTextFields();

        oKB = new JButton("Ok");
        oKB.addActionListener(this);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);

        unitArray = new String[]{"lb", "oz", "L", "mL"};
        getUnit = unitArray[0];
        unitDropDownBox = new JComboBox(unitArray);
        unitDropDownBox.addActionListener(this);


//Set the grid layout
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = 2;
        add(itemNameTF, gc);


        gc.gridx = 0;
        gc.gridy = 1;
        add(amtPurchaseTF, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        add(amtUsedTF, gc);

        gc.gridx = 3;
        gc.gridy = 1;
        add(unitDropDownBox, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        add(priceTF, gc);

        gc.gridx = 4;
        gc.gridy = 4;
        add(oKB, gc);

        gc.gridx = 5;
        gc.gridy = 4;
        add(cancel,gc);

    }

    /**
     * I'm sure why I put all the textfields seems unnecessary, but too lazy to change it something else.
     */
    private void setListTextFields() {
        listTextFields = new ArrayList<>();
        listTextFields.add(itemNameTF);
        listTextFields.add(amtPurchaseTF);
        listTextFields.add(priceTF);
        listTextFields.add(amtUsedTF);

    }

    /**
     * This checks if all the text fields are nonblank.
     *
     * @return
     */
    private boolean allFilled() {
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
    private void resetList() {

        for (int i = 0; i < listTextFields.size(); i++) {
            SelfClearingTextField curr = listTextFields.get(i);
            if (!(curr.hasBeenClickedAndFilled())) {

                curr.reset();
            }
        }


    }

    /**
     * Finds the insertion point.
     *
     * //@param name
     * @return
     */
//TODO rewrite to overwrite item instead of insert
    private int findInsertionPoint(String name) {
        DTM = indgredentPanel.getDTM();
        int index = DTM.getRowCount();
        for (int i = 0; i < DTM.getRowCount(); i++) {
            if (name.compareTo((String) DTM.getValueAt(i, 0)) < 0) {
                index = i;
                break;
            }

        }
        return index;
    }



    @Override
    public void actionPerformed (ActionEvent e){
        if (e.getSource() == oKB) {

            Object[] options = {"Yes", "no"};
            String itemStr = itemNameTF.getText();

            if (allFilled()) {
                itemStr = itemStr.toUpperCase();
                double amtPurchasedValue = amtPurchaseTF.getValue();
                double amtUsedValue = amtUsedTF.getValue();
                double priceValue = priceTF.getValue();
                if (ID.ingredientCheck(itemStr)) {//too many checking ArrayList very inefficient! <=========================Replace me==================

                    int n = JOptionPane.showOptionDialog(this,
                            "Are you sure you want to update " + (amtPurchasedValue - amtUsedValue) + " " + getUnit + " of " + itemStr + " for $" + priceValue + "?",
                            "Confirm", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == 0) {

                        //*****************************************************************************************
                        // TODO insert update ingredient code here for backend work.
                        // this is just temporary code for testing purposes!
                        IngredientItem item = new IngredientItem(); // create temp object to hold all new values
                        item.setName(itemStr);                      //set the values
                        item.setCost(priceValue);
                        item.setMeasurementUnit(getUnit);
                        item.setWeight(amtPurchasedValue - amtUsedValue);   //forces always decrement upon use; always increment upon purchase

                        ID.updateIngredientInList(item);            //updating the item in the list

                        //******************************************************************************************
                        //int row = findInsertionPoint(itemStr);

                        //DTM.insertRow(row, item.toQOHString());

                        indgredentPanel.turnOffToolBar(true);
                        dispose();

                        System.out.println(itemStr + " has been updated.");

                    } else {
                        System.out.println(itemStr + " was not updated.");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, itemStr + " is not in the inventory.");
                }
            }
            else {
                        JOptionPane.showMessageDialog(this, "All textfields must be filled!");
                        resetList();

                    }
                } else if (e.getSource() == unitDropDownBox) {
                    getUnit = (String) unitDropDownBox.getSelectedItem();
                }
                else if(e.getSource() == cancel){
                    indgredentPanel.turnOffToolBar(true);
                    dispose();

                }
            }
        }



