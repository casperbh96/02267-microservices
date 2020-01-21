package com.dtupay.adapters.customer.model;

import com.dtupay.adapters.token.model.Token;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dumitru
 *
 */
public class Customer {
    int id;
    String cpr;
    String name;
    List<Token> tokens;

    /**
     * intialize the customer with id, cpr and name in json format
     * @param json
     */
    public Customer(JSONObject json) {
        this.id = json.getInt("id");
        this.cpr = json.getString("cpr");
        this.name = json.getString("name");
        tokens = new ArrayList<>();
        // TODO: parse tokens
    }

    /**
     * add more information
     * @param _id customer id has been added
     * @param _cpr CPR number
     * @param _name customer name
     */

    public Customer(int _id, String _cpr, String _name) {
        id = _id;
        cpr = _cpr;
        name = _name;
        tokens = new ArrayList<>();
    }
    /**
     * To set Id for customer
     * @param id a new customer id
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Shows customer id
     * @return customer id
     */

    public int getId() {
        return id;
    }

    /**
     * To set customer CPR number
     * @param _cpr customer CPR number
     */
    public void setCpr(String _cpr) {
        this.cpr = _cpr;
    }

    /**
     * shows customer cpr number
     * @return customer CPR number
     */
    public String getCpr() {
        return cpr;
    }

    /**
     * To set customer name
     * @param name customer name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * shows customer name
     * @return customer name
     */

    public String getName() {
        return name;
    }

    /**
     * To set list of customer tokens
     * @param tokens customer tokens
     */

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * shows list of customer tokens
     * @return list of tokens
     */

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
