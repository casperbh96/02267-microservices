package com.dtupay.junit;

import com.dtupay.bank.BankAdapter;
import com.dtupay.bank.IBankAdapter;
import com.dtupay.bank.exceptions.BankAdapterException;
import dtu.ws.fastmoney.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BankAdapterTest {
    IBankAdapter bankAdapter;
    User user;
    BigDecimal big;
    int cpr;

    @Before
    public void Setup() throws BankAdapterException {
        bankAdapter = new BankAdapter();
        user = new User();
        big = new BigDecimal(99999);
        cpr = 1234561234;
    }

    @After
    public void cleanUp() throws BankAdapterException {
        bankAdapter.deleteAllCreatedAccounts();
    }

    @Test
    public void CreateAccountWithNoException() throws BankAdapterException {
        bankAdapter.createAccount("test", cpr, big);
    }

    @Test(expected = BankAdapterException.class)
    public void ChecksIfThereIsThrownAnExceptionWhenGivingWrongInputsToCreateAccount() throws BankAdapterException {
        bankAdapter.createAccount(null, cpr, big);
    }

    @Test(expected = BankAdapterException.class)
    public void ThrowsAExceptionWhenAccoutIsDeletedAndTryingToFindIt() throws BankAdapterException {
        bankAdapter.createAccount("test", cpr, big);
        bankAdapter.removeAccountByCpr(cpr);
        bankAdapter.getBalanceByCPR(cpr);
    }

    @Test
    public void ReturnsTheValueOfTheBalanceOfAAccount() throws BankAdapterException {
        bankAdapter.createAccount("Test", cpr, big);
        BigDecimal actual = bankAdapter.getBalanceByCPR(cpr);
        assertEquals(big, actual);
    }

    @Test(expected = BankAdapterException.class)
    public void ThrowsAExceptionWhenTryingToGetBalanceFromANonExitingAccount() throws BankAdapterException {
        bankAdapter.getBalanceByCPR(cpr);
    }

    @Test
    public void MoneyShouldBeTransferredFromCustomerToMerchant() throws BankAdapterException {
        bankAdapter.createAccount("Test", cpr, big);
        bankAdapter.createAccount("MerchantCpr", 1231231234, big);
        bankAdapter.makeTransaction(cpr, 1231231234, big, "TestComment");
        BigDecimal actual = bankAdapter.getBalanceByCPR(cpr);
        BigDecimal expected = BigDecimal.valueOf(0);
        assertEquals(expected, actual);
    }
}