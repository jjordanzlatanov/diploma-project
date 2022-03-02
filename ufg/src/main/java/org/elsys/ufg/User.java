package org.elsys.ufg;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

@Table("users")
public class User implements Serializable {
    @Id
    private Integer id;
    private String username;
    private String password;
    private String email;
    private Boolean activated;
    @Transient
    private String repeatedPassword;

    public User(){
        this.activated = false;
    }

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

    public void validateLogin(UserRepository userRepository){
        if(username == null || password == null){
            return;
        }

        if(username.equals("") || password.equals("")){
            throw new EmptyInputException("login");
        }

        if(userRepository.existsByUsernameAndPassword(username, password) == null){
            throw new UserDoesNotExistException();
        }

        if(!userRepository.getActivatedStateByUsername(username)){
            throw new UserNotActivatedException();
        }
    }

    public void validateRegister(UserRepository userRepository){
        if(username == null || password == null || email == null || repeatedPassword == null){
            return;
        }

        if(username.equals("") || password.equals("") || email.equals("") || repeatedPassword.equals("")){
            throw new EmptyInputException("register");
        }

        if(userRepository.existsByUsername(username) != null){
            throw new UsernameIsTakenException();
        }

        if(userRepository.existsByEmail(email) != null){
            throw new EmailIsTakenException();
        }
    }
}
