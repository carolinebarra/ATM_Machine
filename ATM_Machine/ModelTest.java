import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ModelTest {
	private Bank b;
	private Model model;
	final String ACCOUNT_NO = "account_no";
	final String PASSWORD = "password";
	private String display1;
	private int number;
	private String state;

	@Before
	public void setUp() throws Exception {
		b = null;
		model = new Model(b);
		model.initialise("welcome");
		 state = model.state;
		 number = model.number;
		 display1 = model.display1;
	}

	@After
	public void tearDown() throws Exception {
		b= null;
		model = null;
	}

	@Test
	public void testInitialise() { 
		assertEquals(ACCOUNT_NO, state);
		assertEquals("welcome", display1);
		assertEquals(0, number);
	}

	@Test
	public void testSetState() {
		model.setState(PASSWORD);
		String newstate = model.state; //update state after changed
		assertEquals(PASSWORD, newstate);
		
	}
}
