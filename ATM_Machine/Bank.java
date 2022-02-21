//Bank class - hold the bank accounts,
// a current account that we are logged in to.

/**
 * Bank class hold the bank accounts, Check for current account that we logged
 * in to. Checking for password and if the account exists.
 * @author Caroline Barra  University of Brighton
 *
 */

public class Bank {
	int maxAccounts = 10; // maximum number of accounts that the bank can holds
	int numAccounts = 0; // current number of accounts
	BankAccount[] accounts = new BankAccount[maxAccounts]; // array for accounts
	BankAccount account = null; // currently logged in account ("null" if no on is logged in)

	public Bank() {
		Debug.trace("Bank::<contructor>");
	}

	// a method to create new BankAccounts - this is known as a 'factory method'
	// is a more flexible way to do it than just using the 'new' keyword directly
	/**
	 * @param accNumber BankAccount number
	 * @param pinPsswd  BankAccount password
	 * @param balance   BankAccount balance
	 * @return
	 */
	public BankAccount makeBankAccount(int accNumber, int pinPsswd, double balance) {
		return new BankAccount(accNumber, pinPsswd, balance);
	}

	// a method to add a new bank account to the bank - it returns true it is
	// succeeds
	// or false if the bank is full

	/**
	 * @param a Account
	 * @return Add the bank account to bank If number of accounts is less then 10 or
	 *         false if the bank is full
	 */
	public boolean addBankAccount(BankAccount a) {
		if (numAccounts < maxAccounts) {
			accounts[numAccounts] = a;

			numAccounts++;
			Debug.trace("Bank::addBankAccount " + a.accNumber + " " + a.pinPsswd + " £" + a.balance);
			return true;
		} else {
			Debug.trace("Bank:: addBankAccount can't add more than 10 banks accounts");
			return false;
		}
	}

	// a variant of addBankAccount which makes the account and adds it all in one
	// go.
	// Using the same name for this method is called 'method overloading' - two
	// methods
	// can have the same name if they take different argument combinations
	public boolean addBankAccount(int accNumber, int pinPsswd, double balance) {
		return addBankAccount(makeBankAccount(accNumber, pinPsswd, balance));
	}

	// Check whether if the bank account and password correspond to an actual bank
	// account
	// if so login to it(by setting 'account' to it)
	// and return true. Otherwise, reset the account to null
	// Check if the account exist
	/**
	 * @param newAccNumber input for the user
	 * @return if the input match if one of the bank account it exists. if return
	 *         false the input doesn't match
	 */
	public boolean checkForBankAcc(int newAccNumber) {
		for (BankAccount b : accounts) {
			if (b.accNumber == newAccNumber) {
				Debug.trace("Bank::checkForAccount accNumber= " + newAccNumber);
				account = b;
				return true;
			} else if (b.accNumber != newAccNumber) {
				// the account is not found
				Debug.trace("Bank:::checkForAccount no account found for accNumber = " + newAccNumber);
				return false;
			}
		}
		return true;
	}

	// Check if the password is correct
	/**
	 * @param newAccNumber input for the user for account number
	 * @param newPinPsswd  input for the user for password
	 * @return if the input for acc and psswd match with one of the bank account
	 *         return that bank account if return false if the password isn't
	 *         correct
	 * 
	 */
	public boolean checkPsswd(int newAccNumber, int newPinPsswd) {
		for (BankAccount b : accounts) {
			if (b.accNumber == newAccNumber && b.pinPsswd == newPinPsswd) {
				Debug.trace("Bank::checkForPassword = " + newPinPsswd);
				account = b;
				return true;
			} else {
				// the account is not found
				Debug.trace("Bank::checkForPassword wrong Password for accNumber = " + newAccNumber
						+ " wrong password: " + newPinPsswd);
				return false;
			}
		}
		return true;
	}
	
	/** Check if the password is correct to create new one
	 * @param prevPinPsswd
	 * @return true value the passwords matches otherwise returns false
	 */
	public boolean prevPsswd(int prevPinPsswd) {
		for (BankAccount b : accounts) {
			if (b.pinPsswd == prevPinPsswd) {
				Debug.trace("Bank::prevPassword correct= " + prevPinPsswd);
				account = b;
				return true;
			} else if (b.pinPsswd != prevPinPsswd) {
				// previous password dont match
				Debug.trace("Bank:::prevPassowrd wrong = " + prevPinPsswd);
				return false;
			}
		}
		return true;
	}

	// Reset the bank to a 'logged out' state
	/**
	 * Reset the bank to a 'logged out' state account is set back to null
	 */
	public void logout() {
		if (loggedIn()) {
			Debug.trace("Bank::logout: logging out, accNumber = " + account.accNumber);
			account = null;
		}
	}

	// test whether the bank is logged in to an account or not
	/**
	 * @return if true the account is logged in if return false the account is null
	 */
	public boolean loggedIn() {
		if (account == null) {
			return false;
		} else {
			Debug.trace("Bank::login: loggin in , accNumber = " + account.accNumber);
			return true;
		}
	}

	// try to deposit money into the account
	// (by calling the deposit method on the BankAccount object)
	/**
	 * @param amount user input for amount to deposit to bank account
	 * @return if the account is logged in, deposit the amount on the account if
	 *         false do nothing
	 */
	public boolean deposit(double amount) {
		if (loggedIn()) {
			return account.deposit(amount);
		} else {
			return false;
		}
	}

	// same method as deposit but for withdraw
	/**
	 * @param amount user input for amount to withdraw to bank account
	 * @return if the account is logged in, withdraw the amount on the account if
	 *         false do nothing
	 */
	public boolean withdraw(double amount) {
		if (loggedIn()) {
			return account.withdraw(amount);
		} else {
			return false;
		}
	}

	// get the account balance
	/**
	 * @return if the account is logged in, display the balance of the account if
	 *         false do nothing
	 */
	public double getBalance() {
		if (loggedIn()) {
			return account.getBalance();
		} else {
			return -1; // use -1 as an indicator of an error
		}
	}

	/**
	 * @param newPin user input for new password
	 * @return if the account is logged in, change old password to the user new
	 *         password
	 */
	public boolean getNewPin(int newPin) {
		if (loggedIn()) {
			return account.getNewPin(newPin);
		} else {
			return false;
		}
	}
}
