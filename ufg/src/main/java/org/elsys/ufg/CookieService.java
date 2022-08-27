package org.elsys.ufg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CookieService {
    private Cookie cookie;
    private Map<String, Cookie> cookies;

    @Autowired
    private UserRepository userRepository;

    public CookieService(){}

    public CookieService create(String name, String value){
        cookie = new Cookie(name, value);
        return this;
    }

    public CookieService setMaxAge(Integer age){
        cookie.setMaxAge(age);
        return this;
    }

    public CookieService setPath(String path){
        cookie.setPath(path);
        return this;
    }

    public void build(HttpServletResponse response){
        response.addCookie(cookie);
    }

    public void updateCookies(HttpServletRequest request){
        if(request.getCookies() == null){
            cookies = new HashMap<>();
            return;
        }

        cookies = Stream.of(request.getCookies()).collect(Collectors.toMap(Cookie::getName, Cookie -> Cookie));
    }

    public Cookie getCookie(String name, HttpServletRequest request){
        updateCookies(request);

        return cookies.get(name);
    }

    public void deleteCookie(String name, HttpServletRequest request, HttpServletResponse response){
        updateCookies(request);

        if(cookies.isEmpty()){
            return;
        }

        cookie = cookies.get(name);
        cookie.setValue("");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

    public void clearCookies(HttpServletRequest request, HttpServletResponse response){
        updateCookies(request);

        for(Map.Entry<String, Cookie> entry : cookies.entrySet()){
            cookie = entry.getValue();

            cookie.setValue("");
            cookie.setMaxAge(0);

            response.addCookie(cookie);
        }
    }

    public Boolean isLogged(HttpServletRequest request){
        updateCookies(request);

        return !cookies.isEmpty() && userRepository.existsByUsernameAndPassword(cookies.get("username").getValue(), cookies.get("password").getValue()) != null;
    }

    public void login(User user, HttpServletResponse response){
        create("username", user.getUsername()).setMaxAge(Integer.MAX_VALUE).setPath("/").build(response);
        create("password", user.getPassword()).setMaxAge(Integer.MAX_VALUE).setPath("/").build(response);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response){
        deleteCookie("username", request, response);
        deleteCookie("password", request, response);
    }
}
