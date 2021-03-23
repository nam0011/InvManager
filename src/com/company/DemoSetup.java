package com.company;

import java.io.IOException;

public class DemoSetup {

    FileManager FileManager = new FileManager("DataSource/ingredients.json");
    IngredientFactory testFactory = new IngredientFactory();
    IngredientDictionary ingredientDictionary;

    /*
    Currently Demos without using the Inventory Manager. This is to showcase that the foundation code works properly
     */
    public DemoSetup(){
        //test
        this.createDictionary();
        ingredientDictionary.printDictionary();
        this.printDemo();
        ingredientDictionary.printDictionary();
        System.out.println("\n\n Check out the New JSON File in the Data Source Folder");
        this.createFileWriter();
    }

    public void createDictionary(){
        try {
            FileManager.generateStringArrayList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileManager.createObjectArray();

        //TODO - I getting the instance of the singleton of IngredientDictionary.
        IngredientDictionary ID = IngredientDictionary.getIngredientDictionary();

        testFactory.startFactory(FileManager.getObjectArrayList());
        ingredientDictionary = new IngredientDictionary(testFactory.getList());
        //TODO Jonathan
        ID.setIngredientItemArrayList(testFactory.getList());

        try {
            FileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createFileWriter(){
        FileManager = new FileManager();
        FileManager.setFileName("DataSource/DemoIngredientsPrintOut.json");
        FileManager.setStringArrayList(ingredientDictionary.convertToStringArrayList());

        try {
            FileManager.generateJSONFile(FileType.INGREDIENTS);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void printDemo(){
        //TEST INGREDIENT TO ADD TO LIST TO BE ADDED TO FILE UPDATE
        IngredientItem NewItem = new IngredientItem();
        NewItem.setName("GROUND BEEF");
        NewItem.setType("Dry");
        NewItem.setCost(5.7);
        NewItem.setWeight(2);
        NewItem.setMeasurementUnit("lb");
        NewItem.setQuantityOnHand(0.5);
        System.out.println("\n\nAttempt to Add and Existing Ingredient");

        ingredientDictionary.addIngredientToList(NewItem);

        NewItem.setName("BEEF STEAK");
        System.out.println("\n\nAdding Beef Steak as an Ingredient Item");
        ingredientDictionary.addIngredientToList(NewItem);

        System.out.println("\n\nDemoing the Remove Ingredient Method");
        //TESTS THE REMOVAL AND UPDATE OF AN INGREDIENT
        IngredientItem demoRemove = new IngredientItem(ingredientDictionary.getIngredientItem("BUTTER"));
        ingredientDictionary.removeIngredientFromList(demoRemove);

        System.out.println("\n\nDemoing the Update Ingredient Method");
        //TESTING FOR TAKING AN INGREDIENT TO MODIFY AND SEND TO UPDATE
        IngredientItem demo = new IngredientItem(ingredientDictionary.getIngredientItem("SALT"));
        demo.setMeasurementUnit("Changed Names");
        ingredientDictionary.updateIngredientInList(demo);
        System.out.println("Updates By Removing the Current Version of the Ingredient and then Adding the Updated Version");
    }
}
