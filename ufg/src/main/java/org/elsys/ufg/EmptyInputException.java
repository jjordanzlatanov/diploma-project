package org.elsys.ufg;

public class EmptyInputException extends RuntimeException {
    private String page;

    public EmptyInputException(String page){
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public String getErrorMessage(){
        return "One or more fields were empty";
    }
}
