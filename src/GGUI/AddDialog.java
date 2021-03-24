package GGUI;

import com.company.IngredientDictionary;
import com.company.IngredientItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddDialog extends JDialog implements ActionListener {
 private SelfClearingTextField itemNameTF;
 private SelfClearingNumbField amtPurchaseTF;
 private SelfClearingNumbField priceTF;
private JComboBox unitDropDownBox;
private ArrayList<SelfClearingTextField> listTextFields;
 private JButton oKB;
 private IngredientDictionary ID;
 private String getUnit;
 private String[] unitArray = new String[4];
 private IngredientPanelController ingredientPanelController;
 private DefaultTableModel DTM;



/*
TODO:
    Jonathan and Nathan,
    Suggest editing to not call the Ingredient Dictionary Instance again. What this will do is not add to the main
        ingredient dictionary and cause issues.
            My Suggestion:: Make this Add Dialog Pop Out Window take in the Information from the user and then set
                the information to Ingredient Item, then setting each Attribute with Input from the TextFields
                before closing the window. That way, back in the Ingredient Panel, All you have to do is add the
                Ingredient to the Ingredient Dictionary by having the AddDialog pass back an Ingredient Item.
    Now, Exception to this would be that when the Ingredient Already Existed.
        So, you would need it to be able to recreate the Add Dialog Window with the Text Fields Populated
        with Data Entered previously by Reading from the Ingredient Item passed back previously.
    Example Use of AddDialog, usable in IngredientPanel but needs to be a Recursive Method Call to Keep Throwing Error

    IngredientItem temp = new IngredientItem(AddDialog());
    //NextLine is incorrect method and causes error
    ID.addIngredient(temp); //Error occurs here because ingredient Already Exists based on Name
    //Fix is:
    try{
        ID.addIngredient(temp);
    }catch{
        AddDialog(temp);    //This would need for AddDialog to have Second Constructor to Also Pop Error Window
                            //That the Ingredient Already Exists.
    }

    So, I believe this same setup can be used to Add, Delete, and Update the main Ingredient Dictionary
        created in the Ingredient Panel by having recursive methods. This will allow for the Ingredient Panel
        to be Considered the Ingredient Controller. This will allow for future Expansions of Recipe Items Dictionary,
        Recipes with Instructions, and other Kitchen Needs such as the Cost Calculations more complex than we
        anticipate on doing.


                                    Cody
*/

    public  AddDialog(IngredientPanelController panel){
        setTitle("Add Item");
        ingredientPanelController = panel;
        DTM = ingredientPanelController.getDTM();
        ID = IngredientDictionary.getIngredientDictionary();
        setLayout(new GridBagLayout());
        JDialog j = new JDialog();

        buildDialog();
        setSize(300,300);
        pack();

        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Finds the insertion point.
     * @param name
     * @return
     */
    private int findInsertionPoint(String name)
    {
        DTM = ingredientPanelController.getDTM();
        int index = DTM.getRowCount();
        for(int i = 0; i < DTM.getRowCount(); i++)
        {
         if(name.compareTo((String)DTM.getValueAt(i, 0)) < 0) {
             index = i;
             break;
         }

        }

        return index;
    }
    private void buildDialog()
    {

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4,4,4,4);

        itemNameTF = new SelfClearingTextField("Item Name",30);
        itemNameTF.setSize(30,12);
        itemNameTF.setFont(new Font("New Times Roman" , Font.ITALIC, 12));
        amtPurchaseTF = new SelfClearingNumbField("Amount Purchased",10);

        priceTF = new SelfClearingNumbField("price", 30);

        setListTextFields();

        oKB = new JButton("Ok");
        oKB.addActionListener(this);
        unitArray = new String[]{"lb", "oz", "L", "mL"};
        getUnit = unitArray[0];
        unitDropDownBox = new JComboBox(unitArray);
        unitDropDownBox.addActionListener(this);





//Set the grid layout
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = 2;
        add(itemNameTF,gc);



        gc.gridx = 0;
        gc.gridy = 1;
        add(amtPurchaseTF,gc);

        gc.gridx = 1;
        gc.gridy = 1;
        add(unitDropDownBox, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        add(priceTF,gc);

        gc.gridx = 4;
        gc.gridy = 4;
        add(oKB,gc);

    }
    private void setListTextFields(){
        listTextFields = new ArrayList<>();
        listTextFields.add(itemNameTF);
        listTextFields.add(amtPurchaseTF);
        listTextFields.add(priceTF);


    }

    /**
     * This checks if all the text fields are nonblank.
     * @return
     */
    private boolean allFilled(){
        boolean all = true;
        for(int i = 0; i <listTextFields.size(); i++){
            all = (all && listTextFields.get(i).hasBeenClickedAndFilled());
            if(!all){
                break;
            }
        }
        return all;
    }

    /**
     * This method resets all the blank/nonclicked-on text field to default in the textfields.
     * I might need to move this to an utility method so other frames can use this.
     */
    private void resetList(){

        for(int i = 0; i <listTextFields.size(); i++){
            SelfClearingTextField curr = listTextFields.get(i);
            if(!(curr.hasBeenClickedAndFilled())){

                curr.reset();
            }
        }


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == oKB) {

            Object[] options = {"Yes", "no"};
            String itemStr = itemNameTF.getText();

            if (allFilled()) {
                itemStr = itemStr.toUpperCase();
                double amtPurchasedValue = amtPurchaseTF.getValue();
                double priceValue = priceTF.getValue();
                if (ID.searchForIngredient(itemStr)) {
                    JOptionPane.showMessageDialog(this, itemStr + " is already in the inventory.");
                } else {
                    int n = JOptionPane.showOptionDialog(this,
                            "Are you sure you want to add " + amtPurchasedValue + " "+getUnit+ " of "+ itemStr +" for $"+ priceValue + "?",

                            "Confirm", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == 0) {


                        System.out.println(itemStr + " has been added.");
                        int row = findInsertionPoint(itemStr);
                        Object newRow [] = {itemStr, amtPurchasedValue + " " +getUnit, priceValue};
                        DTM.insertRow(row, newRow);
                        //*****************************************************************************************
                        // TODO insert add ingredient code here for backend work.
                        // this is just temporary code for testing purposes!
                        IngredientItem item = new IngredientItem();
                        item.setName(itemStr);
                        item.setCost(priceValue);
                        item.setMeasurementUnit(getUnit);
                        item.setQuantityOnHand(amtPurchasedValue);
                        ID.addIngredientToList(item);

                        //******************************************************************************************
                        dispose();
                    } else {
                        System.out.println(itemStr + " was not added.");
                    }
                }

            }
            else{
                JOptionPane.showMessageDialog(this, "All textfields must be filled!");
                resetList();

            }
        }
        else if(e.getSource() == unitDropDownBox)
        {
            getUnit = (String) unitDropDownBox.getSelectedItem();
        }
    }
}


