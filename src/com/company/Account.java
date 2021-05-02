package com.company;

import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Account {
    protected String userName;
    protected int hashPassword;
    protected Date lastLogin;
    protected String firstName;
    protected String lastName;
    protected Date birthday;
    protected boolean adminPriv;
    protected  boolean manPriv;
    protected  int noLogins;
    protected  String account;

    public Account(){
        adminPriv = false;

    }
    public Account(String UNin, int PWin, String type){
        userName = UNin;
        hashPassword = PWin;
        account = type;
        noLogins = 0;

        birthday = new Date();
        lastLogin = new Date();

    }
    public Account(ArrayList<String> list) throws ParseException {
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).toString()) {
                case "account":

                    i++;
                    setPriv(list.get(i));
                    account = list.get(i);

                    break;
                case "USERNAME":
                    i++;
                    setUserName(list.get(i));

                    break;
                case "PASSWORD":
                    i++;
                    int inHashPw = Integer.parseInt(list.get(i));
                    setHashPassword(inHashPw);

                    break;
                case "lastLogin":
                    i++;
                    if(list.get(i) != "") {
                        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(list.get(i));
                        setLastLogin(date);
                    }

                    break;
                case "last":
                    i++;
                    lastName = list.get(i);

                    break;
                case "first":
                    i++;
                    firstName = list.get(i);

                    break;
                case "birthday":
                    i++;
                    if(list.get(i) != null) {
                        birthday = new SimpleDateFormat("dd/MM/yyyy").parse(list.get(i));
                    }
                    break;
                case "noLogins":
                    i++;
                    noLogins = Integer.parseInt(list.get(i));



            }
        }

    }
    public String toJSONString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strLastLogin = "";
        String strBirthday = "";
        if(lastLogin != null && birthday != null) {
            strLastLogin = dateFormat.format(lastLogin);
            strBirthday = dateFormat.format(birthday);
        }



        return "{\"" +
                "account\" : \"" + account  + "\", \"" +
                "USERNAME\" : \"" + userName + "\", \"" +
                "PASSWORD\" : \"" + hashPassword + "\", \"" +
                "lastLogin\" : \"" + strLastLogin + "\", \"" +
                "birthday\" : \"" + strBirthday + "\", \"" +
                "first\" : \"" + firstName + "\", \"" +
                "last\" : \"" + lastName + "\" ,\"" +
                "noLogins\" : \"" + noLogins + "\"" +
                "}";

    }

    public boolean isAdminPriv() {
        return adminPriv;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public int getNoLogins() {
        return noLogins;
    }

    public void login() {
        this.noLogins = noLogins+1;
    }

    public boolean isManPriv() {
        return manPriv;
    }

    public void setPriv(String accType){
        if(accType.equals("admin")){
            adminPriv = true;
            manPriv = true;
        }
        else if(accType.equals("manager")){
            adminPriv = false;
            manPriv= true;
        }
        else{
            adminPriv = false;
            manPriv = false;
        }

    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(int hashPassword) {
        this.hashPassword = hashPassword;
    }

    public boolean accessGranted(String userNameIn, int hashPasswordIn)
    {
        boolean login = false;
        userNameIn = userNameIn.toLowerCase();
        if(userNameIn.equals(userName) && hashPasswordIn == hashPassword){
            login = true;


        }
        return login;
    }
}
