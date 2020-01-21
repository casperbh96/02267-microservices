package com.dtupay.adapters.customer.model;

import com.dtupay.adapters.token.model.Token;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    int id;
    String cpr;
    String name;
    List<Token> tokens;

    public Customer(JSONObject json) {
        this.id = json.getInt("id");
        this.cpr = json.getString("cpr");
        this.name = json.getString("name");
        tokens = new ArrayList<>();
        // TODO: parse tokens
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

    public Token getUnusedToken() {
        for (Token t : getTokens()) {
            if (!t.getUsed()){
                return t;
            }
        }
        return null;
    }

}
