package kz.assel.practice.dao;

import kz.assel.practice.model.Book;
import kz.assel.practice.model.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll(){
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> getById(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny();
    }

    public void add(Book book){
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book book){
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE id=?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public Optional<Person> getPerson(int id){
        return jdbcTemplate.query("SELECT Person.* FROM Person JOIN Book ON Person.id = Book.person_id" +
                                        " WHERE Book.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void assign(int id, Person newPerson){
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?", newPerson.getId(), id);
    }

    public void freeUp(int id){
        jdbcTemplate.update("UPDATE Book SET person_id = NULL WHERE id = ?", id);
    }
}
