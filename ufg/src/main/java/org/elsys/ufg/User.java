package org.elsys.ufg;

public class User {
    private String username;
    private String password;
    private String email;
    private String repeatedPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public boolean validateLogin(){
        if(username != null && password != null){
            return true;
        }

        return false;
    }

    public boolean validateRegister(){
        if(username != null && password != null && email != null){
            return true;
        }

        return false;
    }
}
