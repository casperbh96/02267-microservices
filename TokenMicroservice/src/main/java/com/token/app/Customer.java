package com.token.app;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    int id;
    String cpr;
    String name;
    List<Token> tokens;

    public Customer() {
    }

    public Customer(String _name) {
        name = _name;
        tokens = new ArrayList<>();
    }

    public Customer(String _cpr, String _name) {
        cpr = _cpr;
        name = _name;
        tokens = new ArrayList<>();
    }

    public Customer(int _id, String _cpr, String _name) {
        id = _id;
        cpr = _cpr;
        name = _name;
        tokens = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCpr(String _cpr) {
        this.cpr = _cpr;
    }

    public String getCpr() {
        return cpr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public Token giveToken() {
        if (!tokens.isEmpty())
            return tokens.get(0);
        return null;
    }

    public void addToken(Token token) {
        tokens.add(token);
    }


}
