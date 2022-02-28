package org.elsys.ufg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;

@Controller
public class MainController{
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailTokenRepository emailTokenRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String getLogin(@ModelAttribute("user") User user) {
        return "login";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public String postLogin(@ModelAttribute("user") User user, @RequestParam(value = "register", required = false) String registerRedirect, @RequestParam(value = "login", required = false) String loginRedirect) {
        if (registerRedirect != null) {
            return "redirect:/register";
        }

        if(loginRedirect != null){
            return "redirect:/login";
        }

        user.validateLogin(userRepository);

        return "login";
    }

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public String getRegister(@ModelAttribute("user") User user){
        return "register";
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public String postRegister(@ModelAttribute("user") User user){
        user.validateRegister(userRepository);

        userRepository.save(user);

        EmailToken emailToken = new EmailToken(emailTokenRepository, user.getId());

        emailTokenRepository.save(emailToken);

        return "sent_confirmation_mail";
    }

    @RequestMapping(value = "/confirmemail/{token}", method = RequestMethod.GET)
    public String getConfirmEmail(@PathVariable("token") String token){
        if(emailTokenRepository.existsByToken(token) != null){
            Instant start = Instant.parse(emailTokenRepository.findTimestampByToken(token));
            Instant end = Instant.now();

            if(Duration.between(start, end).toHours() <= 24){
                userRepository.activateById(emailTokenRepository.findUserIdByToken(token));
                emailTokenRepository.deleteByToken(token);
            }
        }

        return "email_confirmation_success";
    }
}
