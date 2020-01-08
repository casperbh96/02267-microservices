package dtu.ws.fastmoney;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dtu.ws.fastmoney.persistency.InMemoryRepository;
import dtu.ws.fastmoney.persistency.Repository;

/**
 * Class Bank represents bank services, with accounts and the transfer of money between 
 * accounts. A bank is created with <code>new Bank()</code>. The default persistence layer
 * uses an in-memory database. An alternative persistence layer can be set using <code>setRepository</code>.
 * 
 * All accesses to the bank, that go through the same object, use the same 
 * database.
 */
public class Bank {

	/* Default database. Can/Should be set to the production
	 * database.
	 */
	Repository pl = new InMemoryRepository();

	/**
	 * Create a new account with a given balance for a given user. 
	 * The method returns a unique account number.
	 * 
	 * @param user for which the account should be created
	 * @param initialBalance the initial balance
	 * @return the account number
	 * @throws BankServiceException when the initial balance is negative or the user has null or empty fields for CPR number, first name, or last name
	 */
	public String createAccountWithBalance(User user, BigDecimal initialBalance) throws BankServiceException {
		if (initialBalance.compareTo(new BigDecimal(0)) == -1) {
			throw new BankServiceException("Initial balance must be 0 or positive");
		}
		if (user.getCprNumber() == null || user.getCprNumber().isEmpty()) {
			throw new BankServiceException("Missing CPR number");
		}
		if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
			throw new BankServiceException("Missing first name");
		}
		if (user.getLastName() == null || user.getLastName().isEmpty()) {
			throw new BankServiceException("Missing last name");
		}
		if (findAccountByCprNumber(user.getCprNumber()).isPresent()) {
			throw new BankServiceException("Account already exists");
		}

		synchronized (this) {
			Account account = new Account();
			account.setUser(user);
			account.setId(UUID.randomUUID().toString());
			account.setBalance(initialBalance);
			pl.executeInTransaction(() -> {pl.createAccount(account);});
			return account.getId();
		}
	}

	private Optional<Account> findAccountByCprNumber(String cprNumber) {
		Account a = pl.readAccountByCpr(cprNumber);
		if (a == null) {
			return Optional.empty();
		} else {
			return Optional.of(a);
		}
	}

	/**
	 * Delete an existing account.
	 * @param account is the account number to be deleted
	 * @throws BankServiceException when the account doe snot exist
	 */
	synchronized public void retireAccount(String account) throws BankServiceException {
		if (pl.readAccount(account) != null) {
			pl.executeInTransaction(() -> {pl.deleteAccount(account);});
		} else {
			throw new BankServiceException("Account does not exist");
		}
	}

	/**	
	 * Transfer money from one account to another
	 * @param accountFrom the debitor
	 * @param accountTo the creditor
	 * @param amount the amount to transfer
	 * @param description the reason for the transaction
	 * @throws BankServiceException when amount is negative, or the debitor and creditor do not exists or the description is missing
	 */
	public void transferMoneyFromTo(String accountFrom, String accountTo, BigDecimal amount, String description)
			throws BankServiceException {
		if (!hasAccount(accountFrom)) {
			throw new BankServiceException("Debtor account does not exist");
		}
		if (!hasAccount(accountTo)) {
			throw new BankServiceException("Creditor account does not exist");
		}
		if (amount.compareTo(new BigDecimal(0)) == -1) {
			throw new BankServiceException("Amount must be positive");
		}
		if (description == null || description.isEmpty()) {
			throw new BankServiceException("Description missing");
		}
		synchronized (this) {
			pl.executeInTransaction(()-> {
				Account f = pl.readAccount(accountFrom);
				Account t = pl.readAccount(accountTo);
				if (f.getBalance().subtract(amount).compareTo(new BigDecimal(0)) == -1) {
					throw new BankServiceException("Debtor balance will be negative");
				}
				Date time = new Date();
				f.sendMoney(pl, amount, description, t, time);
			});
		}
	}

	private boolean hasAccount(String accountId) {
		return pl.readAccount(accountId) != null;
	}

	/**
	 * Return the account data for a givcen account number.
	 * @param account the account number
	 * @return the account data
	 * @throws BankServiceException when the account number does not exist
	 */
	public Account getAccount(String account) throws BankServiceException {
		if (!hasAccount(account)) {
			throw new BankServiceException("Account does not exist");
		}
		return pl.readAccount(account);
	}

	/**
	 * Return an account data for a user with a givcen CpR number
	 * @param cpr CPR number
	 * @return the account data
	 * @throws BankServiceException when the bank does not have a user with the given CPR number
	 */
	public Account getAccountByCprNumber(String cpr) throws BankServiceException {
		Optional<Account> a = findAccountByCprNumber(cpr);
		if (a.isPresent()) {
			return a.get();
		} else {
			throw new BankServiceException("Account does not exist");
		}
	}

	/**
	 * @return the list of accounts of the bank.
	 */
	public AccountInfo[] getAccounts() {
		List<AccountInfo> as = new ArrayList<>();
		for (Account a : pl.readAccounts()) {
			AccountInfo ai = new AccountInfo();
			ai.setUser(a.getUser());
			ai.setAccountId(a.getId());
			as.add(ai);
		}
		return as.toArray(new AccountInfo[0]);
	}

	/**
	 * Set the database used by the bank. The default database is an in-memory database.
	 * @param repo the database the bank should be using.
	 */
	public void setRepository(Repository repo) {
		pl = repo;
	}
}
