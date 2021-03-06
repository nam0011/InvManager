package com.company;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class InventoryManager {
    IngredientDictionary ID = IngredientDictionary.getIngredientDictionary();
    FileManager FileManager;
    private static InventoryManager instance = null;
    private Account account;// This is the account logged in.
    public static ChangeLogger getInventoryChangeLogger() {
        return InventoryChangeLogger;
    }
    public void emptyChangeDTM(){

        InventoryChangeLogger.emptyChangeDTM();
    }

    private static ChangeLogger InventoryChangeLogger = new ChangeLogger();
    private static FileManager FileUpdate = new FileManager();
    private IngredientItem CurrentItem; //This is the currentitem for the iterator section of Inventory Manager
    private int IngredientSize; //Size of Ingredient Dictionary
    private int CurIndexIngredient = 0; //Index of current Ingredient Item
    double initialInventorycost;
    private DefaultTableModel ingDTM;

    public static InventoryManager getInventoryManager() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }
    //TODO ADD ELEMENTS TO MAKE IT MORE LIKE AN ITERATOR
    /**
     * This class initializes the Inventory Manager. It sets the default files for loading and populating the various dictionaries, future updates of the inventory and gets required information.
     * */
    private InventoryManager(){
        setDefaultFile("DataSource/ingredients.json"); //IF IT EVER BREAKS CHECK HERE FIRST

        try {
            FileManager.generateStringArrayList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileManager.createObjectArray();
        //createIngredientDictionary();
        //This should replace method in DemoSetup that initially populates the array

        ID.startFactory(FileManager.getObjectArrayList());//This cuts out the middleman of IngredientFactory.
        //BuildTheDefaultTableModel.





        try {
            FileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    /**
     * This function allows the file used to populate the dictionary be easily changed.
     * This acts as an iterator even though the json file is not a class, but it defines the IngredientDictionary class and is the basis on which IngredientDictionaries are built.
     * This allows multiple IngredientDictionaries to be iterated through.
     * @param name The string directory of the file used to populate the inventory.
     * */

    public void setDefaultFile(String name){
        FileManager = new FileManager(name);
    }
    public void setAccount(Account accountIn){
        account = accountIn;

    }
    /**
     * Method to Get the Ingredient Item Linked List to be managed else where.
     * TODO look into security issues with passing this from the Dictionary.
     *
     * @return The main list that contains ingredient items.
     */
    public ArrayList<IngredientItem> getIngredientItemArrayList() {
        return ID.getIngredientItemArrayList();
    }

    /**
     * Method allows you to get a single Ingredient Item referenced by name
     *
     * @param ingredientName Name of the Ingredient Item to returned
     * @return Returns Ingredient Item Extracted from the Array List
     */
    public IngredientItem getIngredientItem(String ingredientName) {

        return ID.getIngredientItem(ingredientName);

    }

    /**
     * Method to access the next ingredient item while iterating through the list.
     * @return The next IngredientItem
     */

    public IngredientItem nextIngredient(){
        if (CurIndexIngredient<IngredientSize-1){
            CurIndexIngredient++;
        }
        CurrentItem = ID.getIngredientItemArrayList().get(CurIndexIngredient);

        return CurrentItem;
    }
    /**
     * Method to access the current ingredient item while iterating through the list.
     * @return The current IngredientItem
     */
    public IngredientItem currentIngredient(){
        return CurrentItem;
    }
    /**
     * Method to access the previous ingredient item while iterating through the list.
     * @return The previous IngredientItem
     */
    public IngredientItem prevIngredient(){
        if (CurIndexIngredient>0){
            CurIndexIngredient--;
        }
        CurrentItem = ID.getIngredientItemArrayList().get(CurIndexIngredient);

        return CurrentItem;
    }


    /**
     * Method to Add an Ingredient to the Inventory
     * @param addItem   The Ingredient Item to be Added
     */
    public void addIngredientToList(IngredientItem addItem){
            //Records the Item to be Added to the Ingredient Dictionary
        InventoryChangeLogger.recordIngredientChange(ChangeLoggerAction.ADD,addItem,null);
            //Adds the Item to the Ingredient Dictionary
        ID.addIngredientToList(addItem);
    }

    /**
     * Method to Remove/Delete from Inventory
     * @param removeItem    Ingredient Item to be Removed/Deleted
     */
    public void removeIngredientFromList(IngredientItem removeItem){
            //Records the Item to Removed from the Ingredient Dictionary to the ChangeLog
        this.InventoryChangeLogger.recordIngredientChange(ChangeLoggerAction.DELETE,removeItem,null);
            //Removes the Item from the Ingredient Dictionary
        this.ID.removeIngredientFromList(removeItem);

    }

    public IngredientItem purchaseIngredientInList(Double amtPurch, Double newPrice, int index){
        //Updates for purchasing the Item from the Ingredient Dictionary
        //purchasedItem =this.ID.purchaseIngredientInList(purchasedItem);
        IngredientItem purchasedItem = ID.purchaseIngredientInList(amtPurch,newPrice,index);
        //Records the Item to be Purchased from the Ingredient Dictionary to the ChangeLog
        this.InventoryChangeLogger.recordIngredientChange(ChangeLoggerAction.PURCHASE,purchasedItem,null);

        return purchasedItem;
    }

    public IngredientItem useIngredientInList(Double amtUsed, int index){
        //Updates for Use Item from the Ingredient Dictionary
        IngredientItem usedItem = this.ID.useIngredientInList(amtUsed, index);
        //usedItem = ID.useIngredientInList(usedItem);
        //Records the Item to be Used from the Ingredient Dictionary to the ChangeLog
        this.InventoryChangeLogger.recordIngredientChange(ChangeLoggerAction.USE,usedItem,null);


        return usedItem;
    }




/**
 * This returns the current cost of all items in the inventory.
 * @return A double which represents the current cost of all items in the inventory
 * */
    public double curInventoryCost(){
        return ID.inventoryCost();
    }
    /**
     * This returns the difference between the initial cost of all items and the current cost.
     * @return A double which represents the cost difference.
     * */
    public double costDifference(){
        return initialInventorycost - curInventoryCost();
    }

    /**
     * Method will Retrieve a copy of the Ingredient Item Needed
     * @param ingredientName    The name of the Ingredient Item to be Retrieved
     * @return  Returns the requested Ingredient Item
     * TODO add Exception Handler for when the Ingredient Item Does not
     */
    public IngredientItem getIngredient(String ingredientName){
        return ID.getIngredientItem(ingredientName);
    }

    /**
     * Method Checks to see an Ingredient Item Exists in the Inventory Dictionary
     * @param ingredient    The Ingredient Item to be Searched for
     * @return  Boolean Value based on result
     */
    public boolean ingredientCheck(String ingredient){
        return ID.ingredientCheck(ingredient);
    }

    /**
     * This method updates the backup ingredient dictionary files.
     */

    public void UpdateBackups() throws IOException {

        //TODO the following line of code is causing problems
        Collections.sort(ID.getIngredientItemArrayList());

        FileUpdate.setFileName("DataSource/ingredientsBACKUP.json");
        FileUpdate.setStringArrayList(ID.convertToStringArrayList());

        try {
            FileUpdate.generateJSONFile(FileType.INGREDIENTS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileUpdate.setFileName("DataSource/ingredientsBACKUP2.json");
        FileUpdate.setStringArrayList(ID.convertToStringArrayList());

        try {
            FileUpdate.generateJSONFile(FileType.INGREDIENTS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method updates the default ingredient dictionary file.
     */

    public void UpdateJSONFile() throws IOException {
        Collections.sort(ID.getIngredientItemArrayList());


        FileManager.setStringArrayList(ID.convertToStringArrayList());

        try {
            FileManager.generateJSONFile(FileType.INGREDIENTS);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Account getAccount() {
        return account;
    }

    public String[] printDictionary(int i){
        return ID.printDictionary(i);
    }

    public DefaultTableModel getIngDTM() {
        return ID.getIngDTM();
    }

    /**
     * This method reverts back the Default Table Model for the ingredient table and the
     * arraylist and the dictionary.
     */

    public void revertDTMandIDAL(){
        FileManager backup = new FileManager("DataSource/ingredients.json");

        try {
            backup.generateStringArrayList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        backup.createObjectArray();

        //This should replace method in DemoSetup that initially populates the array

        ID.revertDatabases(backup.getObjectArrayList());
        //BuildTheDefaultTableModel.



        try {
            FileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InventoryChangeLogger.emptyChangeDTM();
    }


    /**
     * method to check if there is anything waiting to push from
     * changelogger table to backend files
     * @return true or false if any objects exist here
     */
    public boolean anyChanges(){

        return InventoryChangeLogger.anyChanges();
}
}
