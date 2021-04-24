package com.company;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class AccessControl {
private boolean loggedIn;
private static AccessControl AccessInstance = null;
private ArrayList<Account> list;
private DefaultTableModel accountDTM;
private FileManager file;


private AccessControl() throws IOException, ParseException {
list = new ArrayList<>();
file = new FileManager("DataSource/Accounts.json");
    file.generateStringArrayList();
    file.createObjectArray();
    ArrayList<ArrayList<String>> object = file.getObjectArrayList();
    for(int i = 0; i < object.size(); i++){
        list.add(new Account(object.get(i)));

    }


}
public void generateDTM(){
    accountDTM = new DefaultTableModel();
    accountDTM.addColumn("Username");
    accountDTM.addColumn("Last Login");
    accountDTM.addColumn("Birthday");
    accountDTM.addColumn("Account type");
}


/**
     * allows the end user to build a new user as well as the backend to do some checks (this was needed due to previous implementation)
     * @param username  incoming username to set a new users name
     * @param password  incoming password to set a new users password
     * @return  the user
     */



    /**
     *  check to give the user access
     * @param inUN  text field from GUI for user input
     * @param inPW  text field from GUI for pw input
     * @return  bool value of yes or no to get into appplication
     */
    public Account giveAccess(String inUN, int inPW) {
        Account output = null;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).accessGranted(inUN, inPW)){
                output = list.get(i);
                break;

            }

        }



    return output;

}

    /**
     *Singleton of AccessControl - this is not currently implemented
     * @return  AccessInstance
     */
    public static AccessControl getAccessInstance() throws IOException, ParseException {
    if(AccessInstance == null)
    {
        AccessInstance = new AccessControl();
    }
    return AccessInstance;
}

    public void addAccount(Account newAccount ) throws IOException {
        file.appendToFile(newAccount.toJSONString());
        this.UpdateJSONFile();
    }

    public void UpdateJSONFile() throws IOException {


        try {
            file.generateJSONFile(FileType.ACCOUNT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




//end of Access Control class
}
