package com.company;

import java.util.ArrayList;
import java.util.Date;


public class IngredientItem implements Cloneable, Comparable<IngredientItem>{
    //TODO weight vs quantityOnHand may be lead to some confusion.
    //Private Variables
    private String name;   //What the Ingredient Item is called
    private String type;   //What type, Dry or wet
    private String measurementUnit;    //The measurement Unit Used: oz, ml, grams
    private double cost;  //Cost per oz or ml
    private double weight;    //Weights is how per cost, i.e. ground beef cost "$5.50 per 1lb"
    private double quantityOnHand;    //Current stock of Ingredient.
    private double quantDiff;
    private double quantChangeRatio;
    private double priceDiff;
    private double priceChangeRatio;
    private double OGPrice;
    private double OGQuant;
    private String typeOfChange;
    private Date lastUsedDate;



    private int index;

    /**
     * Constructor with no parameters
     */
    public IngredientItem(){
        this.typeOfChange = null;
        this.name = "";   //What the Ingredient Item is called
        this.type = "";   //What type, Dry or wet
        this.measurementUnit = "";    //The measurement Unit Used: oz, ml, grams
        this.cost = 0.0;  //Cost per oz or ml
        this.weight = 0.0;    //Weights is how per cost, i.e. ground beef cost "$5.50 per 1lb"
        this.quantityOnHand = 0.0;    //Current stock of Ingredient.
        this.quantDiff = 0.0;
        this.quantChangeRatio = 0.0;
        this.priceDiff = 0.0;
        this.priceChangeRatio = 0.0;
        this.lastUsedDate = null;
    }

    /**
     * Constructor to make a copy of an Ingredient Item
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
        Double format = Double.parseDouble(String.format("%.3f",weight*cost));
        if (type == ""){
            type = "DRY";
        }
        return "{\"" +
                "name\" : \"" + name + "\", \"" +
                "type\" : \"" + type + "\", \"" +
                "measurementUnit\" : \"" + measurementUnit + "\", \"" +
                "cost\" : \"" + cost + "\", \"" +
                "weight\" : \"" + weight + "\", \"" +
                "onHand\" : \"" + format + "\"}";
    }
    /**
     * Method to fill the Current Table Setup
     * TODO check to see if the data for the tables can be populated differently
     * @return
     */
    public String[] toQOHString() {
        return new String[]{this.name , String.format("%.1f",this.weight) + " " + this.measurementUnit,String.format("$%1$,.2f",this.getCost())};
    }




    /**
     * Method to Clone the Ingredient Item
     * @return
     * @throws CloneNotSupportedException
     */
    public IngredientItem clone() throws CloneNotSupportedException {
        return (IngredientItem) super.clone();

    }


//****Getters, and Setters Below************************/



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
                    this.setOGPrice(this.cost);
                    break;
                case "weight":
                    i++;
                    this.weight = Double.parseDouble(list.get(i));
                    this.setOGQuant(this.weight);
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
     * Method to set the ratio change from previous amount to new amount of single item inventory
     * @param changeRatioQuantity will always default to null and will only be updated by the update item function call
     */
    public void setQuantChangeRatio(double changeRatioQuantity) {
        this.quantChangeRatio = changeRatioQuantity;
    }

    /**
     * Method to set the actual weight difference between what was used and bought, and what was previously in the inventory
     * @param diffQuant - set by the update function call
     */
    public void setQuantDiff(double diffQuant) {
        this.quantDiff = diffQuant;
    }

    /**
     * Method to set the price difference between a newly purchased amount and the current average inventory price
     * @param diffPrice will be set in the update function call
     */
    public void setPriceDiff(double diffPrice) {
        this.priceDiff = diffPrice;
    }

    /**
     * Method to se tht price change ratio found by averaging prices of purchased, used, and old inv
     * @param changeRatioPrice will be set in update function call
     */
    public void setPriceChangeRatio(double changeRatioPrice) {
        this.priceChangeRatio = changeRatioPrice;
    }

    /**
     * getter for price change ratio
     * @return ratio of price price
     */
    public double getPriceChangeRatio() {
        return priceChangeRatio;
    }

    /**
     * getter for price difference
     * @return difference between old and new price
     */
    public double getPriceDiff() {
        return priceDiff ;
    }

    /**
     * getter for quantity difference
     * @return difference between current inventory and updated inventory quanities
     */
    public double getQuantDiff() {
        return quantDiff;
    }

    /**
     * getter for quantity change ratio
     * @return ratio between used, purchased, and prev inventory amount
     */
    public double getQuantChangeRatio() {
        return quantChangeRatio;
    }

    /**
     * setter for the original price for display in reports panel
     * @param ogPrice   set by update method
     */
    public void setOGPrice(double ogPrice) {
        this.OGPrice = ogPrice;
    }

    /**
     * getter to retrieve original pricing of item if item was updated
     * @return original item price
     */
    public double getOGPrice() {
        return OGPrice;
    }

    /**
     * setter for the original quantity in inventory of an item
     * @param ogQuant   set by update method
     */
    public void setOGQuant(double ogQuant){
        this.OGQuant = ogQuant;
    }

    /**
     * getter for the original item quantity
     * @return  original inventory amount of item
     */
    public double getOGQuant() {
        return OGQuant;
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


    public int compareTo(IngredientItem otherItem){

        if (this.getName().compareTo(otherItem.getName())==1) {
            return 1;
        }
        else if (this.getName().compareTo(otherItem.getName())==-1) {
            return -1;
        }
        else if (this.getName().compareTo(otherItem.getName())==0) {
            return 0;
        }
    return this.getName().compareTo(otherItem.getName());

    }

    /**
     * Returns the index where item is found acting as a bookmark
     * @return
     */
    public int getIndex() {
        return index;
    }

    /**
     *  This function only works if during a guaranteed stream with the data base  is not changed.
     *
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     *
     * @param amtUsed
     */
    public void useItem(Double amtUsed){

        this.setOGQuant(weight);
        this.setOGPrice(cost);

        this.setCost(cost - (OGPrice/OGQuant) * amtUsed);
        this.setWeight(this.getOGQuant() - amtUsed); //decrement the weight of the item in the list



    }

    /**
     *
     * @return
     */
    public void purchaseItem(Double purchAmt, Double newPrice)
    {
        OGQuant = weight;
        OGPrice = cost;


        cost = cost + newPrice;

        weight = weight + purchAmt;
    }


    public String getTypeOfChange() {
        return typeOfChange;
    }

    public void setTypeOfChange(String typeOfChange) {
        this.typeOfChange = typeOfChange;
    }
}   //End of IngredientItem