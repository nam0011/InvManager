package com.company;

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
    protected  int noLogins;

    public Account(){

    }
    public Account(ArrayList<String> list) throws ParseException {
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).toString()) {
                case "account":

                    i++;
                    setPriv(list.get(i));

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
                    Date date =new SimpleDateFormat("dd/MM/yyyy").parse(list.get(i));
                    setLastLogin(date);

                    break;
                case "last":
                    i++;
                    lastName = list.get(i);

                    break;


            }
        }

    }

    public boolean isAdminPriv() {
        return adminPriv;
    }

    public void setPriv(String accType){
        if(accType.equals("admin")){
            adminPriv = true;
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
        userNameIn = userName.toLowerCase();
        if(userNameIn.equals(userName) && hashPasswordIn == hashPassword){
            login = true;


        }
        return login;
    }
}