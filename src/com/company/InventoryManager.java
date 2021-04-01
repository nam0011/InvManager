package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;


public class InventoryManager {
    IngredientDictionary IngredientDictionary; //Copy instance of IngredientDictionary here to load inventory
    private static InventoryManager instance = null;
    IngredientFactory IngredientFactory;
    private static ChangeLogger InventoryChangeLogger = new ChangeLogger();
    private static FileManager FileUpdate = new FileManager();
    double initialInventorycost;

    public static InventoryManager getInventoryManager() {
        if (instance == null) {

            instance = new InventoryManager();
        }

        return instance;
    }

    public InventoryManager(){
        //TODO make so that it takes in a file name here for either Initial Setup or Demo Setup
        FileUpdate.setFileName("DataSource/ingredientsUPDATE.json");
        IngredientFactory = new IngredientFactory();
        createIngredientDictionary();
        initialInventorycost = IngredientDictionary.inventoryCost();
    }

    /**
     * Method to Get the Ingredient Item Linked List to be managed else where.
     * TODO look into security issues with passing this from the Dictionary.
     *
     * @return
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
     * Method to create the Ingredient Dictionary and House within the Inventory Manger for Ease of Access to the GUI
     * Designed for Future Development with Recipes being Managed as well
     */
    private void createIngredientDictionary(){
//        try {
//            FileManager.generateStringArrayList();
//            InventoryChangeLogger = new ChangeLogger();
//            //This will set the Original Ingredient File in Change Logger. This is the Raw read in String.
//            InventoryChangeLogger.setOriginalIngredientFile(FileManager.getStringArrayList());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        FileManager.createObjectArray();
//        IngredientFactory.startFactory(FileManager.getObjectArrayList());
      //  IngredientDictionary = IngredientDictionary.getIngredientDictionary();

//        try {
//            FileManager.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            FileManager.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Method to Generate/Create the JSON file for Ingredients before closing of program
     */


    /**
     * Method to Update a Single Item and Get its original for Storing the Changes made.
     * @param updateItem The Item to be updated.
     */
    public void updateIngredientItem(IngredientItem updateItem){
            //Gets the Original Item from the Dictionary Prior to Updating.
        IngredientItem original = new IngredientItem(this.IngredientDictionary.getIngredientItem(updateItem.getName()));
            //Records both Original and Updated Item versions to the Changelog
        InventoryChangeLogger.recordIngredientChange(ChangeLoggerAction.UPDATE, original, updateItem);
            //Updates the Item in the Ingredient Dictionary
        this.IngredientDictionary.updateIngredientInList(updateItem);
    }

    /**
     * Method to Add an Ingredient to the Inventory
     * @param addItem   The Ingredient Item to be Added
     */
    public void addIngredient(IngredientItem addItem){
            //Records the Item to be Added to the Ingredient Dictionary
        InventoryChangeLogger.recordIngredientChange(ChangeLoggerAction.ADD,addItem,null);
            //Adds the Item to the Ingredient Dictionary
        IngredientDictionary.addIngredientToList(addItem);
    }

    /**
     * Method to Remove/Delete from Inventory
     * @param removeItem    Ingredient Item to be Removed/Deleted
     */
    public void removeIngredient(IngredientItem removeItem){
            //Records the Item to Removed from the Ingredient Dictionary to the ChangeLog
        this.InventoryChangeLogger.recordIngredientChange(ChangeLoggerAction.DELETE,removeItem,null);
            //Removes the Item from the Ingredient Dictionary
        this.IngredientDictionary.removeIngredientFromList(removeItem);

    }


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
    public ArrayList<String> PromptForInput(){
        ArrayList<String> InputStream = new ArrayList<String>();

        return InputStream;
    }

    // I couldn't find a method to create an ingredient from scratch, so I just created one.
    // This can be deleted or moved somewhere else

    /*
    This is done with the IngredientItem Constructor, it can be created multiple ways to be passed around.
    Best not to have any other class creating an Ingredient Item like this.
     */
    public IngredientItem createIngredient(ArrayList<String> InputStream){
        //
        IngredientItem newIngredient = new IngredientItem();
        newIngredient.setName(InputStream.get(0));
        newIngredient.setType(InputStream.get(1));
        newIngredient.setCost(Double.parseDouble(InputStream.get(2)));
        newIngredient.setWeight(Double.parseDouble(InputStream.get(3)));
        newIngredient.setMeasurementUnit(InputStream.get(0));
        newIngredient.setQuantityOnHand(Double.parseDouble(InputStream.get(2)));
       // newIngredient.setLastUsedDate(InputStream.get(0)); //create a method to convert string to date
        return newIngredient;
    }






    public double calculateCost(String ingredient, double quantity){
        /*TODO
        *This I think would be the following formula
        *Beginning Inventory (at the beginning of the year)
        *Plus Purchases and Other Costs
        *Minus Ending Inventory (at the end of the year)
        *Equals Cost of Goods Sold.

         */
    //Do we prompt for quantity of item in question we are trying to calculate?
        //Unit calculation comes here
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
    public boolean doesIngredientExist(IngredientItem ingredient){
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

}
