package com.company;

import java.io.IOException;

public class DemoInventory {

    FileManager FileManager = new FileManager("DataSource/ingredients.json");
    IngredientFactory testFactory = new IngredientFactory();
    IngredientDictionary ingredientDictionary;

    public DemoInventory(){

        this.createDictionary();
        testInventory();
    }

    public void createDictionary(){
        try {
            FileManager.generateStringArrayList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileManager.createObjectArray();
        testFactory.startFactory(FileManager.getObjectArrayList());
        ingredientDictionary = new IngredientDictionary(testFactory.getList());
        try {
            FileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

   public void testInventory(){
        System.out.println("Initialize Inventory");
        InventoryManager Manager = new InventoryManager();
        System.out.println("Successfully loaded Inventory");
        double curcost = Manager.curInventoryCost();
        double initcost = Manager.initialInventorycost;
        //IngredientItem test = Manager.searchIngredient("BEEFS BROTH");
        //Manager.removeIngredient(Manager.searchIngredient("BEEFS BROTH"));
        //Manager.removeIngredient(Manager.searchIngredient("BROWN SUGAR"));
        double newcost = Manager.curInventoryCost();
        System.out.println("Initial Cost: "+initcost);
       System.out.println("Current Cost: "+newcost);
       System.out.println("Also Initial Cost: "+curcost);

   }

   public void testChangeLogger(){
        InventoryManager inventoryManager = new InventoryManager();
       //TEST INGREDIENT TO ADD TO LIST TO BE ADDED TO FILE UPDATE
       IngredientItem NewItem = new IngredientItem();
       NewItem.setName("GROUND BEEF");
       NewItem.setType("Dry");
       NewItem.setCost(5.7);
       NewItem.setWeight(2);
       NewItem.setMeasurementUnit("lb");
       NewItem.setQuantityOnHand(0.5);
       System.out.println("\n\nAttempt to Add and Existing Ingredient");

       inventoryManager.addIngredient(NewItem);

       NewItem.setName("BEEF STEAK");
       System.out.println("\n\nAdding Beef Steak as an Ingredient Item");
       inventoryManager.addIngredient(NewItem);

       System.out.println("\n\nDemoing the Remove Ingredient Method");
       //TESTS THE REMOVAL AND UPDATE OF AN INGREDIENT
       IngredientItem demoRemove = new IngredientItem(ingredientDictionary.getIngredientItem("BUTTER"));
       inventoryManager.removeIngredient(demoRemove);

       System.out.println("\n\nDemoing the Update Ingredient Method");
       //TESTING FOR TAKING AN INGREDIENT TO MODIFY AND SEND TO UPDATE
       IngredientItem demo = new IngredientItem(ingredientDictionary.getIngredientItem("SALT"));
       demo.setMeasurementUnit("Changed Names");
       ingredientDictionary.updateIngredientInList(demo);
       System.out.println("Updates By Removing the Current Version of the Ingredient " +
               "and then Adding the Updated Version");

       inventoryManager.InventoryChangeLogger.beforeClosing();
   }


}
//test
//final test for the night before I give up

