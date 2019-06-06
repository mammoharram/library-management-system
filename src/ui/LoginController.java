package ui;


import java.io.IOException;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController extends Stage implements LibWindow{
	public static final LoginController INSTANCE = new LoginController();

	@FXML
	private TextField login_id;
	@FXML
	private PasswordField password;
	
	// Reference to the main application.
;
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
	 * Called when the user clicks the new button. Opens a dialog to main manu
	 *
	 */
	@FXML
	private void handlelogin() {
		Start.hideAllWindows();
		ControllerInterface c = new SystemController();
		System.out.println(login_id.getText()+" "+password.getText());
		try {
			c.login(login_id.getText().trim(), password.getText().trim());
			System.out.println(SystemController.currentAuth);
			boolean result=showMainMan();
			System.out.println("login "+result);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			System.out.println("Wrong login details "+e.getMessage());
		}
		
		
	}
	public boolean showMainMan() {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
//			Start.primStage().close();
//			System.out.println(SystemController.successLogin);
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(Start.class.getResource("../ui/MainManu.fxml"));
			
			AnchorPane page = (AnchorPane) loader.load();
			
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			
			dialogStage.setTitle("Main Manu");
			dialogStage.initModality(Modality.WINDOW_MODAL);
		
			dialogStage.initOwner(Start.primStage());
			Scene scene = new Scene(page);
		
			dialogStage.setScene(scene);
			
			// Set the person into the controller.
			MainManuController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			
//			controller.setPerson(person);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void isInitialized(boolean val) {
		// TODO Auto-generated method stub
		
	}
	

}
