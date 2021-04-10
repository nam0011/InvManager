package GGUI;

import com.company.IngredientDictionary;
import com.company.IngredientItem;
import com.company.InventoryManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class IngredientPanel extends abstractPanel implements ActionListener {

    private JTabbedPane tabbedPane;
    private JToolBar ingreToolBar;
    private SelfClearingTextField ingreSearchTF;
    private JButton ingreSearchB;
    private JButton ingreAddB;
    private JButton ingrePurchaseB;
    private JButton ingreUseB;
    private JButton ingreRemoveB;
    private JButton ingreListAllB;
    private JTable ingredientTable;
    private JScrollPane tablePane;
    private JScrollPane scrollPane;
    private DefaultTableModel DTM;
    private DefaultFrame defaultFrame;
    InventoryManager IM = InventoryManager.getInventoryManager();
    ArrayList<IngredientItem> IL;
    public IngredientPanel(DefaultFrame inframe){
        super(inframe);
        setLayout(new GridBagLayout());
        defaultFrame = inframe;
        tablePane = new JScrollPane();

        buildIngrePanel();
        buildIngredientTable();
        setLayout();

    }



    private void filter(String search)
    {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(DTM);
        ingredientTable.setRowSorter(tr);

        tr.setRowFilter(RowFilter.regexFilter(search));
    }

    /**
     * This builds the ingredient table, connects the default table model to the table, and creates
     * the scrollabe panal
     */
    private void buildIngredientTable(){

        IL=IM.getIngredientItemArrayList();
        String[][] data;
        int n = IL.size();

        String[] header = new String[] {"Ingredient Name", "Quantity on hand", "Cost"};


        ingredientTable = new JTable();
        ingredientTable.getTableHeader().setReorderingAllowed(false);

        DTM = (DefaultTableModel) ingredientTable.getModel();

        DTM.addColumn(header[0]);
        DTM.addColumn(header[1]);
        DTM.addColumn(header[2]);
        ingredientTable.setRowSelectionAllowed(true);



        for(int r = 0; r < n; r++) {

            DTM.addRow(IM.printDictionary(r));

        }

        ingredientTable.setPreferredScrollableViewportSize(new Dimension(500, 50));
        ingredientTable.setDragEnabled(false);
        ingredientTable.setDefaultEditor(Object.class, null);
        //TODO

        TableColumnModel columnModel = ingredientTable.getColumnModel();
        columnModel.getColumn(1).setWidth(500);

        scrollPane = new JScrollPane(ingredientTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        scrollPane.setPreferredSize(new Dimension(450, 450));
        GridBagConstraints gc = new GridBagConstraints();



    }
    public DefaultTableModel getDTM(){

        return DTM;
    }

    /**
     * The following method just adds the buttons to the toolbar and the toolbar to the panel
     * It also adds the listeners to the buttons.
     */
    private void buildIngrePanel(){


    ingreToolBar = new JToolBar();
    ingreToolBar.setFloatable(false);

    ingreSearchTF = new SelfClearingTextField("Search", 60);



    ingreSearchTF.addKeyListener(new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            String search = ingreSearchTF.getText().toUpperCase();
            filter(search);
        }
    });

    ingreAddB =new JButton("Add");

    ingrePurchaseB =new JButton("Purchase");

    ingreRemoveB = new JButton("Remove");
    ingreUseB = new JButton("Use"); //TODO think of better name!!


    ingreSearchTF.setColumns(12);

    //The following code builds toolbar;
        {



        ingreToolBar.add(ingreSearchTF);
        ingreToolBar.add(ingreAddB);
        ingreToolBar.add(ingrePurchaseB);
        ingreToolBar.add(ingreUseB);
        ingreToolBar.add(ingreRemoveB);

         }

    // The following code adds the listener to the  Buttons.
    {


        ingreAddB.addActionListener(this);
        ingrePurchaseB.addActionListener(this);
        ingreRemoveB.addActionListener(this);
        ingreUseB.addActionListener(this);



    }







}
/**
 * Set the layout
 */
private void setLayout(){
    GridBagConstraints gc = new GridBagConstraints();

    //gc.insets = new Insets(4, 4, 4, 4);



    gc.gridx = 0;
    gc.gridy = 0;
    add(ingreToolBar,gc);

    gc.gridx = 0;
    gc.gridy = 1;
    add(scrollPane,gc);

}




    /**
     * The actionPerformed method determines what button you pressed and performs a certain action via a series of if-else statements
     * @param e
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ingreAddB)
        {
            new AddDialog(this);






            System.out.println("Add");
        }
        else if(e.getSource() == ingrePurchaseB)
        {
            int updateIndex[] = ingredientTable.getSelectedRows();
            if(updateIndex.length != 1)
            {
                JOptionPane.showMessageDialog(this, "Please only select one item from the table.");
            }

            else {
                String itemName = (String)ingredientTable.getValueAt(updateIndex[0],0);
                IngredientItem itemObj = IM.getIngredientItem(itemName);
                new PurchaseDialog(this, itemObj);

                System.out.println("purchase");
            }

        }
        else if(e.getSource() == ingreRemoveB)
        {
            int removeIndex[] = ingredientTable.getSelectedRows();


            int n = removeIndex.length;
            ArrayList<String> removeNames = new ArrayList<>();
            String curr;
            for(int r = 0; r < n; r++)
            {
                System.out.println(removeIndex[r]);
                System.out.println(DTM.getValueAt(r, 0));
                curr = (String)ingredientTable.getValueAt(removeIndex[r], 0);
                removeNames.add(curr);
            }

            new RemoveDialog(this, removeNames);


        }
        else if(e.getSource() == ingreUseB){
            int updateIndex[] = ingredientTable.getSelectedRows();
            if(updateIndex.length != 1)
            {
                JOptionPane.showMessageDialog(this, "Please only select one item from the table.");
            }

            else {
                String itemName = (String) ingredientTable.getValueAt(updateIndex[0], 0);
                IngredientItem itemObj = IM.getIngredientItem(itemName);
                new UseDialog(this, itemObj);

            }
        }
        if(e.getSource() != ingreSearchTF && !ingreSearchTF.hasBeenClickedAndFilled()){
            ingreSearchTF.reset();
        }

    }

}
