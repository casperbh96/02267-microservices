package com.dtupay.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Merchant {
    String name;
    String id;
    List<Token> tokens;
    IDtuPayApp dtuPay;

    public Merchant () {}

    public Merchant( String _id, String _name) {
        id = _id;
        name = _name;
        tokens = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IDtuPayApp getDtuPay() {
        return dtuPay;
    }

    public void setDtuPay(IDtuPayApp dtuPay) {
        this.dtuPay = dtuPay;
    }

    public boolean scanCustomerToken(Token customerToken){
        return dtuPay.checkTokenValidity(customerToken);
    }

    public void requestTransfer(Token token, BigDecimal amount, String description){
        dtuPay.transferMoney(this.id, token, amount, description);
    }
}
