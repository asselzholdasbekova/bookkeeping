package kz.assel.practice.controller;

import kz.assel.practice.dao.PersonDao;
import kz.assel.practice.model.Person;
import kz.assel.practice.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonDao personDao;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDao personDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String getAll(Model model){
        model.addAttribute("people", personDao.getAll());

        return "people/all";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDao.getById(id).get());
        model.addAttribute("books", personDao.getBooksByPersonId(id));

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping()
    public String save(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        personDao.add(person);

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDao.getById(id));

        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "people/edit";
        }
        personDao.update(id, person);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDao.delete(id);

        return "redirect:/people";
    }
}
