package ui;

import java.io.IOException;
import java.util.Objects;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;
import dataaccess.Auth;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

public class MainManuController {
	@FXML
	private Button checkoutButton;
	@FXML
	private Button checkoutRecordButton;
	@FXML
	private Button membersButton;
	@FXML
	private Button booksButton;

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
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		if (Objects.deepEquals(SystemController.currentAuth, Auth.LIBRARIAN)) {
			membersButton.setVisible(false);
			booksButton.setVisible(false);
		} else if (Objects.deepEquals(SystemController.currentAuth, Auth.ADMIN)) {
			checkoutButton.setVisible(false);
			checkoutRecordButton.setVisible(false);
		}

	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to check out
	 *
	 */
	@FXML
	private void onPressCheckOutButton() {

		System.out.println("Checking out book ");

	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to add book
	 *
	 */
	@FXML
	private void onPressBooksButton() {

		Start.showBooksListWindow(this);

	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to add book
	 *
	 */
	@FXML
	private void onPressMembersButton() {

		System.out.println("Checking Edit book ");

	}

	/**
	 * Called when the user clicks the new button. Opens a dialog check out record
	 *
	 */
	@FXML
	private void onPressCheckOutRecordButton() {

		System.out.println("Checking Edit book ");

	}

}
