package kz.assel.practice.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {

    private int id;

    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, max = 200, message = "Name size should be between 2 and 200 symbols!")
    private String fullName;

    @Min(value = 1900, message = "Year of birth cannot be less than 1900")
    private int year;

    public Person(){}

    public Person(String fullName, int year) {
        this.fullName = fullName;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
