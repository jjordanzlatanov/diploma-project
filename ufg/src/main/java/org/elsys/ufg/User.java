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

    public String validateLogin(){
        if(username == null || password == null){
            return "redirect";
        }

        if(username.equals("") || password.equals("")){
            return "emptyField";
        }

        return "valid";
    }

    public String validateRegister(){
        if(username == null || password == null || email == null || repeatedPassword == null){
            return "show";
        }

        if(username.equals("") || password.equals("") || email.equals("") || repeatedPassword.equals("")){
            return "emptyField";
        }

        return "valid";
    }
}
