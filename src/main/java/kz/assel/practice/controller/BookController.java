package kz.assel.practice.controller;

import kz.assel.practice.dao.BookDao;
import kz.assel.practice.dao.PersonDao;
import kz.assel.practice.model.Book;
import kz.assel.practice.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;
    private final PersonDao personDao;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao){
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping()
    public String getAll(Model model){
        model.addAttribute("books", bookDao.getAll());

        return "books/all";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model, @ModelAttribute("newPerson") Person newPerson){
        model.addAttribute("book", bookDao.getById(id).get());

        Optional<Person> person = bookDao.getPerson(id);
        if(person.isPresent()){
            model.addAttribute("person", person.get());
        }
        else {
            model.addAttribute("people", personDao.getAll());
        }

        return "books/show";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("newPerson") Person newPerson){
        bookDao.assign(id, newPerson);

        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/freeup")
    public String freeUp(@PathVariable("id") int id){
        bookDao.freeUp(id);

        return "redirect:/books/" + id;
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String save(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/new";
        }
        bookDao.add(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDao.getById(id));

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "books/edit";
        }
        bookDao.update(id, book);

        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDao.delete(id);

        return "redirect:/books";
    }
}
