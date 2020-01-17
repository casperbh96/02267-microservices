package com.dtupay.app;

import com.dtupay.bank.exceptions.BankAdapterException;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.IMerchantAdapter;
import com.dtupay.database.ITokenAdapter;
import com.dtupay.bank.IBankAdapter;
import com.dtupay.database.exceptions.CustomerDoesNotExist;
import com.dtupay.database.exceptions.FakeToken;
import com.dtupay.database.exceptions.TokenAlreadyUsed;

import java.math.BigDecimal;

public class DtuPayApp implements IDtuPayApp {

    private IBankAdapter bank;
    private ICustomerAdapter customers;
    private IMerchantAdapter merchants;
    private ITokenAdapter tokens;
    private ITokenManagement tokenManager;

    public DtuPayApp(IBankAdapter bank, ICustomerAdapter customers, IMerchantAdapter merchants, ITokenAdapter tokens) {
        this.bank = bank;
        this.customers = customers;
        this.merchants = merchants;
        this.tokens = tokens;
    }

    @Override
    public boolean checkTokenValidity(Token token) throws FakeToken, TokenAlreadyUsed {
        // TODO: also check the customerID who provided the token?
        return tokens.isTokenValid(token);
    }

    @Override
    public void transferMoney(String merchantId, Token customerToken, BigDecimal amount, String description) throws BankAdapterException {
        // assumption that token is valid at this point..
        try {
            Customer customer = customers.getCustomerByCustomerId(customerToken.customerId);
            bank.makeTransaction(customer.getCpr(), merchantId, amount, description);
        } catch (CustomerDoesNotExist customerDoesNotExist) {
            customerDoesNotExist.printStackTrace();
        }
    }

}
