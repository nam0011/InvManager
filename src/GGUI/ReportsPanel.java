package GGUI;

import com.company.ChangeLogger;
import com.company.InventoryManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportsPanel  extends abstractPanel implements ActionListener {
    InventoryManager IM = InventoryManager.getInventoryManager();
    private JTable changeTable;
    private JScrollPane scrollPane;
    ReportsPanel(DefaultFrame inFrame) {
        super(inFrame);

        changeTable = new JTable();
        DefaultTableModel intial = IM.getInventoryChangeLogger().getChangeDTM();

        changeTable.setModel(intial);

        buildChangeTable();

        setSize(new Dimension(450,450));
        add(scrollPane);

    }

    private void buildChangeTable(){

        changeTable.setDragEnabled(false);
        changeTable.setDefaultEditor(Object.class, null);
        //TODO

        TableColumnModel columnModel = changeTable.getColumnModel();
        columnModel.getColumn(1).setWidth(500);

         scrollPane = new JScrollPane(changeTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
