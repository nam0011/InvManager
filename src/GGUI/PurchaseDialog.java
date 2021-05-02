package GGUI;

import com.company.IngredientDictionary;
import com.company.IngredientItem;
import com.company.InventoryManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class PurchaseDialog extends AbstractUpdateDialog{
    private SelfClearingPrice priceTF;
    private ArrayList<SelfClearingTextField> listTextFields;
    private InventoryManager IM = InventoryManager.getInventoryManager();

    PurchaseDialog(IngredientPanel panel, IngredientItem itemIn){

        super(panel, itemIn);
        setTitle("PURCHASE MORE " + itemIn.getName() + "?");
        buildDialog();
        listTextFields = getListTextFields();
        listTextFields.add(priceTF);

        IM = InventoryManager.getInventoryManager();

    }

    public void matching(boolean x){
        return;
    }

    @Override
    public void filleUN(boolean x) {

    }

    /**
     * Builds to Purchase Dialog Window
     */
    public void buildDialog()
    {
        GridBagConstraints gc = new GridBagConstraints();
        priceTF = new SelfClearingPrice("Price", 30);
        gc.insets = new Insets(4, 4, 4, 4);
        gc.gridx = 0;
        gc.gridy = 2;
        gc.fill = 2;
        add(priceTF, gc);
        pack();
    }

    /**
     * Checks for user input and calls backend methods for requested action
     * @param e using clicking purchase
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == getoKB()) {
            if (allFilled()) {
                    Object[] options = {"Yes", "no"};

                int n = JOptionPane.showOptionDialog(this,
                        "Are you sure you want to purchase "+getAmtTF().getText() +" "+getItem().getMeasurementUnit()+" of " + getItem().getName() + " for $" + String.format("%1$,.2f",priceTF.getValue()) ,

                        "Confirm", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                if (n == 0) {


                    IngredientItem item = getItem();    //this is the item we are wanting to update

                    //********************************************************************

                       item = IM.purchaseIngredientInList(getAmtTF().getValue(),priceTF.getValue(),item.getIndex());

                    //******************************************************************************************
                    int row = findInsertionPoint(item.getName());

                    getDTM().removeRow(row);
                    getDTM().insertRow(row, item.toQOHString());

                    getIngredientPanel().setDefaultFrameEnable(true);
                    dispose();

                    System.out.println(getItem().getName() + " has been updated.");
                } else {
                    System.out.println(getItem().getName() + " was not updated.");

                }
            }
            else {
                DisplayNotFillError();

            }

            } else if (e.getSource() == getCancel()) {
            getIngredientPanel().setDefaultFrameEnable(true);
            dispose();
        }

        }

    }

