import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String args[]) throws Exception {
		// The main method only gets used when launching from the command line
		// launch initialises the system and then calls start
		launch(args);
	}

	public void start(Stage window) throws Exception {
		// set up debugging and print initial debugging message
		Debug.set(true);
		Debug.trace("Main::start");
		Debug.trace("atm running");
		Bank b = new Bank();
		// Create the Model, View and Controller objects
		Model model = new Model(b); // the model needs the Bank object to 'talk to' the bank
		Controller controller = new Controller(model); // the model needs the model object to talk to'
		FXMLLoader controllerLoader = new FXMLLoader(getClass().getResource("View.fxml")); // load view fmxl file
		
		// add some test bank accounts
		b.addBankAccount(10001, 1111, 100);
		b.addBankAccount(10002, 2222, 50);

		// Link them together so they can talk to each other
		// Each one has instances variable for the other two
		model.controller = controller;
		controller.model = model;

		controllerLoader.setController(controller); // connecting the view fmxl with controller
		Parent root = controllerLoader.load();

		Scene scene = new Scene(root); // scene with the main.fxml file, fxml already have the width and height for
										// window
		window.setTitle("ATM Machine"); // title of the window
		window.setScene(scene);
		window.show();
		model.initialise("Welcome to ATM");
		model.display();
		Debug.trace("atm running");

	}

}
