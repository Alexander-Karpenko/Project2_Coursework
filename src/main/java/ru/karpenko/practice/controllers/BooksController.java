package ru.karpenko.practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.karpenko.practice.models.Book;
import ru.karpenko.practice.models.Genre;
import ru.karpenko.practice.models.Person;
import ru.karpenko.practice.services.BooksService;
import ru.karpenko.practice.services.GenresService;
import ru.karpenko.practice.services.PeopleService;

import javax.validation.Valid;
import java.util.Date;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;
    private final GenresService genresService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService, GenresService genresService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.genresService = genresService;
    }

    @GetMapping()
    public String index(Model model,@CookieValue(value = "username", defaultValue = "Guest") String username ){
        model.addAttribute("isAdmin", peopleService.isAdmin(username));
        model.addAttribute("books", booksService.findAll());
        return "books/index";
    }
    @GetMapping( params = { "page", "books_per_page" })
    public String index2(Model model, @RequestParam(name = "page") int page,
                        @RequestParam(name = "books_per_page") int size){
        model.addAttribute("books", booksService.findAll(page,size));
        return "books/index";
    }
    @GetMapping(params = {"sort_by_year"})
    public String index3(Model model, @RequestParam(name = "sort_by_year") boolean sortByYear){
        model.addAttribute("books", booksService.findAll(sortByYear));
        return "books/index";
    }

    @GetMapping( params = { "page", "books_per_page", "sort_by_year" })
    public String index4(Model model, @RequestParam(name = "page") int page,
                        @RequestParam(name = "books_per_page") int size,
                         @RequestParam(name = "sort_by_year") boolean sortByYear){
        model.addAttribute("books", booksService.findAll(page,size,sortByYear));
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book, Model model,@ModelAttribute("genre") Genre genre){
        model.addAttribute("genres",genresService.findAll());
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model, @ModelAttribute("person")Person person){
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("people",peopleService.findAll());
        return "books/show";

    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        booksService.update(id, book);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id){
        booksService.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/add")
    public String appointBook(@PathVariable("id") long id, @ModelAttribute Person person ){
        booksService.appointBook(id, person);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") long id){
        booksService.releaseBook(id);
        return  "redirect:/books";
    }

    @GetMapping("/search")
    public String searchForm (){
        return "books/search_form";
    }

    @GetMapping(value = "/search", params = {"search"})
    public String search ( Model model, @RequestParam("search") String name)
    {
        model.addAttribute("found_books", booksService.searchByName(name));
        return "books/search";
    }
}

