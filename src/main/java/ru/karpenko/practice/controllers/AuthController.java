package ru.karpenko.practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.karpenko.practice.models.Person;
import ru.karpenko.practice.services.PeopleService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PeopleService peopleService;

    @Autowired
    public AuthController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping("/setCookie/{id}")
    public String setCookie(@PathVariable("id") long id, HttpServletResponse response) {
        Person person = peopleService.findOne(id);
        String username = person.getName();
        try {
            Cookie usernameCookie = new Cookie("username", username);
            usernameCookie.setPath("/");
            usernameCookie.setMaxAge(7 * 24 * 60 * 60); // срок действия cookie в секундах (например, 7 дней)
            response.addCookie(usernameCookie);

        }catch (Exception exception){
            System.out.println(exception);
        }
        return "auth/testCookie";
    }



        @GetMapping("/authorization")
        public String showRegistrationForm() {
            return "auth/authorization";
        }

        @PostMapping("/authorization")
        public String register(@RequestParam String username, @RequestParam String password, Model model) {
//            User user = new User();
//            user.setUsername(username);
//            user.setPassword(password);
//            userService.save(user);
//            model.addAttribute("message", "Registration successful");
            return "redirect:auth/welcome";
        }

        @GetMapping("/login")
        public String showLoginForm() {
            return "login";
        }

        @PostMapping("/login")
        public String login(@RequestParam String username, @RequestParam String password, Model model) {
            return "login";
        }

        @GetMapping("/welcome")
        public String welcome() {
            return "welcome";
        }



    @GetMapping("/deleteCookie")
    public String deleteCookie( HttpServletResponse response) {
        try {
            Cookie usernameCookie = new Cookie("username", "");
            usernameCookie.setPath("/");
            usernameCookie.setMaxAge(7 * 24 * 60 * 60); // срок действия cookie в секундах (например, 7 дней)
            response.addCookie(usernameCookie);

        }catch (Exception exception){
            System.out.println(exception);
        }
        return "auth/testCookie";
    }

    @GetMapping("/getUser")
    public String getUser(@CookieValue(value = "username", defaultValue = "Guest") String username, Model model) {
        model.addAttribute("username", username);
        return "auth/GetCookie";
    }
}
