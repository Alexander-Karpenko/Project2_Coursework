package ru.karpenko.practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.karpenko.practice.models.LoginForm;
import ru.karpenko.practice.models.Person;
import ru.karpenko.practice.services.CookiesService;
import ru.karpenko.practice.services.PeopleService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PeopleService peopleService;
    private final CookiesService cookiesService;

    @Autowired
    public AuthController(PeopleService peopleService, CookiesService cookiesService) {
        this.peopleService = peopleService;
        this.cookiesService = cookiesService;
    }

    @GetMapping("/authorization")
    public String showRegistrationForm(@ModelAttribute("person") Person person)  {
        return "auth/authorization";
    }

    @PostMapping()
    public String register(@ModelAttribute("person") @Valid Person person,
                           BindingResult bindingResult, HttpServletResponse response) {
         if (bindingResult.hasErrors())
             return "auth/authorization";
         if (person.getUsername().equals("Guest"))
             return "auth/authorization";
         person.setRole("USER");
         peopleService.save(person);
         cookiesService.setCookie(person.getUsername(),response);
         return "redirect:auth/welcome";
    }

    @GetMapping("/login")
    public String showLoginForm(@ModelAttribute("loginForm") LoginForm loginForm)
    {
        return "auth/login";
    }

    @GetMapping("/enter") public String login(@ModelAttribute("loginForm") @Valid LoginForm loginForm,
                                              BindingResult bindingResult, HttpServletResponse response) {
        Person byUsername = peopleService.findByUsername(loginForm.getUsername());
        if (byUsername == null){
            return "auth/login";
        }
        if (bindingResult.hasErrors()){
            System.out.println(bindingResult);
            return "auth/login";
        }
        if(!byUsername.getPassword().equals(loginForm.getPassword())){
            return "auth/login";
        }
        cookiesService.deleteCookie(response);
        cookiesService.setCookie(loginForm.getUsername(), response);
        return "redirect:/auth/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(@CookieValue(value = "username", defaultValue = "Guest") String username, Model model) {
        Person person = peopleService.findByUsername(username);
        if(person != null) {
            model.addAttribute("person", person);
            model.addAttribute("username", username);
            model.addAttribute("role", person.getRole());
        }else {
            model.addAttribute("role", "GUEST");
        }
        return "auth/welcome";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response){
        cookiesService.deleteCookie(response);
        return "redirect:/auth/welcome";
    }

    @GetMapping("/getUser")
    public String getUser(@CookieValue(value = "username", defaultValue = "Guest") String username, Model model) {
        model.addAttribute("username", username);
        return "auth/GetCookie";
    }
}
