package com.company;

import java.util.ArrayList;
import java.util.LinkedList;

public class IngredientFactory {
//TODO exceptions
    private ArrayList<IngredientItem> list;

    public IngredientFactory() {
        this.list = new ArrayList<>();
    }

    public ArrayList<IngredientItem> getList() {
        return list;
    }

    public void startFactory(ArrayList<ArrayList<String>> ingredientStrings){
        for(int i = 0; i<ingredientStrings.size(); i++){
            IngredientItem temp = new IngredientItem(ingredientStrings.get(i));
            this.list.add(temp);
        }
    }
}
