package org.elsys.ufg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Controller
public class MainController{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailTokenRepository emailTokenRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private RedirectHandler redirectHandler;

    @Autowired
    private CookieService cookieService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getHome(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = cookieService.getCookie("username", request);
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(@ModelAttribute("user") User user) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin(@ModelAttribute("user") User user, @RequestParam Map<String, String> params, HttpServletResponse response) {
        String redirect = redirectHandler.redirection(params);

        if(redirect != null){
            return redirect;
        }

        user.validateLogin(userRepository);

        cookieService.create("username", user.getUsername()).setMaxAge(Integer.MAX_VALUE).setPath("/").build(response);
        return "redirect:/home";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister(@ModelAttribute("user") User user, HttpServletRequest request, HttpServletResponse response){
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String postRegister(@ModelAttribute("user") User user, @RequestParam Map<String, String> params) throws MessagingException {
        String redirect = redirectHandler.redirection(params);

        if(redirect != null){
            return redirect;
        }

        user.validateRegister(userRepository);

        userRepository.save(user);

        EmailToken emailToken = new EmailToken(emailTokenRepository, user.getId());

        emailTokenRepository.save(emailToken);

        MimeMessage mailMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
        messageHelper.setFrom("officialufggame@gmail.com");
        messageHelper.setTo(user.getEmail());
        messageHelper.setText("Press Link to Confirm Email: <a href=\"http://localhost:8080/confirmemail/" + emailToken.getToken() + "\">" + "Link</a>", true);
        emailSender.send(mailMessage);

        return "sent_confirmation_mail";
    }

    @RequestMapping(value = "/confirmemail/{token}", method = RequestMethod.GET)
    public String getConfirmEmail(@PathVariable("token") String token){
        if(emailTokenRepository.existsByToken(token) == null){
            return "email_confirmation_failure";
        }

        Instant start = Instant.parse(emailTokenRepository.findTimestampByToken(token));
        Instant end = Instant.now();

        Integer user_id = emailTokenRepository.findUserIdByToken(token);
        emailTokenRepository.deleteByToken(token);

        if(Duration.between(start, end).toHours() >= 24){
            userRepository.deleteById(user_id);
            return "email_confirmation_failure";
        }

        userRepository.activateById(user_id);

        return "email_confirmation_success";
    }
}
