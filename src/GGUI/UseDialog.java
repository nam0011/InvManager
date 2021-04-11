package GGUI;

import com.company.IngredientItem;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UseDialog extends AbstractUpdateDialog {
    public UseDialog(IngredientPanel panel, IngredientItem itemIn) {
        super(panel, itemIn);
        setTitle("How much " + itemIn.getName() + " did you use!");
        getAmtTF().setDefaultText("Amount used");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getoKB()) {
            if (allFilled()) {
                if (getItem().getWeight() < getAmtTF().getValue()) {
                    JOptionPane.showMessageDialog(this, "You do not have enough in stock.");
                } else {
                    Object[] options = {"Yes", "no"};

                    int n = JOptionPane.showOptionDialog(this,
                            "Are you sure you used" + getAmtTF().getValue() + " of " + getItem().getName(),

                            "Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == 0) {

                        IngredientItem iItem = getItem();

                        //*****************************************************************************************
                        // TODO insert add ingredient code here for backend work.
                        // this is just temporary code for testing purposes!
                        iItem.amountUsed(getAmtTF().getValue());

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

