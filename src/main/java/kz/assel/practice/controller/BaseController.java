package kz.assel.practice.controller;

import kz.assel.practice.dao.BookDao;
import kz.assel.practice.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    private final PersonDao personDao;
    private final BookDao bookDao;

    @Autowired
    public BaseController(PersonDao personDao, BookDao bookDao) {
        this.personDao = personDao;
        this.bookDao = bookDao;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personDao.getAll());
        model.addAttribute("books", bookDao.getAll());

        return "index";
    }
}
