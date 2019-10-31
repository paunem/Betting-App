package com.example.beteasier;

public class User {
    private String name, surname, email, country;
    private double balance;

    public User(String name, String surname, String email, String country, double balance) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.country = country;
        this.balance = balance;
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

    public void setBalance(double balance) {
        this.balance = balance;
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

    public double getBalance() {
        return balance;
    }
}
