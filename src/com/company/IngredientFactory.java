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


}
