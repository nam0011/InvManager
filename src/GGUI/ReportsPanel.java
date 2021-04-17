package GGUI;

import com.company.ChangeLogger;
import com.company.InventoryManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ReportsPanel  extends abstractPanel implements ActionListener {
    InventoryManager IM = InventoryManager.getInventoryManager();
    private JTable changeTable;
    private JScrollPane scrollPane;
    private JButton saveAll;
    private JButton discardAll;

    ReportsPanel(DefaultFrame inFrame) {
        super(inFrame);
        saveAll = new JButton("Save All");
        saveAll.addActionListener(this);
        discardAll = new JButton("Discard all");
        discardAll.addActionListener(this);

        changeTable = new JTable();
        DefaultTableModel intial = IM.getInventoryChangeLogger().getChangeDTM();

        changeTable.setModel(intial);

        buildChangeTable();

        buildLayout();
        setSize(new Dimension(450,450));


    }

    private void buildLayout(){
        setLayout(new GridBagLayout());
        //build toolbar
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(saveAll);
        toolBar.add(discardAll);

        GridBagConstraints gc = new GridBagConstraints();
        //placing the layout
        gc.gridy = 0;
        gc.gridx = 0;
        add(scrollPane, gc);

        gc.gridy = 1;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.anchor = GridBagConstraints.SOUTHEAST;
        add(toolBar, gc);

    }

    private void buildChangeTable(){

        changeTable.setDragEnabled(false);
        changeTable.setDefaultEditor(Object.class, null);
        //TODO

        TableColumnModel columnModel = changeTable.getColumnModel();
        columnModel.getColumn(1).setWidth(500);

         scrollPane = new JScrollPane(changeTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    }

    /**
     * action check to decide whether we want to save all progress or not
     * @param e user button choice on reports panel
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == saveAll)
        {
            try {
                IM.UpdateJSONFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            IM.getInventoryChangeLogger().emptyChangeDTM();
        }
        else if (e.getSource() == discardAll)
        {
            Object[] options = {"Yes", "no"};
            int n =JOptionPane.showOptionDialog(this, "Are you sure you want to discard all changes?", "Discard all", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

            if(n == 0){
                /*TODO we need to the following
                    1. revert JSON file to the backup.
                    2. revert Ingredient Dictionary to the reflect all the back up

                    3.revert the Default table model back to    

                 */

                //#2
                IM.revertDTMandIDAL();

            }
            else if (n == 1){

            }
        }
    }
}
