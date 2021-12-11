package org.elsys.ufg;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController{
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("user") User user){
        if(!user.validateLogin()) {
            return "login";
        }

        return null;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model, @ModelAttribute("user") User user){
        switch (user.validateRegister()){
            case "redirect" -> {
                return "register";
            }

            case "emptyField" -> throw new EmptyInputException("register");
        }

        if(!user.getPassword().equals(user.getRepeatedPassword())){
            throw new PasswordRepeatPasswordMismatchException();
        }

        System.out.println(user.getEmail());

        return null;
    }
}
