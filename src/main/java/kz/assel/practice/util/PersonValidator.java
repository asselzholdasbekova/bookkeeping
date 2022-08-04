package kz.assel.practice.util;

import kz.assel.practice.dao.PersonDao;
import kz.assel.practice.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDao personDao;

    @Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person)target;

        if(personDao.getByFullName(person.getFullName()).isPresent()){
            errors.rejectValue("fullName", "", "Person with this full name already exists!");
        }
    }
}
