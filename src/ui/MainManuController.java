package ui;



import java.util.Objects;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
import dataaccess.Auth;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public class MainManuController {
	@FXML
	private Button checkout;
	@FXML
	private Button checkoutRecord;
	@FXML
	private Button addMember;
	@FXML
	private Button addBook;
	@FXML
	private Button editMember;
	@FXML
	private ToggleButton on;

	private Stage dialogStage;
	private boolean okClicked = false;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	/**
	 * Returns true if the user clicked OK, false otherwise.
	 *
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}
	
	
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		okClicked=true;
		System.err.println(Objects.deepEquals(SystemController.currentAuth,Auth.LIBRARIAN));
//		BooleanBinding disable = on.selectedProperty().not();
		if(Objects.deepEquals(SystemController.currentAuth,Auth.LIBRARIAN)) {
//			addMember.setDisable(true);
//			addBook.setDisable(true);
//			editMember.setDisable(true);
			System.out.println("this liberian");
		}else if(Objects.deepEquals(SystemController.currentAuth,Auth.ADMIN)) {
//			checkout.setDisable(true);
//			checkoutRecord.setDisable(true);
			System.out.println("this admin");
		}


	}
	/**
	 * Called when the user clicks the new button. Opens a dialog to check out 
	 *
	 */
	@FXML
	private void handleCheckout() {
		
			System.out.println("Checking out book ");
	
		
	}
	/**
	 * Called when the user clicks the new button. Opens a dialog to add book 
	 *
	 */
	@FXML
	private void handleAddBook() {
		
			System.out.println("Adding book ");
	
		
	}
	/**
	 * Called when the user clicks the new button. Opens a dialog to add book 
	 *
	 */
	@FXML
	private void handleAddMember() {
		
			System.out.println("Checking Edit book ");
	
		
	}
	/**
	 * Called when the user clicks the new button. Opens a dialog to edit member 
	 *
	 */
	@FXML
	private void handleEditMember() {
		
			System.out.println("Checking editting member");
	
		
	}
	/**
	 * Called when the user clicks the new button. Opens a dialog check out record 
	 *
	 */
	@FXML
	private void handleCheckOutRecord() {
		
			System.out.println("Checking Edit book ");
	
		
	}

}
