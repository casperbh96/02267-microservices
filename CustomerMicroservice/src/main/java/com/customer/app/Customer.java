package com.customer.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created a customer class to define customer and its attributes
 */

public class Customer {
    int id;
    String cpr;
    String name;
    List<Token> tokens;

    public Customer() {
    }

    /**
     * This is constructor for customer class to initiate the customer object
     * @param _name an initial customer name with a list of tokens
     */

    public Customer(String _name) {
        name = _name;
        tokens = new ArrayList<>();
    }

    /**
     * To add information
     * @param _cpr CPR number is added as additional information which will be used for bank transactions
     * @param _name a new customer name
     */

    public Customer(String _cpr, String _name) {
        cpr = _cpr;
        name = _name;
        tokens = new ArrayList<>();
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

    /**
     * Provides customer a token when needed
     * @return token
     */

    public Token giveToken(){
        // TODO: might need some work on selecting which token to give
        if (!tokens.isEmpty())
            return tokens.get(0);
        return null;
    }


}
