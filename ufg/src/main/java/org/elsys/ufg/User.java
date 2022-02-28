package org.elsys.ufg;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class User {
    @Id
    private Integer id;
    private String username;
    private String password;
    private String email;
    @Transient
    private String repeatedPassword;
    @Transient
    private boolean activated;

    public User(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String validateLogin(UserRepository userRepository){
        if(username == null || password == null){
            return "show";
        }

        if(username.equals("") || password.equals("")){
            return "emptyField";
        }

        if(userRepository.existsByUsernameAndPassword(username, password) == null){
            return "does not exist";
        }

        if(!activated){
            return "not activated";
        }

        return "valid";
    }

    public String validateRegister(UserRepository userRepository){
        if(username == null || password == null || email == null || repeatedPassword == null){
            return "show";
        }

        if(username.equals("") || password.equals("") || email.equals("") || repeatedPassword.equals("")){
            return "emptyField";
        }

        if(userRepository.existsByUsername(username) != null){
            return "username is already taken";
        }

        if(userRepository.existsByEmail(email) != null){
            return "email is already taken";
        }

        return "valid";
    }
}
