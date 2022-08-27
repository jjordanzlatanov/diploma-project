package org.elsys.ufg;

public class UserNotActivatedException extends RuntimeException{
    public String getErrorMessage(){
        return "User is not activated, please check your mail";
    }

    public String getPage(){
        return "login";
    }
}
