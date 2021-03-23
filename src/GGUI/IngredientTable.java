package GGUI;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IngredientTable extends JTable implements ActionListener {

    String[] header;
    String[] data;
    public IngredientTable(){
        header = new String[]{"Ingredient","price","amount on hand"};
        //data = new String[]{{" c","c ","c "}};
        //JTable table = new JTable(header, data);
        getColumnModel().getColumn(0).setMaxWidth(30);
        getRowSelectionAllowed();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == getSelectedRows()){


        }
    }
}
