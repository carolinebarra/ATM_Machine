import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for Bank
 * @author Caroline Barra  University of Brighton
 */
public class BankTest {
	private Bank test;
	private BankAccount account;

	@Before
	public void setUp() throws Exception {
		test = new Bank();
		test.addBankAccount(10011, 1234, 57.00);
	}

	@After
	public void tearDown() throws Exception {
		test = null;
		account = null;
	}

	@Test
	public void addTooManyAcc() {
		test.addBankAccount(10011, 1234, 57.00);
		test.addBankAccount(1001, 1234, 100.00);
		test.addBankAccount(111, 1234, 57.00);
		test.addBankAccount(12, 1234, 100.00);
		test.addBankAccount(11, 1234, 57.00);
		test.addBankAccount(12, 1234, 100.00);
		test.addBankAccount(1661, 1234, 57.00);
		test.addBankAccount(1342, 1234, 100.00);
		test.addBankAccount(132, 1234, 100.00);
		assertEquals(10, test.accounts.length);
	}

	@Test
	public void checkBankAcc() {
		assertTrue("account exist", test.checkForBankAcc(10011));
		assertFalse("account doesnt exist", test.checkForBankAcc(10111));
	}

	@Test
	public void checkPassword() {
		assertTrue("correct password and account",test.checkPsswd(10011, 1234));
		assertFalse("wrong password for account", test.checkPsswd(10012, 2));
		assertFalse("wrong account", test.checkPsswd(1111, 333));
	}

	@Test
	public void login() {
		account = new BankAccount(1111, 2222, 57.00);
		test.addBankAccount(account);
		test.account = account;
		assertTrue("logged in",test.loggedIn());
	}

	@Test
	public void withdraw() {
		login();
		assertTrue("you have enough funds", test.account.withdraw(20.00));
		assertFalse("you dont have enough funds", test.account.withdraw(100.00));

	}

	@Test
	public void deposit() {
		login();
		assertTrue("money deposited", test.account.deposit(100.00));
		assertFalse("ammount negative", test.account.deposit(-1.00));
	}

	@Test
	public void getBalance() {
		login();
		assertEquals(57.0, test.account.getBalance(),0);
		assertNotEquals(59.0, test.account.getBalance(), 0);
		assertNotEquals(57.1, test.account.getBalance(), 0);
	}

	@Test
	public void newPassword() {
		login();
		assertTrue("valid password",test.account.getNewPin(1234));
		assertTrue("valid password",test.account.getNewPin(9999));
		assertFalse("invalid password",test.account.getNewPin(14));
		assertFalse("invalid password",test.account.getNewPin(1000004));
	}

	@Test
	public void checkPrevPassword() {
		login();
		assertTrue("correct password",test.prevPsswd(1234));
		assertFalse("wrong password",test.prevPsswd(10012));
		assertFalse("wrong password", test.prevPsswd(1111));
	}

	@Test
	public void logOut() {
		test.account = null;
		assertFalse("Not log in", test.loggedIn());
	}

}
