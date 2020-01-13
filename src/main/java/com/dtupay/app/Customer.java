package com.dtupay.app;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    String name;
    String id;
    List<Token> tokens;
    IDtuPayApp dtuPay;

    public Customer() {
    }

    public Customer(String _id, String _name) {
        id = _id;
        name = _name;
        tokens = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public IDtuPayApp getDtuPay() {
        return dtuPay;
    }

    public void setDtuPay(IDtuPayApp dtuPay) {
        this.dtuPay = dtuPay;
    }

    public Token giveToken(){
        // TODO: might need some work on selecting which token to give
        if (!tokens.isEmpty())
            return tokens.get(0);
        return null;
    }


}
