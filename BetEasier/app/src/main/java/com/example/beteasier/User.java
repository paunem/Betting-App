package com.example.beteasier;

public class User {
    private String name, surname, email, country;

    public User(String name, String surname, String email, String country) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.country = country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }
}
