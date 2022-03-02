package org.elsys.ufg;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class CookieFactory {
    private Cookie cookie;
    private Map<String, Cookie> cookieHolder;

    public CookieFactory(){
        cookieHolder = new HashMap<>();
    }

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
        cookieHolder.put(cookie.getName(), cookie);
        response.addCookie(cookie);
    }

    public void delete(String name, HttpServletResponse response){
        if(!cookieHolder.containsKey(name)){
            return;
        }

        cookie = cookieHolder.get(name);
        cookieHolder.remove(name);
        cookie.setValue("");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
