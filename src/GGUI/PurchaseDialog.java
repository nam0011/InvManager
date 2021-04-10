package GGUI;

import com.company.IngredientItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class PurchaseDialog extends AbstractUpdateDialog{
    private SelfClearingNumbField priceTF;
    private ArrayList<SelfClearingTextField> listTextFields;

    PurchaseDialog(IngredientPanel panel, IngredientItem itemIn){

        super(panel, itemIn);
        setTitle("PURCHASE MORE " + itemIn.getName() + "?");
        buildDialog();
        getListTextFields().add(priceTF);


    }
    public void buildDialog()
    {
        GridBagConstraints gc = new GridBagConstraints();
        priceTF = new SelfClearingNumbField("price", 30);
        gc.insets = new Insets(4, 4, 4, 4);
        gc.gridx = 0;
        gc.gridy = 2;
        gc.fill = 2;
        add(priceTF, gc);
        pack();

    }







    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == getoKB()) {
            if (allFilled()) {
                    Object[] options = {"Yes", "no"};

                int n = JOptionPane.showOptionDialog(this,
                        "Are you sure you want to purchase "+getAmtTF().getText() +" "+getItem().getMeasurementUnit()+" of " + getItem().getName() + " for " + priceTF.getValue() ,

                        "Confirm", JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                if (n == 0) {

                    IngredientItem iItem = getItem();

                    //*****************************************************************************************
                    // TODO insert add ingredient code here for backend work.
                    // this is just temporary code for testing purposes!

                        iItem.purchase(priceTF.getValue(), getAmtTF().getValue());

                    //******************************************************************************************
                    int row = findInsertionPoint(iItem.getName());

                    getDTM().removeRow(row);
                    getDTM().insertRow(row, iItem.toQOHString());
                    getIngredientPanel().setDefaultFrameEnable(true);
                    dispose();
                } else {
                    System.out.println(getItem().getName() + " was not updated.");

                }
            }

            } else if (e.getSource() == getCancel()) {
                getIngredientPanel().setDefaultFrameEnable(true);
                dispose();
            }
        }

    }

