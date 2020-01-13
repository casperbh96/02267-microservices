package com.dtupay.app;

import dtu.ws.fastmoney.BankServiceException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Merchant {
    String name;
    String id;
    IDtuPayApp dtuPay;

    public Merchant () {}

    public Merchant( String _id, String _name) {
        id = _id;
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void requestTransfer(Token token, BigDecimal amount, String description) throws BankServiceException {
        dtuPay.transferMoney(this.id, token, amount, description);
    }
}
