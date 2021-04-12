package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Collections;
import java.util.ListIterator;

public class IngredientDictionary {

    private ArrayList<IngredientItem> ingredientItemArrayList;
    private static IngredientDictionary instance = null;
    private static ChangeLogger ingredientChangeLogger = new ChangeLogger();
    private static FileManager FileUpdate = new FileManager();

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
        for (int i = 0; i < ingredientItemArrayList.size(); i++) {

            this.ingredientItemArrayList.add(ingredientItemArrayList.get(i));

        }

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
               break;
            }
        }
        //Exception Handling for ingredient not in array list.
        /*
        try {
            for (int i = 0; i > -1; i++) {
                if (ingredientItemArrayList.get(i).getName().equals(ingredientName)) {
                    i = -2;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(ingredientName + ":: Ingredient Not Found");
        }*/
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
        boolean alreadyExists = ingredientCheck(ingredientItem);

//TODO fix the nonsense. - Jay
        if (alreadyExists == false) {
            System.out.println("Safe to Add to List");
            this.ingredientItemArrayList.add(ingredientItem);
            System.out.println(ingredientItem.getName() + ":: Has Been Added To the List");
            ingredientChangeLogger.recordIngredientChange(ChangeLoggerAction.ADD, ingredientItem, ingredientItem);
            return true;
        } else {
            System.out.println("Error, Ingredient" + ingredientItem.getName() + "Already Exists");
            return false;
        }
    }

    /**
     * Method to Remove the Ingredient from the List
     * TODO add exception for when the Ingredient Does Not exist in the list.
     *
     * @param ingredientItem The ingredient Item to be removed from the list.
     * @return Boolean Value to be returned to verify the operation succeeded.
     */
    public void removeIngredientFromList(IngredientItem ingredientItem) {
        boolean exists = ingredientCheck(ingredientItem);

        if (exists == true) {
            for (int i = 0; i < this.ingredientItemArrayList.size(); i++) {
                if (this.ingredientItemArrayList.get(i).getName().equals(ingredientItem.getName())) {
                    String temp = this.ingredientItemArrayList.get(i).getName();
                    this.ingredientItemArrayList.remove(i);
                    System.out.println(temp + ":: Removed From Ingredient Dictionary List");
                    ingredientChangeLogger.recordIngredientChange(ChangeLoggerAction.DELETE, ingredientItem, ingredientItem);
                }
            }
        }
    }


    /**
     * Method to Update a Single Ingredient Item that exists in the list.
     * UPDATES BY REPLACING THE SAME INGREDIENT IN THE LIST WITH A NEW VERSION OF THE ITEM
     *
     * @param updateItem The Ingredient Item to be updated
     * @return Boolean Value to be returned to verify the operation succeeded.
     */
    public IngredientItem updateIngredientInList(IngredientItem updateItem) {

            for (IngredientItem ingredientItem : this.ingredientItemArrayList) {                         //start iterating through the list
                if (ingredientItem.getName().equals(updateItem.getName())) {   //if found we need to do some math and push to a list
                    double ogPrice = ingredientItem.getCost();  //set the original price and quantity of the found item before changes
                    double ogQuant = ingredientItem.getWeight();

                    ingredientItem.setOGPrice(ogPrice); //hold these values we need to display them later
                    ingredientItem.setOGQuant(ogQuant);

                    double priceDif;   //find the difference between the prices and store in a temp value name price difference
                    double priceChangeRatio;

                    if (updateItem.getCost() != 0) {//if there is a difference in price we want a percentage value of that difference
                        priceDif = updateItem.getCost() - ogPrice;
                        if(priceDif < 0){
                            priceDif = -1*priceDif;
                        }
                        priceChangeRatio = updateItem.getCost() / ogPrice;    //store that value

                        //double meanPrice = (updateItem.getCost()+ogPrice)/2;
                        //average price per unit?

                        ingredientItem.setPriceChangeRatio(priceChangeRatio);
                        ingredientItem.setPriceDiff(priceDif);
                        double meanPrice = (((updateItem.getCost()*updateItem.getWeight())+(ogPrice*ogQuant))/(updateItem.getWeight()+ogQuant));    //not universal enough but works for current build testing means 4-2-21

                        ingredientItem.setCost(meanPrice);
                    } else {
                        ingredientItem.setCost(ogPrice);
                    }

                    double quantDif = updateItem.getWeight() - ogQuant;   //Update Dialog handles our decrementing values therefore we only need to use simple addition and will always update properly
                    ingredientItem.setQuantDiff(quantDif);
                    double quantChangeRatio;

                    if (quantDif != 0) {//if there is a difference in quantity we want a percentage value of that difference and we need to know if the input value is incrementing or decrementing our inventory
                        if (updateItem.getWeight() > 0) {        //if incrementing inventory amount
                            quantChangeRatio = updateItem.getWeight() / ogQuant;    //find the change ratio
                            ingredientItem.setQuantChangeRatio(quantChangeRatio);
                            ingredientItem.setWeight(ogQuant + updateItem.getWeight()); //increment the weight of the item in the list

                        } else if (updateItem.getWeight() < 0) {    //if decrementing
                            quantChangeRatio = -1 * (updateItem.getWeight() / ogQuant);    //use the negative reciprocal we always want a positive ratio
                            ingredientItem.setQuantChangeRatio(quantChangeRatio);
                            ingredientItem.setWeight(ogQuant + updateItem.getWeight()); //decrement the weight of the item in the list
                        }
                    } else {  //if no change(this will never be the case if update is filled out but here for continuity
                        ingredientItem.setWeight(ogQuant);
                    }
                    updateItem = ingredientItem;    //make sure that all updates are pushed back to the object pushed into function
                    //ingredientChangeLogger.recordIngredientChange(ChangeLoggerAction.UPDATE, updateItem, updateItem);
                }
            }

        return updateItem;  //return the updated object
    }


    public IngredientItem purchaseIngredientInList(IngredientItem purchaseItem){

        for (IngredientItem ingredientItem : this.ingredientItemArrayList) {                         //start iterating through the list
            if (ingredientItem.getName().equals(purchaseItem.getName())) {   //if found we need to do some math and push to a list
                double ogPrice = purchaseItem.getOGPrice();  //set the original price and quantity of the found item before changes
                double ogQuant = purchaseItem.getOGQuant();

                double priceDif;   //find the difference between the prices and store in a temp value name price difference

                if (purchaseItem.getCost() != 0) {//if there is a difference in price we want a percentage value of that difference
                    ingredientItem.setPriceDiff(purchaseItem.getCost() - ogPrice);  //store it
                    if( ingredientItem.getPriceDiff() < 0){
                        ingredientItem.setPriceDiff(-1*ingredientItem.getPriceDiff());  //make sure its always a positive difference
                    }

                    ingredientItem.setPriceChangeRatio(purchaseItem.getCost() / ogPrice); // store that value

                    double meanPrice = (((purchaseItem.getCost()*purchaseItem.getWeight())+(ogPrice*ogQuant))/(purchaseItem.getWeight()+ogQuant));    //not universal enough but works for current build testing means 4-2-21

                    ingredientItem.setCost(meanPrice);
                } else {
                    ingredientItem.setCost(ogPrice);
                }

                double quantDif = purchaseItem.getWeight() - ogQuant;   //Update Dialog handles our decrementing values therefore we only need to use simple addition and will always update properly
                ingredientItem.setQuantDiff(quantDif);
                double quantChangeRatio;

//i dont think we need half this shit since we are only incrementing i just left it the way it was
            //   if (quantDif != 0) {//if there is a difference in quantity we want a percentage value of that difference and we need to know if the input value is incrementing or decrementing our inventory
               //    if (purchaseItem.getWeight() > 0) {        //if incrementing inventory amount
                        quantChangeRatio = purchaseItem.getWeight() / ogQuant;    //find the change ratio
                        ingredientItem.setQuantChangeRatio(quantChangeRatio);
                        ingredientItem.setWeight(ogQuant + purchaseItem.getWeight()); //increment the weight of the item in the list

               //     } else if (purchaseItem.getWeight() < 0) {    //if decrementing
               //         quantChangeRatio = -1 * (purchaseItem.getWeight() / ogQuant);    //use the negative reciprocal we always want a positive ratio
               //         ingredientItem.setQuantChangeRatio(quantChangeRatio);
               //         ingredientItem.setWeight(ogQuant + purchaseItem.getWeight()); //decrement the weight of the item in the list
               //     }
              //  } else {  //if no change(this will never be the case if update is filled out but here for continuity
                //    ingredientItem.setWeight(ogQuant);
              //  }
                purchaseItem = ingredientItem;    //make sure that all updates are pushed back to the object pushed into function
                ingredientChangeLogger.recordIngredientChange(ChangeLoggerAction.UPDATE, purchaseItem, purchaseItem);   //feed it to the changelogger
            }
        }

        return purchaseItem;    //return the updated item
    }



    /**
     * Method convert Ingredient Array List to an Array List of String Printouts
     * @return
     */
    public ArrayList<String> convertToStringArrayList(){
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.clear();
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

        FileUpdate.setFileName("DataSource/ingredientsUPDATE.json");
        FileUpdate.setStringArrayList(this.convertToStringArrayList());

        try {
            FileUpdate.generateJSONFile(FileType.INGREDIENTS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

//hello
