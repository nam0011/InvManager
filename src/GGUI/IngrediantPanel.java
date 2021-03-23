package GGUI;

import com.company.IngredientDictionary;
import com.company.IngredientItem;

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
import java.util.LinkedList;

public class IngrediantPanel extends JPanel implements ActionListener {

    private JTabbedPane tabbedPane;
    private JToolBar ingreToolBar;
    private SelfClearingTextField ingreSearchTF;
    private JButton ingreSearchB;
    private JButton ingreAddB;
    private JButton ingreUpdateB;
    private JButton ingreRemoveB;
    private JButton ingreListAllB;
    private JTable ingredientTable;
    private JScrollPane tablePane;
    private JScrollPane scrollPane;
    private DefaultTableModel DTM;
    IngredientDictionary ID = IngredientDictionary.getIngredientDictionary();
    ArrayList<IngredientItem> IL;
    public IngrediantPanel(){

        tablePane = new JScrollPane();

        buildIngrePanel();
        buildIngredientTable();

    }

    private void filter(String search)
    {
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(DTM);
        ingredientTable.setRowSorter(tr);

        tr.setRowFilter(RowFilter.regexFilter(search));
    }
    private void buildIngredientTable(){

        IL=ID.getIngredientItemArrayList();
        String[][] data;
        int n = IL.size();
        data = new String[n][2];
        String[] header = new String[] {"ingredient", "Amount on hand"};



        ingredientTable = new JTable();
        DTM = (DefaultTableModel) ingredientTable.getModel();
        ingredientTable.setRowSelectionAllowed(true);
        DTM.addColumn("Ingredient");
        DTM.addColumn("Amount on hand");
        String[] rowData;
        for(int r = 0; r < n; r++) {
            rowData = new String[]{IL.get(r).getName(), String.valueOf(IL.get(r).getWeight()) + " " + IL.get(r).getMeasurementUnit()};
            DTM.addRow(rowData);


        }

        ingredientTable.setPreferredScrollableViewportSize(new Dimension(500, 50));
        ingredientTable.setDragEnabled(false);
        ingredientTable.setDefaultEditor(Object.class, null);
        //TODO

        TableColumnModel columnModel = ingredientTable.getColumnModel();
        columnModel.getColumn(1).setWidth(500);

        scrollPane = new JScrollPane(ingredientTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        scrollPane.setPreferredSize(new Dimension(450, 450));
        add(scrollPane);


    }

    /**
     * The following method just adds the buttons to the toolbar and the toolbar to the panel
     * It also adds the listeners to the buttons.
     */
    private void buildIngrePanel(){


    ingreToolBar =new JToolBar();
    ingreToolBar.setFloatable(false);
    ingreSearchB =new JButton("Search");
    ingreSearchTF =new SelfClearingTextField("Search", 60);

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

    ingreUpdateB =new JButton("Update");

    ingreRemoveB = new JButton("Remove");

    ingreListAllB =new JButton("List All");

    //The following code builds toolbar;
        {
            ingreSearchTF.setColumns(12);
        add(ingreSearchTF);


        ingreToolBar.add(ingreSearchB);
        ingreToolBar.add(ingreAddB);
        ingreToolBar.add(ingreUpdateB);
        ingreToolBar.add(ingreRemoveB);
        ingreToolBar.addSeparator();
        ingreToolBar.add(ingreListAllB);
         }

    // The following code adds the listener to the  Buttons.
    {

        ingreSearchB.addActionListener(this);
        ingreAddB.addActionListener(this);
        ingreUpdateB.addActionListener(this);
        ingreRemoveB.addActionListener(this);
        ingreListAllB.addActionListener(this);


    }
      add(ingreToolBar);







}

    /**
     * The actionPerformed method determines what button you pressed and performs a certain action via a series of if-else statements
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ingreSearchB) {
            System.out.println(ingreSearchTF.getText());
        }
        else if(e.getSource() == ingreAddB)
        {

            new AddDialog(this);

            System.out.println("Add");
        }
        else if(e.getSource() == ingreUpdateB)
        {
            System.out.println("Update");

        }
        else if(e.getSource() == ingreRemoveB)
        {
            System.out.println("Remove");
        }
        else if(e.getSource() == ingreListAllB){

            System.out.println("list all");

        }
    }

}
