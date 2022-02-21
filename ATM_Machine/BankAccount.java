// BankAccount class
// Method to Withdraw, Deposit, Check the balance and to Change the PIN

/**
 * This class contains the variables for the account number, pin password and balance
 * @author Caroline Barra  University of Brighton
 * 
 */
public class BankAccount {

	public int accNumber = 0;
	public int pinPsswd = 0;
	public double balance = 0; // floating point balance

	public BankAccount() {
	}

	public BankAccount(int accNumber, int pinPsswd, double balance) {
		this.accNumber = accNumber;
		this.pinPsswd = pinPsswd;
		this.balance = balance;

	}

	// Withdraw money from the account. Return true if successful, or
	// false if the amount is negative, or less than the amount in the account
	public boolean withdraw(double amount) {
		if (amount < 0 || balance < amount) {
			return false;
		} else {
			balance = balance - amount; // subtract amount from the balance
			return true;
		}
	}

	// Deposit the amount of money into the bank account. Return true if successful,
	// or false if the amount
	public boolean deposit(double amount) {

		if (amount <= 0) {
			return false;
		} else {
			balance = balance + amount;
			return true;
		}
	}

	public double getBalance() {
		return balance;
	}

	// Get New Pin check if the password has 4 numbers. Return true and new password is successful,
	// or false if bigger than 9999 or smaller than 1000
	public boolean getNewPin(int newPin) {

		if (newPin < 1000 | newPin >= 10000) {
			return false;
		} else {
			pinPsswd = newPin;
			return true;
		}
	}

}
