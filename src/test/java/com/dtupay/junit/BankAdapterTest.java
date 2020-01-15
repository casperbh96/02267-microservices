package com.dtupay.junit;

import com.dtupay.bank.BankAdapterSoap;
import com.dtupay.bank.IBankAdapter;
import dtu.ws.fastmoney.BankServiceException_Exception;
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
    String cpr;

    @Before
    public void Setup() throws BankServiceException_Exception {
        bankAdapter = new BankAdapterSoap();
        bankAdapter.deleteAllAccounts();
        user = new User();
        big = new BigDecimal(99999);
        cpr = "1234561234";
    }

    @Test
    public void CreateAccountWithNoException() throws BankServiceException_Exception {
        bankAdapter.createAccount("test", cpr, big);
    }

    @Test(expected = BankServiceException_Exception.class)
    public void ChecksIfThereIsThrownAnExceptionWhenGivingWrongInputsToCreateAccount() throws BankServiceException_Exception {
        bankAdapter.createAccount(null, cpr, big);
    }

    @Test(expected = BankServiceException_Exception.class)
    public void ThrowsAExceptionWhenAccoutIsDeletedAndTryingToFindIt() throws BankServiceException_Exception {
        bankAdapter.createAccount("test", cpr, big);
        bankAdapter.removeAccountByCpr(cpr);
        bankAdapter.getBalanceByCPR(cpr);
    }

    @Test
    public void ReturnsTheValueOfTheBalanceOfAAccount() throws BankServiceException_Exception {
        bankAdapter.createAccount("Test", cpr, big);
        BigDecimal actual = bankAdapter.getBalanceByCPR(cpr);
        assertEquals(big, actual);
    }

    @Test(expected = BankServiceException_Exception.class)
    public void ThrowsAExceptionWhenTryingToGetBalanceFromANonExitingAccount() throws BankServiceException_Exception {
        bankAdapter.getBalanceByCPR(cpr);
    }

    @Test
    public void MoneyShouldBeTransferredFromCustomerToMerchant() throws BankServiceException_Exception {
        bankAdapter.createAccount("Test", cpr, big);
        bankAdapter.createAccount("MerchantCpr", "1231231234", big);
        bankAdapter.makeTransaction(cpr, "1231231234", big, "TestComment");
        BigDecimal actual = bankAdapter.getBalanceByCPR(cpr);
        BigDecimal expected = BigDecimal.valueOf(0);
        assertEquals(expected, actual);
    }
}