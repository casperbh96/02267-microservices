package com.dtupay;

import com.dtupay.app.*;
import com.dtupay.database.*;
import dtu.ws.fastmoney.Bank;
import org.junit.Before;
import org.junit.Test;

public class DtuPayAppTest {
    Bank bank;
    ICustomerAdapter customers;
    IMerchantAdapter merchants;
    ITokenAdapter tokens;
    IDtuPayApp dtupay;

    @Before
    public void Setup(){
        customers = new CustomerAdapter();
        merchants = new MerchantAdapter();
        tokens = new TokenAdapter();
        dtupay = new DtuPayApp(bank, customers, merchants, tokens);
    }

    @Test
    public void checkTokenValidityOfUnusedToken() {

    }

    @Test
    public void checkTokenValidityOfUsedToken() {

    }

    @Test
    public void checkTokenValidityOfTokenThatDoesNotExistInTokenDatabase() {

    }

    @Test
    public void checkTokenValidityOfTokenFromAnotherCustomer() {

    }

    @Test
    public void transferMoneyFromExistingCustomerToExistingMerchant() {

    }

    @Test
    public void transferMoneyFromExistingCustomerToMerchantThatDoesNotExist() {

    }

    @Test
    public void transferMoneyFromExistingMerchantToExistingCustomer() {
        
    }

    @Test
    public void transferMoneyByNegativeAmountFromCustomerToMerchant() {

    }

}
