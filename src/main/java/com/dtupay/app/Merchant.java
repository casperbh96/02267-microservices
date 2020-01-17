package com.dtupay.app;

import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;
import dtu.ws.fastmoney.BankServiceException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Merchant {
    int id;
    String cvr;
    String name;
    IDtuPayApp dtuPay;

    public Merchant() {
    }

    public Merchant(int _id, String _cvr, String _name) {
        id = _id;
        cvr = _cvr;
        name = _name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setCvr(String _cvr) {
        this.cvr = _cvr;
    }
    public String getCvr() {
        return cvr;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setDtuPay(IDtuPayApp dtuPay) {
        this.dtuPay = dtuPay;
    }
    public IDtuPayApp getDtuPay() {
        return dtuPay;
    }

    public boolean scanCustomerToken(Token customerToken) throws FakeToken, TokenAlreadyUsed {
        return dtuPay.checkTokenValidity(customerToken);
    }

    public void requestTransfer(Token token, BigDecimal amount, String description) throws Exception {
        dtuPay.transferMoney(this.cvr, token, amount, description);
    }
}
