package com.company;

import GGUI.loginGUI;

import java.io.IOException;

public class Main {

    public static final String ADD = "ADD";
    public static final String DELETE = "DELETE";
    public static final String UPDATE = "UPDATE";

    public static void main(String[] args)  {
	// write your code here

       DemoSetup demoSetup = new DemoSetup();
       DemoInventory demoinvent = new DemoInventory();
       demoinvent.testInventory();

       demoinvent.testChangeLogger();


       new loginGUI();










    }
}
