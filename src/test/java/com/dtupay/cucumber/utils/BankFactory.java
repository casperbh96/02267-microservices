package com.dtupay.cucumber.utils;

import com.dtupay.bank.BankAdapterJar;
import com.dtupay.bank.IBankAdapter;
import dtu.ws.fastmoney.Bank;
import dtu.ws.fastmoney.persistency.InMemoryRepository;

public class BankFactory {
	public IBankAdapter createBank() {
		IBankAdapter bank = new BankAdapterJar();
		return bank;
	}
}
