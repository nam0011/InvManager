package GGUI;

import com.company.IngredientDictionary;

import javax.swing.*;
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

    public  AddDialog(JPanel panel){
        setTitle("Add Item");
        ID = IngredientDictionary.getIngredientDictionary();
        setLayout(new GridBagLayout());
        JDialog j = new JDialog();

        buildDialog();
        setSize(300,300);
        pack();
        //setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
     * This method resets all the blank/nonclicked-on text field to default message.
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
                if (ID.ingredientCheck(itemStr)) {
                    JOptionPane.showMessageDialog(this, itemStr + " is already in the inventory.");
                } else {
                    int n = JOptionPane.showOptionDialog(this,
                            "Are you sure you want to add " + amtPurchasedValue + " "+getUnit+ " of "+ itemStr +" for $"+ priceValue + "?",

                            "Confirm", JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == 0) {


                        System.out.println(itemStr + " has been added.");
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


