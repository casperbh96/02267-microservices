package com.dtupay.app;

import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.IMerchantAdapter;
import com.dtupay.database.ITokenAdapter;
import com.dtupay.bank.IBankAdapter;
import dtu.ws.fastmoney.BankServiceException;

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
    public boolean checkTokenValidity(Token token) {
        // TODO: also check the customerID who provided the token?
        if (tokens.checkExists(token) && !token.used)
            return true;
        return false;
    }

    @Override
    public void transferMoney(String merchantId, Token merchantToken, BigDecimal amount, String description) throws BankServiceException{
        // assumption that token is valid at this point..

        bank.makeTransaction(merchantToken.customerId, merchantId, amount, description);
    }

}
