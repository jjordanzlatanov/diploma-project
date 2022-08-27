package org.elsys.ufg;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class DefaultErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        int statusCode = (int) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(statusCode == HttpStatus.NOT_FOUND.value()) {
            return "redirect:/home";
        }

        return "redirect:/home";
    }
}
