package org.elsys.ufg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.Duration;
import java.time.Instant;

@Controller
public class MainController{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailTokenRepository emailTokenRepository;

    @Autowired
    private JavaMailSender emailSender;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(){
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(@ModelAttribute("user") User user) {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin(@ModelAttribute("user") User user, @RequestParam(value = "login", required = false) String loginRedirect, @RequestParam(value = "register", required = false) String registerRedirect) {
        if(loginRedirect != null){
            return "redirect:/login";
        }

        if (registerRedirect != null) {
            return "redirect:/register";
        }

        user.validateLogin(userRepository);

        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister(@ModelAttribute("user") User user){
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String postRegister(@ModelAttribute("user") User user, @RequestParam(value = "register", required = false) String registerRedirect) throws MessagingException {
        if(registerRedirect != null){
            return "redirect:/register";
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
    public String getConfirmEmail(Model model, @PathVariable("token") String token){
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
