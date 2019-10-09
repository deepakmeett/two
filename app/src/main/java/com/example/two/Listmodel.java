package com.example.two;

public class Listmodel {

    public String name, email, password;

    public Listmodel(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public Listmodel() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}