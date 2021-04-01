package com.company;

import GGUI.loginGUI;

import java.io.IOException;

public class Main {

    public static final String ADD = "ADD";
    public static final String DELETE = "DELETE";
    public static final String UPDATE = "UPDATE";

    public static void main(String[] args) throws IOException {
	// write your code here

       DemoSetup demoSetup = new DemoSetup();
      // DemoInventory demoinvent = new DemoInventory();

//TEST PLANS AND EXCEPTION HANDLING


       new loginGUI();


    }
}
