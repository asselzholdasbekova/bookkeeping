package kz.assel.practice.dao;

import kz.assel.practice.model.Book;
import kz.assel.practice.model.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> getById(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public Optional<Person> getByFullName(String fullName){
        return jdbcTemplate.query("SELECT * FROM Person WHERE full_name=?", new Object[]{fullName},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void add(Person person){
        jdbcTemplate.update("INSERT INTO Person(full_name, year) VALUES (?, ?)",
                person.getFullName(), person.getYear());
    }

    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE Person SET full_name=?, year=? WHERE id=?",
                person.getFullName(), person.getYear(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public List<Book> getBooksByPersonId(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
