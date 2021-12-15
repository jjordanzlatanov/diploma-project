package org.elsys.ufg;

public class EmailIsTakenException extends RuntimeException{
    public String getErrorMessage(){
        return "Email is already taken";
    }

    public String getPage(){
        return "register";
    }
}
