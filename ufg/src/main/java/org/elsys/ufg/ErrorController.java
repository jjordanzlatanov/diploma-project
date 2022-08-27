package org.elsys.ufg;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(PasswordRepeatPasswordMismatchException.class)
    public String passwordRepeatPasswordMismatchExceptionHandler(Model model, PasswordRepeatPasswordMismatchException exception){
        model.addAttribute("errorMessage", exception.getErrorMessage());
        model.addAttribute("user", new User());
        return exception.getPage();
    }

    @ExceptionHandler(EmptyInputException.class)
    public String emptyInputExceptionHandler(Model model, EmptyInputException exception){
        model.addAttribute("errorMessage", exception.getErrorMessage());
        model.addAttribute("user", new User());
        return exception.getPage();
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public String userDoesNotExistExceptionHandler(Model model, UserDoesNotExistException exception){
        model.addAttribute("errorMessage", exception.getErrorMessage());
        model.addAttribute("user", new User());
        return exception.getPage();
    }

    @ExceptionHandler(UsernameIsTakenException.class)
    public String usernameIsTakenExceptionHandler(Model model, UsernameIsTakenException exception){
        model.addAttribute("errorMessage", exception.getErrorMessage());
        model.addAttribute("user", new User());
        return exception.getPage();
    }

    @ExceptionHandler(EmailIsTakenException.class)
    public String emailIsTakenExceptionHandler(Model model, EmailIsTakenException exception){
        model.addAttribute("errorMessage", exception.getErrorMessage());
        model.addAttribute("user", new User());
        return exception.getPage();
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public String userNotActivatedException(Model model, UserNotActivatedException exception){
        model.addAttribute("errorMessage", exception.getErrorMessage());
        model.addAttribute("user", new User());
        return exception.getPage();
    }
}

