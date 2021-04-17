package com.company;

//import jdk.internal.icu.text.UnicodeSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;

public class ChangeLogger {


    private JTable changeTable;
    private DefaultTableModel changeDTM;
    private static final String changeLogFilePath = "DataSource/ChangeLog.json";


    /**
     * Building the changelogger table
     */
    public ChangeLogger() {
        changeTable = new JTable();


        String[] header = new String[] {"Ingredient Name", "Old Price & Cost", "New Price & cost", "Change Type" };
        changeDTM = new DefaultTableModel(header, 0);

    }

    /**
     * Method to Record the Change Being Made* @param action    The Enumerated Type ChangeLoggerAction to be recorded
     * @param original  The Original Item if Updating Item
     * @param change    The Changed Item if Updating Item
     */
//TODO try switch statement catch some exception
    public void recordIngredientChange(ChangeLoggerAction action, IngredientItem original, IngredientItem change)
    {
        String[] newRow = this.createRow(original, action);
        changeDTM.addRow(newRow);
    }

    /**
     * Getter for changeTable
     * @return the changeTable
     */
    public JTable getChangeTable() {
        return changeTable;
    }

    /**
     * Default table module for change table getter
     * @return default table module for change table
     */
    public DefaultTableModel getChangeDTM() {
        return changeDTM;
    }

    /**
     * Creates the row as seen in the defaultTableModel.
     * @param item
     * @param action
     * @return
     */
    private String[] createRow(IngredientItem item , ChangeLoggerAction action){
        String[] row = new String[4];
        String na ="N/A";
        row[0] = item.getName();
        String ogPriceWeight = String.format("$%1$,.2f", item.getOGPrice())+ " @ " + String.format("%1$,.2f",item.getOGQuant())+ item.getMeasurementUnit();

        String newPriceWeight = String.format("$%1$,.2f", item.getCost())+ " @ " + String.format("%1$,.2f",item.getWeight())+ item.getMeasurementUnit();;

        switch(action) {
            case ADD:
                row[1] = na;//original
                row[2] = newPriceWeight;//current
                row[3] = action.name();//type of change
                break;
            case DELETE:
                row[1] = newPriceWeight;
                row[2] = na;
                row[3] = action.name();

                break;

            default:
                row[1] =ogPriceWeight;
                row[2] = newPriceWeight;
                row[3] = action.name();





        }
        return row;
    }

    /**
     * Method to empty all of the change table
     */
    public void emptyChangeDTM(){

        int n = changeDTM.getRowCount();
        for(int r = n-1; r >= 0; r--){

            changeDTM.removeRow(r);

        }
    }

    /**
     * If they're are any changes it will return true.
     * @return boolean if there are changes - false not changes - true
     */
    public boolean anyChanges(){

        return (changeDTM.getRowCount() != 0);
    }

    //end of ChangeLogger classs
}
