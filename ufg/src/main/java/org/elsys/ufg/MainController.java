package org.elsys.ufg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController{
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public String login(@ModelAttribute("user") User user, @RequestParam(value = "register", required = false) String registerRedirect) {
        if (registerRedirect != null && registerRedirect.equals("REGISTER")) {
            return "redirect:/register";
        }

        switch (user.validateLogin(userRepository)) {
            case "show" -> {
                return "login";
            }

            case "emptyField" -> throw new EmptyInputException("login");

            case "does not exist" -> throw new UserDoesNotExistException();

            case "not activated" -> throw new UserNotActivatedException();
        }

        return null;
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
    public String register(@ModelAttribute("user") User user){
        switch (user.validateRegister(userRepository)){
            case "show" -> {
                return "register";
            }

            case "emptyField" -> throw new EmptyInputException("register");

            case "username is already taken" -> throw new UsernameIsTakenException();

            case "email is already taken" -> throw new EmailIsTakenException();
        }

        if(!user.getPassword().equals(user.getRepeatedPassword())){
            throw new PasswordRepeatPasswordMismatchException();
        }

        userRepository.save(user);


        return "sent_confirmation_mail";
    }
}
