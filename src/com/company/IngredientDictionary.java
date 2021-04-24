package com.company;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

public class IngredientDictionary {

    private ArrayList<IngredientItem> ingredientItemArrayList;
    private static IngredientDictionary instance = null;
    private static ChangeLogger ingredientChangeLogger = new ChangeLogger();
    private static FileManager FileUpdate = new FileManager();
    private DefaultTableModel ingDTM;


    /**
     * Gets instance the single instance of IngredientFact
     */
    public static IngredientDictionary getIngredientDictionary() {
        if (instance == null) {

            instance = new IngredientDictionary();
        }

        return instance;
    }

    private IngredientDictionary() {
        this.ingredientItemArrayList = new ArrayList<>();
        this.ingDTM = new DefaultTableModel();
        String[] header = new String[] {"Ingredient Name", "Quantity on hand", "Cost"};
        ingDTM.addColumn(header[0]);
        ingDTM.addColumn(header[1]);
        ingDTM.addColumn(header[2]);


    }

    /**
     * Method to Populate the Data Model Tables with Each Ingredient Being Returned
     * Currently Called in Ingredient Panel
     *
     * @param i
     * @return
     */
    public String[] printDictionary(int i) {
        return this.ingredientItemArrayList.get(i).toQOHString();
    }

    /**
     * Creates the Array list for the Singleton
     *
     * @param ingredientItemArrayList
     */
    public void setIngredientItemArrayList(ArrayList<IngredientItem> ingredientItemArrayList) {
        this.ingredientItemArrayList = new ArrayList<>();
        for (int i = 0; i < ingredientItemArrayList.size(); i++) {

            this.ingredientItemArrayList.add(ingredientItemArrayList.get(i));

        }

    }


    /**
     * Constructor to Create a Clone of the Ingredient Item Array List
     *
     * @param ingredientItemArrayList The Ingredient Item Array List to be Cloned
     */
    public IngredientDictionary(ArrayList<IngredientItem> ingredientItemArrayList) {
        this.ingredientItemArrayList = new ArrayList<>();
        for (int i = 0; i < ingredientItemArrayList.size(); i++) {

            this.ingredientItemArrayList.add(ingredientItemArrayList.get(i));

        }
    }

    /**
     * Method to Get the Ingredient Item Linked List to be managed else where.
     * TODO look into security issues with passing this from the Dictionary.
     *
     * @return
     */
    public ArrayList<IngredientItem> getIngredientItemArrayList() {
        return ingredientItemArrayList;
    }

    /**
     * Method allows you to get a single Ingredient Item referenced by name
     *
     * @param ingredientName Name of the Ingredient Item to returned
     * @return Returns Ingredient Item Extracted from the Array List
     */
    public IngredientItem getIngredientItem(String ingredientName) {

        IngredientItem tempIngredientItem = null;
        for (int i = 0; i < this.ingredientItemArrayList.size(); i++) {
            if (this.ingredientItemArrayList.get(i).getName().equals(ingredientName)) {
                tempIngredientItem = new IngredientItem(this.ingredientItemArrayList.get(i));
                tempIngredientItem.setIndex(i);
               break;
            }
        }

        return tempIngredientItem;
    }

