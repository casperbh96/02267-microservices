package com.dtupay.app;

import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;
import dtu.ws.fastmoney.BankServiceException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Merchant {
    String name;
    int id;
    IDtuPayApp dtuPay;

    public Merchant() {
    }

    public Merchant(int _id, String _name) {
        id = _id;
        name = _name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IDtuPayApp getDtuPay() {
        return dtuPay;
    }

    public void setDtuPay(IDtuPayApp dtuPay) {
        this.dtuPay = dtuPay;
    }

    public boolean scanCustomerToken(Token customerToken) throws FakeToken, TokenAlreadyUsed {
        return dtuPay.checkTokenValidity(customerToken);
    }

    public void requestTransfer(Token token, BigDecimal amount, String description) throws Exception {
        dtuPay.transferMoney(this.id, token, amount, description);
    }
}
