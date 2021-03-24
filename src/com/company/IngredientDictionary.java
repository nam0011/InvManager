package com.company;

import java.io.IOException;
import java.util.ArrayList;

import java.util.ListIterator;

public class IngredientDictionary {

    private ArrayList<IngredientItem> ingredientItemArrayList;
    private static  IngredientDictionary instance = null;
    private ChangeLogger ingredientChangeLogger;
    private IngredientFactory ingredientFactory;
    private FileManager fileManager;
    private static double initialCost;



    /**
     * Method to create the Ingredient Dictionary and initiated within the IngredientController Class
     *  as Singleton Class
     */
    private void createIngredientDictionary(){
        try {
            fileManager = new FileManager("DataSource/ingredients.json");
            ingredientFactory = new IngredientFactory();
            fileManager.generateStringArrayList();
            this.ingredientChangeLogger = new ChangeLogger();
            //This will set the Original Ingredient File in Change Logger. This is the Raw read in String.
            ingredientChangeLogger.setOriginalIngredientFile(fileManager.getStringArrayList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileManager.createObjectArray();
        ingredientFactory.startFactory(fileManager.getObjectArrayList());
        this.setIngredientItemArrayList(ingredientFactory.getList());

    }

    /**
     * Gets instance the single instance of IngredientFact
     */
    public static IngredientDictionary getIngredientDictionary()
    {
        if(instance == null)
        {

            instance = new IngredientDictionary();
        }

        return instance;

    }

    /*
    If the GUI takes the Ingredient Dictionary and create the instance. Then--
    GuI Says add, delete, update,
    Create Separate Class to do the Cost calculations, Change Calculations, and Other Actions to be
        performed on the Ingredient Dictionary.
    This will allow for the GuI to have the Results of the Calculations returned in the needed DataType
     */

    /**
     * Constructor of the Ingredient Dictionary for the Singleton Instance
     */
    private IngredientDictionary()
    {
        this.ingredientItemArrayList = new ArrayList<>();
        this.createIngredientDictionary();

    }

    /**
     * Creates the Array list for the Singleton
     * @param ingredientItemArrayList
     */
    public void setIngredientItemArrayList(ArrayList<IngredientItem> ingredientItemArrayList){
        this.ingredientItemArrayList = new ArrayList<>();
        for(int i = 0; i < ingredientItemArrayList.size(); i++){

            this.ingredientItemArrayList.add(ingredientItemArrayList.get(i));

        }

    }

    /**
     * Method to Get the Ingredient Item Linked List to be managed else where.
     * TODO look into security issues with passing this from the Dictionary.
     * @return
     */
    public ArrayList<IngredientItem> getIngredientItemArrayList() {
        return ingredientItemArrayList;
    }

    /**
     * Method allows you to get a single Ingredient Item referenced by name
     * @param ingredientName    Name of the Ingredient Item to returned
     * @return  Returns Ingredient Item Extracted from the Array List
     */
    public IngredientItem getIngredientItem(String ingredientName){

        IngredientItem tempIngredientItem = null;
        for(int i = 0; i < this.ingredientItemArrayList.size(); i++){
            if(this.ingredientItemArrayList.get(i).getName().equals(ingredientName)){
                tempIngredientItem = new IngredientItem(this.ingredientItemArrayList.get(i));
            }
        }
        //Exception Handling for ingredient not in array list.
        try{
            for(int i=0;i>-1;i++){
                if (ingredientItemArrayList.get(i).getName().equals(ingredientName)){
                    i=-2;
                }
            }
        }
        catch(IndexOutOfBoundsException e){
            System.out.println(ingredientName+":: Ingredient Not Found");
        }
        return tempIngredientItem;
    }

    /**
     * Method to check if an Ingredient Name Already Exists in the Array List
     * @param ingredientName Name of the Ingredient to be Searched for
     * @return  Boolean Value to be returned to verify the operation succeeded.
     * This is a duplicate method so that if the "Name" of the Ingredient Item needs to be searched for.
     */
    public boolean searchForIngredient(String ingredientName){
        boolean isIngredient = false;
        try{
            for(int i = 0; i < this.ingredientItemArrayList.size(); i++){
                if(this.ingredientItemArrayList.get(i).getName().equals(ingredientName)){
                    isIngredient = true;
                }
            }
        }
        catch(IndexOutOfBoundsException e){
            System.out.println(ingredientName+":: Ingredient Not Found");
        }

        return isIngredient;
    }

    /**
     * Method to check if an Ingredient Already Exists in the Array List
     * @param ingredientItem
     * @return  Boolean Value to be returned to verify the operation succeeded.
     * This is a duplicate method so that if other elements of the Ingredient Item need to be searched for
     * TODO implement elements to check other elements of the Ingredient Item
     */
    public boolean searchForIngredient(IngredientItem ingredientItem){

        boolean isIngredient = false;
        try{
            for(int i = 0; i < this.ingredientItemArrayList.size(); i++){
                if(this.ingredientItemArrayList.get(i).getName().equals(ingredientItem.getName())){
                    System.out.println("Found Ingredient ::" + this.ingredientItemArrayList.get(i).getName());
                    isIngredient = true;
                }
            }
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
        //TODO add exception Handling here
        //if(isIngredient == false) System.out.println(ingredientItem.getName() + " :: Error Not Found");

        return isIngredient;
    }

    /**
     * Method to Add Ingredient Item to the List,
     * TODO add exception call for when the Ingredient Already Exists in the list
     * @param ingredientItem    The ingredient Item to be added.
     * @return  Boolean Value to be returned to verify the operation succeeded.
     */

    public boolean addIngredientToList(IngredientItem ingredientItem){
        boolean alreadyExists = searchForIngredient(ingredientItem);


        if(alreadyExists == false){
            System.out.println("Safe to Add to List");
            ingredientChangeLogger.recordIngredientChange(
                    ChangeLoggerAction.ADD,
                    ingredientItem,
                    null);
            this.ingredientItemArrayList.add(ingredientItem);
            System.out.println(ingredientItem.getName() + ":: Has Been Added To the List");
            return true;
        }else {
            System.out.println("Error, Ingredient" + ingredientItem.getName() + "Already Exists");
            return false;
        }
    }

    /**
     * Method to Remove the Ingredient from the List
     * TODO add exception for when the Ingredient Does Not exist in the list.
     * @param ingredientItem    The ingredient Item to be removed from the list.
     * @return  Boolean Value to be returned to verify the operation succeeded.
     */
    public void removeIngredientFromList(IngredientItem ingredientItem){
       boolean exists = searchForIngredient(ingredientItem);

        if(exists == true) {
            for (int i = 0; i < this.ingredientItemArrayList.size(); i++) {
                if (this.ingredientItemArrayList.get(i).getName().equals(ingredientItem.getName())) {
                    String temp = this.ingredientItemArrayList.get(i).getName();
                    ingredientChangeLogger.recordIngredientChange(
                            ChangeLoggerAction.DELETE,
                            this.ingredientItemArrayList.get(i),
                            null);
                    this.ingredientItemArrayList.remove(i);
                    System.out.println(temp + ":: Removed From Ingredient Dictionary List");
                }
            }
        }
    }

    /**
     * Method to Update a Single Ingredient Item that exists in the list.
     * UPDATES BY REPLACING THE SAME INGREDIENT IN THE LIST WITH A NEW VERSION OF THE ITEM
     * @param updateItem    The Ingredient Item to be updated
     * @return  Boolean Value to be returned to verify the operation succeeded.
     */
    public boolean updateIngredientInList(IngredientItem updateItem){
        ingredientChangeLogger.recordIngredientChange(
                ChangeLoggerAction.UPDATE,
                this.getIngredientItem(updateItem.getName()),
                updateItem);
        this.removeIngredientFromList(updateItem);
        this.addIngredientToList(updateItem);
        return true;
    }

    /**
     * Method convert Ingredient Array List to an Array List of String Printouts
     * @return
     */
    public ArrayList<String> convertToStringArrayList(){
        ArrayList<String> stringArrayList = new ArrayList<>();
        for(int i = 0; i < this.ingredientItemArrayList.size(); i++){
            stringArrayList.add(this.ingredientItemArrayList.get(i).toJSONString());
        }
        return stringArrayList;
    }
    /**
     * This method results the total cost of all items in the inventory
     */
    public double inventoryCost(){
        double finalCost = 0;
        for (IngredientItem i:ingredientItemArrayList) {
           finalCost+=i.getCost();
       }
        return finalCost;
    }
    /**
     * Method for Testing, Prints out all Items in the Ingredient Item Array List
     */
    public void printDictionary(){
        ListIterator<IngredientItem> itr = this.ingredientItemArrayList.listIterator(0);
        while(itr.hasNext()){
            System.out.println(itr.next().toJSONString());
        }
    }

    /**
     * Method to Populate the Data Model Tables with Each Ingredient Being Returned
     * Currently Called in Ingredient Panel
     * @param i
     * @return
     */
    public String[] printDictionary(int i){
        return this.ingredientItemArrayList.get(i).toQOHString();
    }

    /**
     * Method to Generate/Create the JSON file for Ingredients before closing of program
     */
    public void saveIngredientDictionary(){
        fileManager = new FileManager();
        //TODO Update to reflect a file name being passed in, either from initial setup or from a demo setup
        fileManager.setFileName("DataSource/IngredientsTEST.json");
        fileManager.setStringArrayList(this.convertToStringArrayList());

        try {
            fileManager.generateJSONFile(FileType.INGREDIENTS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to return the Size of the Ingredient Dictionary
     * @return
     */
    public int getSize(){
        return this.ingredientItemArrayList.size();
    }

    /**
     * Method to Get the Initial Cost of the Ingredient Dictionary at the Time of Load.
     * @return
     */
    public static double getInitialCost() {
        return initialCost;
    }

    /**
     * This returns the current cost of all items in the inventory.
     * @return A double which represents the current cost of all items in the inventory
     * */
    public double curInventoryCost(){
        return this.inventoryCost();
    }
    /**
     * This returns the difference between the initial cost of all items and the current cost.
     * @return A double which represents the cost difference.
     * */
    public double costDifference(){
        return this.initialCost - curInventoryCost();
    }

    /**
     * This function takes in a string of the ingredient name and returns it's index.
     *
     *
     * @param ingredientItem String of the
     * @return
     */

    public int searchForIngredientIndex(String ingredientItem){
        int index = -1;
        ingredientItem = ingredientItem.toUpperCase();
        try{
            for(int i = 0; i < this.ingredientItemArrayList.size(); i++){
                if(this.ingredientItemArrayList.get(i).getName().equals(ingredientItem)){

                    index = i;
                    break;
                }
            }
        }
        catch(NullPointerException e){
            System.out.println(e.getMessage());
        }
        //TODO add exception Handling here
        //if(isIngredient == false) System.out.println(ingredientItem.getName() + " :: Error Not Found");

        return index;
    }
}