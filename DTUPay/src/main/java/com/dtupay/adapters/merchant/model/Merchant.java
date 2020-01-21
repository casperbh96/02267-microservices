package com.dtupay.adapters.merchant.model;

import org.json.JSONObject;

/**
 * @author Dumitru
 * merchant class
 */
public class Merchant {
    int id;
    String cvr;
    String name;

    /**
     * intialize the merchant with id, cpr and name in json format
     * @param json
     */
    public Merchant(JSONObject json) {
        this.id = json.getInt("id");
        this.cvr = json.getString("cvr");
        this.name = json.getString("name");
    }
    /**
     * add more information
     * @param _id merchant id has been added
     * @param _cvr CPR number
     * @param _name merchant name
     */
    public Merchant(int _id, String _cvr, String _name) {
        id = _id;
        cvr = _cvr;
        name = _name;
    }

    /**
     * To set Id for merchant
     * @param id a new merchant id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Shows merchant id
     * @return merchant id
     */
    public int getId() {
        return id;
    }

    /**
     * To set merchant CPR number
     * @param _cvr merchant CPR number
     */
    public void setCvr(String _cvr) {
        this.cvr = _cvr;
    }

    /**
     * shows merchant cpr number
     * @return merchant CPR number
     */
    public String getCvr() {
        return cvr;
    }

    /**
     * To set merchant name
     * @param name merchant name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * shows merchant name
     * @return merchant name
     */
    public String getName() {
        return name;
    }

}
