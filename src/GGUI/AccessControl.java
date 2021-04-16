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

public AccessControl buildUser(String username, String password){
   this.setActUN(username);
   this.setActPW(password);
   return this;
    }


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

public static AccessControl getAccessInstance(){
    if(AccessInstance == null)
    {
        AccessInstance = new AccessControl();
    }
    return AccessInstance;
}


public String getActUN(){
    return this.actUN;
}
public void setActUN(String username){ this.actUN = username; }
public String getActPW(){ return this.actPW; }
public void setActPW(String password){ this.actPW = password; }

//end of Access Control class
}
