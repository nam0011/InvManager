package GGUI;

import com.company.IngredientDictionary;
import com.company.IngredientItem;
import com.company.InventoryManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UseDialog extends AbstractUpdateDialog {

    private InventoryManager IM = InventoryManager.getInventoryManager();

    /**
     * builds the use dialog window
     * @param panel the panel layout we want
     * @param itemIn the item we want to edit
     */
    public UseDialog(IngredientPanel panel, IngredientItem itemIn) {
        super(panel, itemIn);
        amtTF.setDefaultText("Amount Used");
        setTitle("How much " + itemIn.getName() + " did you use!");
        IM = InventoryManager.getInventoryManager();
    }

    /**
     * action listener to perform UX methods based on user choice of button
     * @param e users choice of button of use dialog window
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getoKB()) {
            if (allFilled()) {
                if (getItem().getWeight() < getAmtTF().getValue()) {
                    JOptionPane.showMessageDialog(this, "You do not have enough in stock.");
                } else {
                    Object[] options = {"Yes", "no"};

                    int n = JOptionPane.showOptionDialog(this,
                            "Are you sure you used " + getAmtTF().getValue() + " " + getItem().getMeasurementUnit()+ " of " + getItem().getName(),

                            "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == 0) {

                        IngredientItem item = getItem();

                        item.setOGQuant(item.getWeight());
                        item.setOGPrice(item.getCost());
                        item.setCost(0);
                        item.setWeight(getAmtTF().getValue());

                        item = IM.useIngredientInList(item);

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
            }
            else
                {
                    JOptionPane.showMessageDialog(this, "All textfields must be filled!");
                    resetList();
                }
            } else if (e.getSource() == getCancel()) {
                getIngredientPanel().setDefaultFrameEnable(true);
                dispose();
            }
        }



    }

