package com.calculator;

import java.util.List;

public class Customer {
    String name;
    int id;
    List<Token> tokens;

    public Customer () {}

    public Customer(int _id, String _name) {
        id = _id;
        name = _name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
