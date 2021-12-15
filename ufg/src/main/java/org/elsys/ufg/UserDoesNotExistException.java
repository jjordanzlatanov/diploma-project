package org.elsys.ufg;

public class UserDoesNotExistException extends RuntimeException{
    public String getErrorMessage(){
        return "User does not exist";
    }

    public String getPage(){
        return "login";
    }
}
