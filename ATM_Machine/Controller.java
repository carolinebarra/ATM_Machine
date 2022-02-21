import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * This class contains methods that allow you to work with the data from Bank
 * @author Caroline Barra  University of Brighton
 * 
 */
public class Controller {

    @FXML
    private Label title;

    @FXML
    private GridPane numberPad; // numberPad

    @FXML
    private GridPane actionPad;

    @FXML
    private Button deposit;

    @FXML
    private Button withdraw;

    @FXML
    private Button balance;

    @FXML
    private Button change_pin;

    @FXML
    private TextField message; // Input area

    @FXML
    private ScrollPane message_area;

    /**
     * 
     */
    @FXML
    private TextArea reply; // message board

	public Model model;

	public Controller(Model model) {
		Debug.trace("Controller::<constructor>");
		this.model = model; //add the model to controller 
	} 
	
    @FXML
    void buttonClicked(ActionEvent event) {
		Button b = ((Button) event.getSource());
		if (model != null) {
			String action = b.getText(); // get the button label
			Debug.trace("Controller::process: action = " + action);
			switch (action) {
			case "CLEAR":
				model.processClear();
				break;
			case "ENTER":
				model.processEnter();
				break;
			case "Withdraw":
				model.processWithdraw();
				break;
			case "Deposit":
				model.processDeposit();
				break;
			case "Balance":
				model.processBalance();
				break;
			case "CANCEL":
				model.processCancel();
				break;
			case "Change PIN":
				model.processChangePin();
				break;
			default:
				model.processUnknownKey(action);
				break;
			}
		}

    }

    /** Number PAD buttons clicked
     * @param event
     */
    @FXML
    void processNumPadButton(ActionEvent event) {
		Button b = ((Button) event.getSource());
		if (model != null) {
			String label = b.getText(); // get the button label
			switch(label) {
			case "00":
				model.processDoubleZero(label); // process the double zero
				break;		
			case "000":
					model.processTripleZero(label); // process the double zero
					break;
				default:
					model.processNumber(label); //the rest of the numbers
					break;
			}
		}

    }


    @FXML
    void initialize() {
    }

	public void update(Controller c) {
		
		if (model != null) {
			Debug.trace("View::update");
			String message2 = model.display1; // get the new message1 from the model
			message.setText(message2); // add it as text of GUI control output1
			String message3 = model.display2; // get the new message2 from the model
			reply.setText(message3); // add it as text of GUI control output2
		}
		
	}

}



