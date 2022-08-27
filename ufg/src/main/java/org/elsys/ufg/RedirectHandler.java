package org.elsys.ufg;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class RedirectHandler {
    private List<String> redirectLinks;

    public RedirectHandler(){
        redirectLinks = List.of("login", "register");
    }

    public String redirection(Map<String, String> params){
        for (String link : redirectLinks) {
            if(params.get(link) != null){
                return "redirect:/" + link;
            }
        }

        return null;
    }
}
