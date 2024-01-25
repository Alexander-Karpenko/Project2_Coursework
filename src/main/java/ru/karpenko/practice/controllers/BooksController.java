package ru.karpenko.practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.karpenko.practice.dao.BookDAO;
import ru.karpenko.practice.dao.PersonDAO;
import ru.karpenko.practice.models.Book;
import ru.karpenko.practice.models.Person;
import ru.karpenko.practice.services.BooksService;
import ru.karpenko.practice.services.PeopleService;

import javax.validation.Valid;


@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BooksService booksService, PeopleService peopleService) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model){
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
    public String newBook(@ModelAttribute("book") Book book){
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
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person){
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("people",peopleService.findAll());
        return "books/show";

    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        booksService.update(id, book);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/add")
    public String appointBook(@PathVariable("id") int id, @ModelAttribute Person person ){
        Book book = booksService.findOne(id);
        book.setOwner(person);
        booksService.save(book);
//        booksService.appointBook(id, person);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        Book book = booksService.findOne(id);
        book.setOwner(null);
        booksService.save(book);
        return  "redirect:/books";
    }
}

