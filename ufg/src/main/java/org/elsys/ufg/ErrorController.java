package org.elsys.ufg;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(PasswordRepeatPasswordMismatchException.class)
    public String passwordRepeatPasswordMismatchExceptionHandler(Model model){
        model.addAttribute("errorMessage", "Password did not match repeated password");
        model.addAttribute("user", new User());
        return "register";
    }
}