    /**
     * Method to check if an Ingredient Name Already Exists in the Array List
     *
     * @param ingredientName Name of the Ingredient to be Searched for
     * @return Boolean Value to be returned to verify the operation succeeded.
     * This is a duplicate method so that if the "Name" of the Ingredient Item needs to be searched for.
     */
    public boolean ingredientCheck(String ingredientName) {
        boolean isIngredient = false;
        try {
            for (int i = 0; i < this.ingredientItemArrayList.size(); i++) {
                if (this.ingredientItemArrayList.get(i).getName().equals(ingredientName)) {
                    isIngredient = true;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ingredientName + ":: Ingredient Not Found");
        }

        return isIngredient;
    }

    /**
     * Method to check if an Ingredient Already Exists in the Array List
     *
     * @param ingredientItem The object of the ingredient to be searched for
     * @return Boolean Value to be returned to verify the operation succeeded.
     * This is a duplicate method so that if other elements of the Ingredient Item need to be searched for
     * TODO implement elements to check other elements of the Ingredient Item
     */
    public boolean ingredientCheck(IngredientItem ingredientItem) {

        boolean isIngredient = false;
        try {
            for (int i = 0; i < this.ingredientItemArrayList.size(); i++) {
                if (this.ingredientItemArrayList.get(i).getName().equals(ingredientItem.getName())) {
                    System.out.println("Found Ingredient ::" + this.ingredientItemArrayList.get(i).getName());
                    isIngredient = true;
                    break;
                }
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        //TODO add exception Handling here
        //if(isIngredient == false) System.out.println(ingredientItem.getName() + " :: Error Not Found");

        return isIngredient;
    }

    /**
     * Method to Add Ingredient Item to the List,
     * TODO add exception call for when the Ingredient Already Exists in the list
     *
     * @param ingredientItem The ingredient Item to be added.
     * @return Boolean Value to be returned to verify the operation succeeded.
     */

    public boolean addIngredientToList(IngredientItem ingredientItem) {

            System.out.println("Safe to Add to List");
            ingredientItem.setOGPrice(ingredientItem.getCost());
            ingredientItem.setOGQuant(ingredientItem.getWeight());
            this.ingredientItemArrayList.add(ingredientItem);
            //TODO This means the DTM and the arrayList doesn't match.


            return true;

    }

    /**
     * Method to Remove the Ingredient from the List
     * TODO add exception for when the Ingredient Does Not exist in the list.
     *
     * @param ingredientItem The ingredient Item to be removed from the list.
     * @return Boolean Value to be returned to verify the operation succeeded.
     */
    public void removeIngredientFromList(IngredientItem ingredientItem) {



            for (int i = 0; i < this.ingredientItemArrayList.size(); i++) {
                if (this.ingredientItemArrayList.get(i).getName().equals(ingredientItem.getName())) {
                    String temp = this.ingredientItemArrayList.get(i).getName();
                    this.ingredientItemArrayList.remove(i);
                    System.out.println(temp + ":: Removed From Ingredient Dictionary List");

                }
            }

    }


    /**
     * Method to purchase a new amount of an item currently in the inventory list
     * @param  - the incoming object with pertinent information for use in calculations
     * @return  purchaseItem - the same object is pushed back after being updated
     */
    public IngredientItem purchaseIngredientInList(Double amtPurch,Double newPrice, int index){

        IngredientItem ingredientItem = ingredientItemArrayList.get(index);                         //start iterating through the list
        ingredientItem.purchaseItem(amtPurch, newPrice);

        return ingredientItem;

    }

    /**
     * Method to do calculations for using an amount of an item currently in the inventory list
     * @param  - the incoming object with pertinent information for use in calculations
     * @return  usedItem - the same object is pushed back after being updated
     */
    public IngredientItem useIngredientInList(Double amtUsed, int index) {



        IngredientItem ingredientItem = ingredientItemArrayList.get(index);
        ingredientItem.useItem(amtUsed);




            return ingredientItem;    //return the updated item

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

    public void UpdateJSONFile() throws IOException {
        Collections.sort(ingredientItemArrayList);

        FileUpdate.setFileName("DataSource/ingredientsBACKUP.json");
        FileUpdate.setStringArrayList(this.convertToStringArrayList());

        try {
            FileUpdate.generateJSONFile(FileType.INGREDIENTS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Method for generating the initial array list of the IngredientDictionary.
     * @param ingredientStrings  An arraylist of string that contains information to construct IngredientItems
     */
    public void startFactory(ArrayList<ArrayList<String>> ingredientStrings){


        for(int i = 0; i<ingredientStrings.size(); i++){
            IngredientItem temp = new IngredientItem(ingredientStrings.get(i));
            this.ingredientItemArrayList.add(temp);
            ingDTM.addRow(temp.toQOHString());
        }
    }

    /**
     * method to be used to revert to original database when changes are opted to not be saved
     * @param ingredientStrings the arraylist in question
     */
    public void revertDatabases(ArrayList<ArrayList<String>> ingredientStrings){

        ingredientItemArrayList.removeAll(ingredientItemArrayList);

        removeAllRows();
        for(int i = 0; i<ingredientStrings.size(); i++){
            IngredientItem temp = new IngredientItem(ingredientStrings.get(i));
            this.ingredientItemArrayList.add(temp);
            this.ingDTM.addRow(temp.toQOHString());
        }
    }

    /**
     * get a default table module to but UI tables
     * @return the ingredient DFT
     */
    public DefaultTableModel getIngDTM() {
        return ingDTM;
    }


    private void removeAllRows(){
        int n = ingDTM.getRowCount();
        for(int r = n-1; r >= 0; r--){

                ingDTM.removeRow(r);

        }
    }


}