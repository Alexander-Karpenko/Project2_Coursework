package ru.karpenko.practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.karpenko.practice.models.Person;
import ru.karpenko.practice.models.Book;
import ru.karpenko.practice.services.BooksService;
import ru.karpenko.practice.services.CookiesService;
import ru.karpenko.practice.services.PeopleService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    private final BooksService booksService;

    private final CookiesService cookiesService;

    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService, CookiesService cookiesService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.cookiesService = cookiesService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        Person person = peopleService.findOne(id);
        model.addAttribute("person", person);
        model.addAttribute("isAdmin", peopleService.isAdmin(person.getUsername()));
        try {
            model.addAttribute("books", booksService.checkOverdue(booksService.findByOwner(person)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id, HttpServletResponse response ) {
        peopleService.delete(id);
        cookiesService.deleteCookie(response);
        return "redirect:/people";
    }

    @GetMapping("/style")
    public String styleTest(){
        return "people/test";
    }

}
