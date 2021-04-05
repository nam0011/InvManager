package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class InventoryManager {
    IngredientDictionary IngredientDictionary = com.company.IngredientDictionary.getIngredientDictionary(); //Copy instance of IngredientDictionary here to load inventory
    FileManager FileManager;
    private static InventoryManager instance = null;
    IngredientFactory IngredientFactory;
    private static ChangeLogger InventoryChangeLogger = new ChangeLogger();
    IngredientFactory testFactory = new IngredientFactory();
    private static FileManager FileUpdate = new FileManager();
    private IngredientItem CurrentItem; //This is the currentitem for the iterator section of Inventory Manager
    private int IngredientSize; //Size of Ingredient Dictionary
    private int RecipeSize; //Size of Recipe Dictionary
    private int CurIndexIngredient = 0; //Index of current Ingredient Item
    double initialInventorycost;

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
    public InventoryManager(){
        setDefaultFile("DataSource/ingredients.json"); //IF IT EVER BREAKS CHECK HERE FIRST
        FileUpdate.setFileName("DataSource/ingredientsUPDATE.json");
        try {
            FileManager.generateStringArrayList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileManager.createObjectArray();
        //createIngredientDictionary();
        //This should replace method in DemoSetup that initially populates the array
        testFactory.startFactory(FileManager.getObjectArrayList());

        //TODO Jonathan
        IngredientDictionary.setIngredientItemArrayList(testFactory.getList());

        try {
            FileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialInventorycost = IngredientDictionary.inventoryCost();
        CurrentItem = IngredientDictionary.getIngredientItemArrayList().get(0);
        IngredientSize = IngredientDictionary.getIngredientItemArrayList().size();

    }
    /**
     * This function allows the file used to populate the dictionary be easily changed.
     * @param name The string directory of the file used to populate the inventory.
     * */

    public void setDefaultFile(String name){
        FileManager = new FileManager(name);
    }
    /**
     * Method to Get the Ingredient Item Linked List to be managed else where.
     * TODO look into security issues with passing this from the Dictionary.
     *
     * @return The main list that contains ingredient items.
     */
    public ArrayList<IngredientItem> getIngredientItemArrayList() {
        return IngredientDictionary.getIngredientItemArrayList();
    }

    /**
     * Method allows you to get a single Ingredient Item referenced by name
     *
     * @param ingredientName Name of the Ingredient Item to returned
     * @return Returns Ingredient Item Extracted from the Array List
     */
    public IngredientItem getIngredientItem(String ingredientName) {

        IngredientItem tempIngredientItem = null;
        for (int i = 0; i < IngredientDictionary.getIngredientItemArrayList().size(); i++) {
            if (IngredientDictionary.getIngredientItemArrayList().get(i).getName().equals(ingredientName)) {
                tempIngredientItem = new IngredientItem(IngredientDictionary.getIngredientItemArrayList().get(i));
            }
        }
        //Exception Handling for ingredient not in array list.
        try {
            for (int i = 0; i > -1; i++) {
                if (IngredientDictionary.getIngredientItemArrayList().get(i).getName().equals(ingredientName)) {
                    i = -2;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ingredientName + ":: Ingredient Not Found");
        }
        return tempIngredientItem;
    }

    /**
     * Method to access the next ingredient item while iterating through the list.
     * @return The next IngredientItem
     */

    public IngredientItem nextIngredient(){
        if (CurIndexIngredient<IngredientSize-1){
            CurIndexIngredient++;
        }
        CurrentItem = IngredientDictionary.getIngredientItemArrayList().get(CurIndexIngredient);

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
        CurrentItem = IngredientDictionary.getIngredientItemArrayList().get(CurIndexIngredient);

        return CurrentItem;
    }

//    private void createIngredientDictionary(){
////        try {
////            FileManager.generateStringArrayList();
////            InventoryChangeLogger = new ChangeLogger();
////            //This will set the Original Ingredient File in Change Logger. This is the Raw read in String.
////            InventoryChangeLogger.setOriginalIngredientFile(FileManager.getStringArrayList());
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        FileManager.createObjectArray();
////        IngredientFactory.startFactory(FileManager.getObjectArrayList());
//      //  IngredientDictionary = IngredientDictionary.getIngredientDictionary();
//
////        try {
////            FileManager.close();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////        try {
////            FileManager.close();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }



    /**
     * Method to Update a Single Item and Get its original for Storing the Changes made.
     * @param updateItem The Item to be updated.
     */
    public IngredientItem updateIngredientInList(IngredientItem updateItem){
            //Gets the Original Item from the Dictionary Prior to Updating.
        IngredientItem original = new IngredientItem(this.IngredientDictionary.getIngredientItem(updateItem.getName()));
            //Records both Original and Updated Item versions to the Changelog
        InventoryChangeLogger.recordIngredientChange(ChangeLoggerAction.UPDATE, original, updateItem);
            //Updates the Item in the Ingredient Dictionary
        return this.IngredientDictionary.updateIngredientInList(updateItem);
    }

    /**
     * Method to Add an Ingredient to the Inventory
     * @param addItem   The Ingredient Item to be Added
     */
    public void addIngredientToList(IngredientItem addItem){
            //Records the Item to be Added to the Ingredient Dictionary
        InventoryChangeLogger.recordIngredientChange(ChangeLoggerAction.ADD,addItem,null);
            //Adds the Item to the Ingredient Dictionary
        IngredientDictionary.addIngredientToList(addItem);
    }

    /**
     * Method to Remove/Delete from Inventory
     * @param removeItem    Ingredient Item to be Removed/Deleted
     */
    public void removeIngredientFromList(IngredientItem removeItem){
            //Records the Item to Removed from the Ingredient Dictionary to the ChangeLog
        this.InventoryChangeLogger.recordIngredientChange(ChangeLoggerAction.DELETE,removeItem,null);
            //Removes the Item from the Ingredient Dictionary
        this.IngredientDictionary.removeIngredientFromList(removeItem);

    }
    /**
     * Method to Search for an Ingredient in the Inventory
     * @param searchInput The string corresponding to the IngredientItem to be searched
     * @return The result of the search
     */

    public IngredientItem searchIngredient(String searchInput){

        IngredientItem SearchResult = IngredientDictionary.getIngredientItem(searchInput);

//        if(!SearchResult.equals(null)){
//            return SearchResult;
//        }else {
//            System.out.println("Ingredient Not Found");
//            return null;
//        }   //TODO change to through exception
//
//       if (SearchResult.equals(null)){
//            System.out.println("Ingredient not found. ");
//        }
        return SearchResult;
    }



    //This function will prompt the user for details about the new ingredient.
    // All attributes will go into the arraylist.

    /*This can be handled through the GUI and it's controller, then it can pass the new ingredient to be added
    * to the "addIngredient()" method
    * It would do so by prompting user to enter the relevant data, then store that in a temporary IngredientItem object
    * That would then be passed to the Inventory Manager's addIngredient() method.
    */
    //CODY FUNCTION I HAVEN'T FOUND USE FOR JUST YET
    public ArrayList<String> PromptForInput(){
        ArrayList<String> InputStream = new ArrayList<String>();

        return InputStream;
    }
    /**
     * This calculates the cost of a specific quantity of an ingredient
     * @param ingredient The string ingredient to be searched for
     * @param quantity The quantity of the inredient searched
     * @return An int that represents the cost of the quantity of the ingredient item
     * */
    public double calculateCost(String ingredient, double quantity){

        return quantity*searchIngredient(ingredient).getCost();
    }
/**
 * This returns the current cost of all items in the inventory.
 * @return A double which represents the current cost of all items in the inventory
 * */
    public double curInventoryCost(){
        return IngredientDictionary.inventoryCost();
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
        return IngredientDictionary.getIngredientItem(ingredientName);
    }

    /**
     * Method Checks to see an Ingredient Item Exists in the Inventory Dictionary
     * @param ingredient    The Ingredient Item to be Searched for
     * @return  Boolean Value based on result
     */
    public boolean ingredientCheck(String ingredient){
        return IngredientDictionary.ingredientCheck(ingredient);
    }


    public void UpdateJSONFile() throws IOException {
        Collections.sort(IngredientDictionary.getIngredientItemArrayList());

        FileUpdate.setFileName("DataSource/ingredientsUPDATE.json");
        FileUpdate.setStringArrayList(IngredientDictionary.convertToStringArrayList());

        try {
            FileUpdate.generateJSONFile(FileType.INGREDIENTS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] printDictionary(int i){
        return IngredientDictionary.printDictionary(i);
    }
}
