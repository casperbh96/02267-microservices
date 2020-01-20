package com.customer.app;

import dtu.ws.fastmoney.BankServiceException;

public class Merchant {
    int id;
    String cvr;
    String name;

    public Merchant() {
    }

    public Merchant(String _cvr, String _name) {
        cvr = _cvr;
        name = _name;
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
}
