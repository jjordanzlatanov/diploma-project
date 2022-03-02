package org.elsys.ufg;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class CookieFactory {
    private Cookie cookie;

    public CookieFactory(){}

    public CookieFactory create(String name, String value){
        cookie = new Cookie(name, value);
        return this;
    }

    public CookieFactory setMaxAge(Integer age){
        cookie.setMaxAge(age);
        return this;
    }

    public CookieFactory setPath(String path){
        cookie.setPath(path);
        return this;
    }

    public void build(HttpServletResponse response){
        response.addCookie(cookie);
    }

    public void delete(String name, HttpServletRequest request, HttpServletResponse response){
        cookie.setValue("");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
