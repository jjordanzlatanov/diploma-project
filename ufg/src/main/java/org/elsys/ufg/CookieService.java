package org.elsys.ufg;

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
}
