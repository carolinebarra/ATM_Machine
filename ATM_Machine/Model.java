public class Model{


	// the ATM model is always in one of three states - waiting for an account
	// number,
	// waiting for a password, or logged in and processing account requests.
	// We use string values to represent each state:
	// (the word 'final' tells Java we won't ever change the values of these
	// variables)
	final String ACCOUNT_NO = "account_no";
	final String CHANGE_PIN ="changepin";
	final String NEW_PIN = "newpin";
	final String PASSWORD = "password";
	final String PREV_PASSWORD = "prevpassword";
	final String LOGGED_IN = "logged_in";
	// variables representing the ATM model
	String state = ACCOUNT_NO; // the state it is currently in
	int number = 0; // current number displayed in GUI (as a number, not a string)
	Bank bank = null; // The ATM talks to a bank, represented by the Bank object.
	int accNumber = -1; // Account number typed in
	int accPasswd = -1; // Password typed in
	int newPin = -1; // New Password
	// These three are what are shown on the View display
	String display1 = null; // The contents of the Message 1 box (a single line)
	String display2 = null; // The contents of the Message 2 box (may be multiple lines)

	public Controller controller;


	public Model(Bank b) {
		// TODO Auto-generated constructor stub
		Debug.trace("Model::<constructor>");
		bank = b;
	}
	// Initialising the ATM (or resetting after an error or logout)
	// set state to ACCOUNT_NO, number to zero, and display message
	// provided as argument and standard instruction message
	public void initialise(String welcome) {
		setState(ACCOUNT_NO);
		number = 0;
		display1 = welcome;
		display2 = "Enter your account number\n" + "Followed by \"Enter\"";
	}

	// use this method to change state - mainly so we print a debugging message
	// whenever
	// the state changes
	public void setState(String newState) {
		if (!state.equals(newState)) {
			String oldState = state;
			state = newState;
			Debug.trace("Model::setState: changed state from " + oldState + " to " + newState);
		}
	}

	public void processNumber(String label) {
		// TODO Auto-generated method stub
		// a little magic to turn the first char of the label into an int
		// and update the number variable with it
		Debug.trace("View::NumberPad: label = " + label);
		char c = label.charAt(0);
		number = number * 10+ c - '0'; // Build number
		// show the new number in the display
		display1 = "" + number;
		display(); // update the GUI
				
	}

	
	
	public void processDoubleZero(String label) {
		Debug.trace("View::Double 00: label = " + label);
		char c = label.charAt(0);
		number = number * 100+ c - '0'; // if the number 
		// show the new number in the display
		display1 = "" + number;
		display(); // update the GUI
		
	}

	public void processTripleZero(String label) {
		Debug.trace("View::Double 00: label = " + label);
		char c = label.charAt(0);
		number = number * 1000+c - '0'; // if the number 
		// show the new number in the display
		display1 = "" + number;
		display(); // update the GUI
		}
			
	// These methods are called by the Controller to change the Model
	// when particular buttons are pressed on the GUI
	// process the Clear button - reset the number (and number display string)
	public void processClear() {
		// clear the number stored in the model
		number = 0;
		display1 = "";
		display(); // update the GUI
	}

	// process the Enter button
	// this is the most complex operation - the Enter key causes the ATM to change
	// state
	// from account number, to password, to logged_in and back to account number
	// (when you log out)
	public void processEnter() {
		// Enter was pressed - what we do depends what state the ATM is already in
		switch (state) {
		case ACCOUNT_NO:
			// we were waiting for a complete account number - save the number we have
			// reset the tyed in number to 0 and change to the state where we are expecting
			// a password
			accNumber = number;
			number = 0;
			display1 = "";					
		setState(PASSWORD);
			display2 = "Now enter your password\n" + "Followed by \"ENTER\"";
			 if(bank.checkForBankAcc(accNumber)) {
					setState(PASSWORD);
					display2 = "Now enter your password\n" + "Followed by \"ENTER\"";
				 } else {

					 setState(ACCOUNT_NO);
						initialise("Unknown account");
						display2 ="This bank account doesn't exist.\n"+"Try again.";
						// go back to the log in state
				 }

			break;
		case PASSWORD:
			// we were waiting for a password - save the number we have as the password
			// and then cotanct the bank with accumber and accPasswd to try and login to
			// an account
			accPasswd = number;
			number = 0;
			display1 = "";
			 Debug.trace("Bank::checkForPassword = " + accNumber);
			// now check the account/password combination. If it's ok go into the LOGGED_IN
			// state, otherwise go back to the start (by re-initialsing)
			if (bank.checkPsswd(accNumber, accPasswd)) {
				
				setState(LOGGED_IN);
				display2 = "Accepted\n" + "Now enter the transaction you require";
			} else {
				 Debug.trace("Bank::checkForAccount accNumber= " + number);
			// if the password or account doesnt match, it gives another chance to try
				initialise("Wrong password");
				setState(PASSWORD);
				display2="Try to enter your password again\n"+"Followed by \"ENTER\"";
			}
			break;
		case PREV_PASSWORD:
			display1="";
			accPasswd = number;
			number = 0;
			Debug.trace("Bank::checkForCurrentPassword = " + accNumber);
			if (bank.prevPsswd(accPasswd)) {
				setState(CHANGE_PIN);
				display2="Change PIN \n" + "Add 4 numbers then press Enter or \n"+"Press Cancel to cancel action";
				 Debug.trace("Bank::checkForAccount PrevPassword= " + accPasswd );
			} else {
				 Debug.trace("Bank::checkForAccount accNumber= " + number);
			// if the password or account doesnt match, it gives another chance to try
				initialise("Wrong password");
				setState(PREV_PASSWORD);
				display2="Try to enter your password again\n"+"Followed by \"ENTER\"";
			}
			break;
		case CHANGE_PIN:
			display1="";
			newPin = number;
			number =0;
            if(bank.getNewPin(newPin)) {
            bank.getNewPin(newPin);
            setState(LOGGED_IN);
            Debug.trace("New password:" +newPin + " for accNumber="+ accNumber);
            } 
		case NEW_PIN:
			if(bank.getNewPin(newPin)) {
				display1="Valid Password";
			display2="New password updated.";
			setState(LOGGED_IN);
			}
			else {
				initialise("Invalid password");
				setState(CHANGE_PIN);
				display2="Create your new password with 4 number \n"+"Followed by \"ENTER\"";
	            Debug.trace("invalid new password:" +newPin + " for accNumber="+ accNumber);
			}


			break;
		case LOGGED_IN:
		default:
			// do nothing in any other state (ie logged in)
		}
		display(); // update the GUI
	}

	// Withdraw button - check we are logged in and if so try and withdraw some
	// money from
	// the bank (number is the amount showing in the interface display)
	public void processWithdraw() {
		if (state.equals(LOGGED_IN)) {
			if (bank.withdraw(number)) {
				display2 = "Withdrawn:£" + number;
			} else {
				display2 = "You do not have sufficient funds";
			}
			number = 0;
			display1 = "";
		} else {
			initialise("You are not logged in");
		}
		display(); // update the GUI
	}

	// Deposit button - check we are logged in and if so try and deposit some money
	// into
	// the bank (number is the amount showing in the interface display)
	public void processDeposit() {
		if (state.equals(LOGGED_IN)) {
			bank.deposit(number);
			display1 = "";
			display2 = "Deposited:£" + number;
			number = 0;
		} else {
			initialise("You are not logged in");
		}
		display(); // update the GUI
	}

	// Balance button - check we are logged in and if so access the current balance
	// and display it
	public void processBalance() {
		if (state.equals(LOGGED_IN)) {
			number = 0;
			display2 = "Your balance is:£" + bank.getBalance();
		} else {
			initialise("You are not logged in");
		}
		display(); // update the GUI
	}

	//Cancel button - check we are logged in and if so log out
	public void processCancel() {
		if (state.equals(LOGGED_IN)) {
			// go back to the log in state
			setState(ACCOUNT_NO);
			number = 0;
			initialise("You have been log out");
			display2 = "Welcome: Enter your account number";
			bank.logout();
		} else if(state.equals(CHANGE_PIN)| state.equals(PREV_PASSWORD)) {
			setState(LOGGED_IN);
			display2="New password cancelled.";
			
		}
		else {
			initialise("You are not logged in");
		}
		
		display(); // update the GUI
	}


	void display() {
		Debug.trace("Model::display");
		controller.update(controller); // connect to controller so it can update
		}
	
	
	public void processUnknownKey(String action) {
	
		// unknown button, or invalid for this state - reset everything
		Debug.trace("Model::processUnknownKey: unknown button \"" + action + "\", re-initialising");
		// go back to initial state
		initialise("Invalid command");
		display();
	}
	public void processChangePin() {
		// TODO Auto-generated method stub
		if(state.equals(LOGGED_IN)) {
			display1="";
			display2= "Please press the current password \n"+"then press Enter";
			setState(PREV_PASSWORD);
		
		} else {
			initialise("You are not logged in");
		}
		display(); // update the GUI
		
	} 
	
	
	
}
