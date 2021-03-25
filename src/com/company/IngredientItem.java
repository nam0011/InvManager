package com.company;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Cody Bracewell
 * CS321-03
 * Ingredient Item Object Class
 */
public class IngredientItem implements Cloneable{
    //TODO weight vs quantityOnHand may be lead to some confusion.
    //Private Variables
    private String name;   //What the Ingredient Item is called
    private String type;   //What type, Dry or wet
    private String measurementUnit;    //The measurement Unit Used: oz, ml, grams
    private double cost;  //Cost per oz or ml
    private double weight;    //Weights is how per cost, i.e. ground beef cost "$5.50 per 1lb"
    private double quantityOnHand;    //Current stock of Ingredient.
    private Date lastUsedDate;


    //Comparator Methods to allow for the Ingredient Item to be sorted by every Attribute if neccessary

    /**
     * Method to Compare by Name
     * @param item  Item to be compared to
     * @return
     */
    public int compareName(IngredientItem item){
        if(this.name.compareTo(item.getName()) > 0){
            return 1;
        }else if(this.name.compareTo(item.getName()) < 0){
            return -1;
        }else return 0;
    }

    /**
     * Method to Compare by Type
     * @param item  Item to be compared to
     * @return
     */
    public int compareType(IngredientItem item){
        if(this.type.compareTo(item.getType()) > 0){
            return 1;
        }else if(this.type.compareTo(item.getType()) < 0){
            return -1;
        }else return 0;
    }

    /**
     * Method to Compare by Measurement Unit
     * @param item  Item to be compared to
     * @return
     */
    public int compareMeasurementUnit(IngredientItem item){
        if(this.measurementUnit.compareTo(item.getMeasurementUnit()) > 0){
            return 1;
        }else if(this.measurementUnit.compareTo(item.getMeasurementUnit()) < 0){
            return -1;
        }else return 0;
    }

    /**
     * Method to Compare by Cost
     * @param item  Item to be compared to
     * @return
     */
    public int compareCost(IngredientItem item){
        if(this.cost > item.getCost()){
            return 1;
        }else if(this.cost < item.getCost()){
            return -1;
        }else return 0;
    }

    /**
     * Method to Compare by Weight
     * @param item  Item to be compared to
     * @return
     */
    public int compareWeight(IngredientItem item){
        if(this.weight > item.getWeight()){
            return 1;
        }else if(this.weight < item.getWeight()){
            return -1;
        }else return 0;
    }

    /**
     * Method to Compare by Quantity on Hand
     * @param item  Item to be compared to
     * @return
     */
    public int compareQOH(IngredientItem item){
        if(this.quantityOnHand > item.getQuantityOnHand()){
            return 1;
        }else if(this.quantityOnHand < item.getQuantityOnHand()){
            return -1;
        }else return 0;
    }

    //TODO add compare method for Dates

    /**
     * Method to convert ingredient to string. This way it is uniform for reading and writing to the file.
     * @return
     */
    public String toJSONString() {
        return "{\"" +
                "name\" : \"" + name + "\", \"" +
                "type\" : \"" + type + "\", \"" +
                "measurementUnit\" : \"" + measurementUnit + "\", \"" +
                "cost\" : \"" + cost + "\", \"" +
                "weight\" : \"" + weight + "\", \"" +
                "quantityOnHand\" : \"" + quantityOnHand + "\", \"" +
                "lastUsedDate\" : \"" + lastUsedDate + "\"}";
    }
    /**
     * Method to fill the Current Table Setup
     * TODO check to see if the data for the tables can be populated differently
     * @return
     */
    public String[] toQOHString() {
        return new String[]{this.name , this.weight + " " + this.measurementUnit,this.currencyFormat()};
    }

    private String currencyFormat()
    {
        String output= new String();

        String format = String.valueOf(cost);
        String arr[] = format.split("\\.");
        String dec = arr[1];
        String whole = arr[0];
        if(dec.length() == 1)
        {

            output = "$" + format + "0";
        }
        else if(dec.length()== 2)
        {
            output = "$" + format;
        }
        else {
            int thirdDec = Character.getNumericValue(dec.charAt(2));
            if(thirdDec < 5)
            {
                output = "$"+whole + "." + dec.substring(0,2);
            }
            else
            {
                int i = Integer.parseInt(dec.substring(0,2)) + 1;

                output = "$" + whole + "." + String.valueOf(i);

            }
        }


        return output;
    }


