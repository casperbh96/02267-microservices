package com.dtupay;

import com.dtupay.adapters.bank.BankAdapter;
import com.dtupay.adapters.bank.IBankAdapter;
import com.dtupay.adapters.bank.exceptions.BankAdapterException;
import com.dtupay.adapters.customer.model.Customer;
import com.dtupay.adapters.customer.CustomerAdapter;
import com.dtupay.adapters.customer.ICustomerAdapter;
import com.dtupay.adapters.customer.exceptions.CustomerException;
import com.dtupay.adapters.merchant.IMerchantAdapter;
import com.dtupay.adapters.merchant.exceptions.MerchantException;
import com.dtupay.adapters.merchant.model.Merchant;
import com.dtupay.adapters.merchant.MerchantAdapter;
import com.dtupay.adapters.token.ITokenAdapter;
import com.dtupay.adapters.token.exceptions.TokenException;
import com.dtupay.adapters.token.model.Token;
import com.dtupay.adapters.token.TokenAdapter;
import com.dtupay.adapters.transaction.ITransactionAdapter;
import com.dtupay.adapters.transaction.TransactionAdapter;
import com.dtupay.adapters.transaction.exceptions.TransactionException;
import com.dtupay.adapters.transaction.model.TransactionCustomer;
import com.dtupay.adapters.transaction.model.TransactionMerchant;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * DTUPayApp class
 */
public class DTUPayApp implements IDTUPayApp {

    private IBankAdapter bank;
    private ICustomerAdapter customers;
    private IMerchantAdapter merchants;
    private ITokenAdapter tokens;
    private ITransactionAdapter transactions;

    /**
     * contructor method to initialize DtuPayApp function
     * @param bankAdapter
     * @param customerAdapter
     * @param merchantsAdapter
     * @param tokensAdapters
     * @param transactionsAdapter
     */
    public DTUPayApp(IBankAdapter bankAdapter, ICustomerAdapter customerAdapter, IMerchantAdapter merchantsAdapter, ITokenAdapter tokensAdapters, ITransactionAdapter transactionsAdapter) {
        this.bank = bankAdapter;
        this.customers = customerAdapter;
        this.merchants = merchantsAdapter;
        this.tokens = tokensAdapters;
        this.transactions = transactionsAdapter;
    }

    /**
     * initializes new varaibles for DtuPayApp which are using rest service to get data
     * @param customerBaseUrl
     * @param merchantBaseUrl
     * @param transactionBaseUrl
     * @param tokenBaseUrl
     */
    public DTUPayApp(String customerBaseUrl, String merchantBaseUrl, String transactionBaseUrl, String tokenBaseUrl) {
        this.bank = new BankAdapter();
        this.customers = new CustomerAdapter(customerBaseUrl);
        this.merchants = new MerchantAdapter(merchantBaseUrl);
        this.tokens = new TokenAdapter(tokenBaseUrl);
        this.transactions = new TransactionAdapter(transactionBaseUrl);
    }

    /**
     * to create new customer
     * @param cpr number
     * @param name customer name
     * @return customer
     */
    @Override
    public Customer createCustomer(String cpr, String name) throws CustomerException {
        return customers.createCustomer(cpr, name);
    }

    /**
     * This class updates the customer with new data values
     * @param customerId
     * @param cpr number
     * @param name customer name
     * @return customer
     * @throws CustomerException
     */

    @Override
    public Customer updateCustomer(int customerId, String cpr, String name) throws CustomerException {
        return customers.updateCustomer(customerId, cpr, name);
    }

    /**
     * customer requesting more tokens
     * @param customerId
     * @param numOfTokens
     * @return list of tokens
     * @throws CustomerException
     * @throws TokenException
     */
    @Override
    public List<Token> getMoreTokens(int customerId, int numOfTokens) throws CustomerException, TokenException {
        Customer customer = customers.getCustomerById(customerId);

        int unusedToken = 0;
        for (Token t : customer.getTokens()) {
            if (!t.getUsed()) {
                unusedToken++;
            }
        }

        if (unusedToken > 1) {
            throw new TokenException("Customer with id {0} has more than one unused tokens left");
        }

        return tokens.getNewTokensForCustomer(customerId, numOfTokens);
    }

    /**
     * gives the monthly report for all merchant transactions
     * @param customerId
     * @param month
     * @param year
     * @return list of transactions
     * @throws TransactionException
     */
    public List<TransactionCustomer> generateMonthlyCustomerReport(int customerId, int month, int year) throws TransactionException {
        return transactions.getMonthlyCustomerReport(customerId, month, year);
    }

    /**
     * to create new merchant
     * @param cvr number
     * @param name merchant name
     * @return merchant
     * @throws MerchantException
     */
    @Override
    public Merchant createMerchant(String cvr, String name) throws MerchantException {
        return merchants.createMerchant(cvr, name);
    }

    /**
     * This class updates the merchant with new data values
     * @param merchantId merchant id
     * @param cvr number
     * @param name merchant name
     * @return merchant
     * @throws MerchantException
     */
    @Override
    public Merchant updateMerchant(int merchantId, String cvr, String name) throws MerchantException {
        return merchants.updateMerchant(merchantId, cvr, name);
    }

    /**
     * Transfer money from sender to reciever account
     * @param merchantId
     * @param customerToken
     * @param amount
     * @param description
     * @throws TokenException
     * @throws CustomerException
     * @throws MerchantException
     * @throws BankAdapterException
     * @throws TransactionException
     */
    @Override
    public void transferMoney(int merchantId, Token customerToken, BigDecimal amount, String description) throws TokenException, CustomerException, MerchantException, BankAdapterException, TransactionException {
        if (!tokens.isTokenValid(customerToken.getId())) {
            throw new TokenException("Token provided is invalid");
        }
        tokens.markTokenAsUsed(customerToken.getId());

        String customerCpr = customers.getCustomerById(customerToken.getCustomerId()).getCpr();
        String merchantCvr = merchants.getMerchantById(merchantId).getCvr();

        bank.makeTransaction(customerCpr, merchantCvr, amount, description);

        transactions.registerTransaction(new Timestamp(System.currentTimeMillis()), customerToken.getCustomerId(), merchantId, customerToken.getId(), amount, false);
    }

    /**
     * gives the monthly report for all merchant transactions
     * @param merchantId
     * @param month
     * @param year
     * @return list of transactions
     * @throws TransactionException
     */
    @Override
    public List<TransactionMerchant> generateMonthlyMerchantReport(int merchantId, int month, int year) throws TransactionException {
        return transactions.getMonthlyMerchantReport(merchantId, month, year);
    }


}
