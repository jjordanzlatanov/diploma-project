package org.elsys.ufg;

public class PasswordRepeatPasswordMismatchException extends RuntimeException {
    public String getErrorMessage() {
        return "Password did not match repeated password";
    }

    public String getPage(){
        return "register";
    }
}