    /**
     * Method to Clone the Ingredient Item
     * @return
     * @throws CloneNotSupportedException
     */
    public IngredientItem clone() throws CloneNotSupportedException {
        return (IngredientItem) super.clone();

    }


//****Constructors, Getters, and Setters Below************************/

    /**
     * Constructor with no parameters
     */
    public IngredientItem(){
        this.name = "";   //What the Ingredient Item is called
        this.type = "";   //What type, Dry or wet
        this.measurementUnit = "";    //The measurement Unit Used: oz, ml, grams
        this.cost = 0.0;  //Cost per oz or ml
        this.weight = 0.0;    //Weights is how per cost, i.e. ground beef cost "$5.50 per 1lb"
        this.quantityOnHand = 0.0;    //Current stock of Ingredient.
        this.lastUsedDate = null;
    }

    /**
     * Constructo to make a copy of an Ingredient Item
     * @param item  The Ingredient Item to be Copied
     */
    public IngredientItem(IngredientItem item) {
        this.name = item.getName();
        this.type = item.getType();
        this.cost = item.getCost();
        this.weight = item.getWeight();
        this.measurementUnit = item.getMeasurementUnit();
        this.quantityOnHand = item.getQuantityOnHand();
        this.lastUsedDate = item.getLastUsedDate();   //TODO Setup Date System Until then All Dates will be null!
    }

    /**
     * Method to take an Array List of String to convert to Ingredient Item
     * @param list
     */
    public IngredientItem(ArrayList<String> list){
        for(int i = 0; i < list.size(); i++){
            switch (list.get(i).toString()){
                case "name":
                    i++;
                    this.name = list.get(i);
                    break;
                case "type":
                    i++;
                    this.type = list.get(i);
                    break;
                case "measurementUnit":
                    i++;
                    this.measurementUnit = list.get(i);
                    break;
                case "cost":
                    i++;
                    this.cost = Double.parseDouble(list.get(i));
                    break;
                case "weight":
                    i++;
                    this.weight = Double.parseDouble(list.get(i));
                    break;
                case "quantityOnHand":
                    i++;
                    this.quantityOnHand = Double.parseDouble(list.get(i));
                    break;
                case "lastUsedDate":
                    i++;
                    this.lastUsedDate = null;   //TODO FIX DATE FROM STRING ISSUE
                    break;
            }
        }
    }

    public IngredientItem(String name, String type, String measurementUnit,
                          double cost, double weight, double quantityOnHand, Date lastUsedDate) {
        this.name = name;
        this.type = type;
        this.measurementUnit = measurementUnit;
        this.cost = cost;
        this.weight = weight;
        this.quantityOnHand = quantityOnHand;
        this.lastUsedDate = lastUsedDate;
    }

    /**
     * Method to Get the Name of the Ingredient Item
     * @return the Ingredient Item's Name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to Set the Name of the Ingredient Item
     * @param name is the Name being passed in
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to Get the type of the Ingredient Item
     * @return the Ingredient Item's type
     */
    public String getType() {
        return type;
    }

    /**
     * Method to Set the type of the Ingredient Item
     * @param type is the type being passed in
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Method to Get the Measurement Unit of the Ingredient Item
     * @return the Ingredient Item's Measurement Unit
     */
    public String getMeasurementUnit() {
        return measurementUnit;
    }

    /**
     * Method to Set the Measurement Unit of the Ingredient Item
     * @param measurementUnit is the Measurement Unit being passed in
     */
    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    /**
     * Method to Get the Cost of the Ingredient Item
     * @return the Ingredient Item's Cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Method to Set the Cost of the Ingredient Item
     * @param cost is the Cost being passed in
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Method to Get the Weight of the Ingredient Item
     * @return the Ingredient Item's Weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Method to Set the Weight of the Ingredient Item
     * @param weight is the Weight being passed in
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Method to Get the Quantity on Hand of the Ingredient Item
     * @return the Ingredient Item's Quantity on Hand Value
     */
    public double getQuantityOnHand() {
        return quantityOnHand;
    }

    /**
     * Method to Set the Quantity on Hand of the Ingredient Item
     * @param quantityOnHand is the Quantity on Hand being passed in
     */
    public void setQuantityOnHand(double quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    /**
     * Method to get the Last Date the Recipe was last used
     * @return the last used date variable
     */
    public Date getLastUsedDate() {
        return lastUsedDate;
    }

    /**
     * Method to set the last used date variable
     * @param lastUsedDate the date passed in of when the recipe was last used
     */
    public void setLastUsedDate(Date lastUsedDate) {
        this.lastUsedDate = lastUsedDate;
    }
}   //End of IngredientItem