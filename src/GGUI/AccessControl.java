package GGUI;

public class AccessControl {
private String actUN;
private String actPW = "password123";
private boolean loggedIn;
private static AccessControl AccessInstance = null;
public AccessControl(){
    actUN = "jlew92";
    actPW = "password123";
}
public boolean giveAccess(String inUN, String inPW) {
    loggedIn = false;
    if(inUN.equals(actUN) && inPW.equals(actPW)) {
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

public String getActPW(){
    return this.actPW;
}

//end of Access Control class
}
