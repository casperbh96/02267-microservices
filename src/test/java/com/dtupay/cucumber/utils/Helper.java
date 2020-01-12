package com.dtupay.cucumber.utils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.dtupay.app.Customer;
import com.dtupay.app.ITokenManagement;
import com.dtupay.app.Merchant;
import com.dtupay.app.TokenManagement;
import com.dtupay.database.CustomerAdapter;
import com.dtupay.database.ICustomerAdapter;
import com.dtupay.database.IMerchantAdapter;
import com.dtupay.database.MerchantAdapter;
import cucumber.api.java.After;
import dtu.ws.fastmoney.Bank;
import dtu.ws.fastmoney.User;

public class Helper {
	public Set<String> usedAccounts = new HashSet<>();
	public String errorMessage;	
    private Bank bank;
    private ICustomerAdapter customers;
	private IMerchantAdapter merchants;
    private ITokenManagement tokenManager = new TokenManagement();
	public boolean errorHasOccured = false;
    
    public Helper(BankFactory factory) {

    	this.bank = factory.createBank();
    	this.customers = new CustomerAdapter();
    	this.merchants = new MerchantAdapter();
    }
    
	public Bank getBank() {
		return bank;
	}
	public ICustomerAdapter getCustomerAdapter(){ return customers; }

	User createUser(String arg1, String arg2, String arg3) {
		User user = new User();
		user.setFirstName(arg1);
		user.setLastName(arg2);
		user.setCprNumber(arg3);
		return user;
	}
	
	public String createBankAccount(String arg1, String arg2, String arg3, int balance) throws Exception {
		User user = createUser(arg1, arg2, arg3);
		return createBankAccount(user, balance);
	}

	public String createBankAccount(User user, int balance) throws Exception {
		String accountId = getBank().createAccountWithBalance(user, new BigDecimal(balance));
		usedAccounts.add(accountId);
		return accountId;
	}

	public Customer createDtuPayCustomer(String name, int id, int tokens){
    	Customer customer = new Customer(id, name);
		customers.createCustomer(customer);
		tokenManager.CustomerGetTokens(customer, tokens);
		return customer;
	}

	public Merchant createDtuPayMerchant(String name, int id, int tokens) {
		Merchant merchant = new Merchant(id, name);
		merchants.createMerchant(merchant);
		return merchant;
	}

	@After
	public void retireUsedAccounts() {
		for (String id : usedAccounts) {
			try {
				bank.retireAccount(id);
			} catch (Exception e) {
				// ignore: account does not exist anymore
			}
		}
		usedAccounts = new HashSet<String>();
	}


}
