package org.elsys.ufg;

public class UsernameIsTakenException extends RuntimeException{
    public String getErrorMessage(){
        return "Username is already taken";
    }

    public String getPage(){
        return "register";
    }
}
