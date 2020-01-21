package com.dtupay;

import com.dtupay.adapters.bank.exceptions.BankAdapterException;
import com.dtupay.adapters.customer.model.Customer;
import com.dtupay.adapters.customer.exceptions.CustomerException;
import com.dtupay.adapters.merchant.exceptions.MerchantException;
import com.dtupay.adapters.merchant.model.Merchant;
import com.dtupay.adapters.token.exceptions.TokenException;
import com.dtupay.adapters.token.model.Token;
import com.dtupay.adapters.transaction.exceptions.TransactionException;
import com.dtupay.adapters.transaction.model.TransactionCustomer;
import com.dtupay.adapters.transaction.model.TransactionMerchant;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Dumitru
 * interface for DTUPayApp containing all its funtionalities
 */
public interface IDTUPayApp {
    // customer methods
    Customer createCustomer(String cpr, String name) throws CustomerException ;

    Customer updateCustomer(int customerId, String cpr, String name) throws CustomerException;

    List<Token> getMoreTokens(int customerId, int numOfTokens) throws CustomerException, TokenException;

    List<TransactionCustomer> generateMonthlyCustomerReport(int customerId, int month, int year) throws TransactionException;

    // merchant methods
    Merchant createMerchant(String cvr, String name) throws MerchantException;

    Merchant updateMerchant(int merchantId, String cvr, String name) throws MerchantException;

    void transferMoney(int merchantId, Token merchantToken, BigDecimal amount, String description) throws TokenException, CustomerException, MerchantException, BankAdapterException, TransactionException;

    List<TransactionMerchant> generateMonthlyMerchantReport(int merchantId, int month, int year) throws TransactionException;
}
