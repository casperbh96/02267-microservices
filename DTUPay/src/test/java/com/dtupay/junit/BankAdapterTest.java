package com.dtupay.junit;

import com.dtupay.adapters.bank.BankAdapter;
import com.dtupay.adapters.bank.IBankAdapter;
import com.dtupay.adapters.bank.exceptions.BankAdapterException;
import dtu.ws.fastmoney.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Junit testing class for bank adapter
 */
public class BankAdapterTest {
    IBankAdapter bankAdapter;
    User user;
    BigDecimal big;
    String cpr;

    /**
     * setup a new bank adapter variable
     */
    @Before
    public void Setup() throws BankAdapterException {
        bankAdapter = new BankAdapter();
        user = new User();
        big = new BigDecimal(99999);
        cpr = "1234561234";
    }

    /**
     * deletes all the accounts from database
     * @throws BankAdapterException
     */
    @After
    public void cleanUp() throws BankAdapterException {
        bankAdapter.deleteAllCreatedAccounts();
    }

    /**
     * tests if account is being created
     * @throws BankAdapterException
     */
    @Test
    public void CreateAccountWithNoException() throws BankAdapterException {
        bankAdapter.createAccount("test", cpr, big);
    }

    /**
     * checks if bank adapter is throwing exception for wrong inputs
     * @throws BankAdapterException
     */
    @Test(expected = BankAdapterException.class)
    public void ChecksIfThereIsThrownAnExceptionWhenGivingWrongInputsToCreateAccount() throws BankAdapterException {
        bankAdapter.createAccount(null, cpr, big);
    }

    /**
     * test for finding an account in database which is already deleted
     * @throws BankAdapterException
     */
    @Test(expected = BankAdapterException.class)
    public void ThrowsAExceptionWhenAccoutIsDeletedAndTryingToFindIt() throws BankAdapterException {
        bankAdapter.createAccount("test", cpr, big);
        bankAdapter.removeAccountByCpr(cpr);
        bankAdapter.getBalanceByCPR(cpr);
    }

    /**
     * checks if it returns the correct balance for an account
     * @throws BankAdapterException
     */
    @Test
    public void ReturnsTheValueOfTheBalanceOfAAccount() throws BankAdapterException {
        bankAdapter.createAccount("Test", cpr, big);
        BigDecimal actual = bankAdapter.getBalanceByCPR(cpr);
        assertEquals(big, actual);
    }

    /**
     * test for checking a balance of an account which does not exists
     * @throws BankAdapterException
     */
    @Test(expected = BankAdapterException.class)
    public void ThrowsAExceptionWhenTryingToGetBalanceFromANonExitingAccount() throws BankAdapterException {
        bankAdapter.getBalanceByCPR(cpr);
    }

    /**
     * checks for money transfer from customer to merchant account
     * @throws BankAdapterException
     */
    @Test
    public void MoneyShouldBeTransferredFromCustomerToMerchant() throws BankAdapterException {
        bankAdapter.createAccount("Test", cpr, big);
        bankAdapter.createAccount("MerchantCpr", "1231231234", big);
        bankAdapter.makeTransaction(cpr, "1231231234", big, "TestComment");
        BigDecimal actual = bankAdapter.getBalanceByCPR(cpr);
        BigDecimal expected = BigDecimal.valueOf(0);
        assertEquals(expected, actual);
    }
}