package GGUI;

import com.company.IngredientDictionary;
import com.company.IngredientItem;
import com.company.InventoryManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class AddDialog extends JDialog implements ActionListener {
    private SelfClearingTextField itemNameTF;
    private SelfClearingNumbField amtPurchaseTF;
    private SelfClearingPrice priceTF;
    private JComboBox unitDropDownBox;
    private ArrayList<SelfClearingTextField> listTextFields;
    private JButton oKB;
    private JButton cancel;
    private InventoryManager IM;
    private String getUnit;
    private String[] unitArray = new String[4];
    private DefaultTableModel DTM;
    private IngredientPanel ingredientPanel;

    public AddDialog(IngredientPanel panel) {
       

        setTitle("Add Item");

        panel.setEnabled(false);

        setResizable(false);


        ingredientPanel = panel;

        //Sets the dialogbox on top frame and disables the DefaultFrame
        setAlwaysOnTop(true);
        setLocation(150,150);
        ingredientPanel.setDefaultFrameEnable(false);

        DTM = ingredientPanel.getDTM();
        IM = InventoryManager.getInventoryManager();
        setLayout(new GridBagLayout());
        JDialog j = new JDialog();

        buildDialog();
        setSize(300, 300);
        pack();
        //setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // call terminate
                ingredientPanel.setDefaultFrameEnable(true);
                dispose();

            }
        });

    }

    private void buildDialog() {

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 1, 4, 1);

        itemNameTF = new SelfClearingTextField("Item Name", 30);
        itemNameTF.setSize(30, 12);

        itemNameTF.setFont(new Font("New Times Roman", Font.ITALIC, 12));
        amtPurchaseTF = new SelfClearingNumbField("Amount Purchased", 10);

        priceTF = new SelfClearingPrice("price", 30);

        setListTextFields();

        oKB = new JButton("Ok");
        oKB.addActionListener(this);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);

        unitArray = new String[]{"LB", "oz", "L", "mL"};
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
     * @param name
     * @return
     */
    private int findInsertionPoint(String name) {

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
                    double priceValue = priceTF.getValue();
                    if (IM.ingredientCheck(itemStr)) {
                        JOptionPane.showMessageDialog(this, itemStr + " is already in the inventory.");
                    } else {
                        IngredientItem item = new IngredientItem();
                        item.setName(itemStr);  //set name
                        item.setCost(priceValue);   //set cost to display on ingredients tab
                        item.setOGPrice(priceValue);    //set original cost to display on reports tab
                        item.setMeasurementUnit(getUnit);   //set measurement unit
                        item.setWeight(amtPurchasedValue);  //set the incoming weight on ingredients tab
                        item.setOGQuant(amtPurchasedValue); //set the original weight to display on reports tab

                        String itemStrArr[]= item.toQOHString();

                        int n = JOptionPane.showOptionDialog(this,
                                "Are you sure you want to add " + itemStrArr[1] + " of " + itemStr + " for "+ itemStrArr[2] + "?",

                                "Confirm", JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (n == 0) {



                            //*****************************************************************************************
                            // TODO insert add ingredient code here for backend work.
                            // this is just temporary code for testing purposes!

                            IM.addIngredientToList(item);

                            //******************************************************************************************
                            int row = findInsertionPoint(itemStr);

                            DTM.insertRow(row, item.toQOHString());
                            ingredientPanel.setDefaultFrameEnable(true);
                            dispose();
                        } else {
                            System.out.println(itemStr + " was not added.");
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "All textfields must be filled!");

                    resetList();

                }
            } else if (e.getSource() == unitDropDownBox) {
                getUnit = (String) unitDropDownBox.getSelectedItem();
            }
            else if(e.getSource() == cancel)
            {
                ingredientPanel.setDefaultFrameEnable(true);
                dispose();

            }
        }
    }



