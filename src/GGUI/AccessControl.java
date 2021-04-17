package GGUI;

import javax.swing.*;

public class AccessControl {
private String actUN;
private String actPW;
private boolean loggedIn;
private static AccessControl AccessInstance = null;


public AccessControl(){
    actUN = "";
    actPW = "";
}


    /**
     * allows the end user to build a new user as well as the backend to do some checks (this was needed due to previous implementation)
     * @param username  incoming username to set a new users name
     * @param password  incoming password to set a new users password
     * @return  the user
     */
    public AccessControl buildUser(String username, String password){
        this.setActUN(username);
        this.setActPW(password);
   return this;
    }


    /**
     *  check to give the user access
     * @param inUN  text field from GUI for user input
     * @param inPW  text field from GUI for pw input
     * @return  bool value of yes or no to get into appplication
     */
    public boolean giveAccess(String inUN, String inPW) {

    AccessControl user1 = new AccessControl();
    AccessControl user2 = new AccessControl();
    AccessControl user3 = new AccessControl();

    user1.buildUser("jonathan", "123");
    user2.buildUser("nathan", "456");
    user3.buildUser("jay", "789");

    loggedIn = false;

    if(inUN.equals(user1.getActUN()) && inPW.equals(user1.getActPW())) {
        loggedIn = true;
    }
    else if(inUN.equals(user2.getActUN()) && inPW.equals(user2.getActPW())) {
        loggedIn = true;
    }
    else if(inUN.equals(user3.getActUN()) && inPW.equals(user3.getActPW())) {
        loggedIn = true;
    }

    return loggedIn;

}

    /**
     *
     * @return
     */
    public static AccessControl getAccessInstance(){
    if(AccessInstance == null)
    {
        AccessInstance = new AccessControl();
    }
    return AccessInstance;
}

    /**
     * getter for userName
     * @return userName
     */
    public String getActUN(){
    return this.actUN;
}

    /**
     * setter for username
     * @param username incoming username
     */
    public void setActUN(String username){ this.actUN = username; }

    /**
     * getter for password
     * @return password
     */
    public String getActPW(){ return this.actPW; }

    /**
     * setter for password
     * @param password incoming password
     */
    public void setActPW(String password){ this.actPW = password; }

//end of Access Control class
}
