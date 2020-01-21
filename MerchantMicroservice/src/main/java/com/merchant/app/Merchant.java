package com.merchant.app;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created a merchant class to define customer and its attributes
 */

public class Merchant {
    int id;
    String cvr;
    String name;

    public Merchant() {
    }

    /**
     * This is constructor for merchant class to initiate the merchant object
     * @param _cpr CPR number is added as additional information which will be used for bank transactions
     * @param _name a new merchant name
     */

    public Merchant(String _cvr, String _name) {
        cvr = _cvr;
        name = _name;
    }

    /**
     * add more information
     * @param _id merchant id has been added
     * @param _cpr CPR number
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
     * @param _cpr merchant CPR number
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
    /**
     * To set DtuPay app
     * @param dtuPay
     */

    /**
     * shows DtuPay app
     * @return DtuPay
     */
    /**
     * Function to scan customer token by merchant
     * @param customerToken token by customer at the time of purchase
     * @return boolean whether merchant is able to scan the token or not
     * @throws FakeToken exception when token is invalid
     * @throws TokenAlreadyUsed exception when token is already used
     */
    /**
     * Function used to request the transfer of money after the validation of token
     * @param token from customer
     * @param amount to be tranferred
     * @param description about the transaction
     * @throws Exception
     */
}
