package ru.karpenko.practice.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import ru.karpenko.practice.models.Person;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class CookiesService {

    public void setCookie(String username, HttpServletResponse response) {
        try {
            Cookie usernameCookie = new Cookie("username", username);
            usernameCookie.setPath("/");
            usernameCookie.setMaxAge(7 * 24 * 60 * 60); // срок действия cookie в секундах (например, 7 дней)
            response.addCookie(usernameCookie);

        }catch (Exception exception){
            System.out.println(exception);
        }
    }

    public void deleteCookie( HttpServletResponse response) {
        try {
            Cookie usernameCookie = new Cookie("username", "");
            usernameCookie.setPath("/");
            usernameCookie.setMaxAge(7 * 24 * 60 * 60); // срок действия cookie в секундах (например, 7 дней)
            response.addCookie(usernameCookie);

        }catch (Exception exception){
            System.out.println(exception);
        }
    }

}
