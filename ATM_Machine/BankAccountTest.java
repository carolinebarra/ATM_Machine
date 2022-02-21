
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Bank Account
 * @author Caroline Barra  University of Brighton
 * 
 */
public class BankAccountTest {

	public static final int EXPECTED_ACC_NUMBER = 10001;
	public static final int EXPECTED_PIN_NUMBER = 1111;
	public static final double EXPECTED_BALANCE = 150.0;
	public static final double EXPECTED_DEPOSIT = 100.0;
	public static final double EXPECTED_WITHDRAW = 100.0;
	private BankAccount account;
	
	@Before
	public void setUp() throws Exception {
		account = new BankAccount(10001,1111,150.0);
	}

	@After
	public void tearDown() throws Exception {
		account = null;
	}
	
	@Test
	public void testBankAccountDetails() {
		assertEquals(EXPECTED_ACC_NUMBER, account.accNumber);
		assertEquals(EXPECTED_PIN_NUMBER, account.pinPsswd);
		assertEquals(EXPECTED_BALANCE, account.balance, 0); // assertEquals(double expected,double actual,double delta)
	}
	@Test
	public void withdraw() {
		assertTrue("enough money to withdraw",account.withdraw(100));
		assertFalse("negative number",account.withdraw(-20));
		assertFalse("dont enough money on the bank account",account.withdraw(300));
	}
	@Test
	public void getBalance() {
		assertEquals(EXPECTED_BALANCE, account.getBalance(),0);
	
	}
	@Test 
	public void deposit() {
		assertTrue("enough money to withdraw", account.deposit(100));
		assertFalse("negative number", account.deposit(-20));
		assertFalse("dont enough money on the bank account", account.deposit(0));
	}
}
