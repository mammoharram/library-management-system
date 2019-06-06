package ui;

import java.util.ArrayList;
import java.util.Objects;

import business.Address;
import business.LibraryMember;
import business.SystemController;
import dataaccess.Auth;
import dataaccess.DataAccessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class MembersAddEditController {

	@FXML
	private TextField txtMemberId;

	@FXML
	private TextField txtMemberFirstName;

	@FXML
	private TextField txtMemberLastName;

	@FXML
	private TextField txtMemberPhone;

	@FXML
	private TextField txtMemberStreet;

	@FXML
	private TextField txtMemberCity;

	@FXML
	private TextField txtMemberZip;

	@FXML
	private TextField txtMemberState;

	@FXML
	private Button btnSave;

	@FXML
	private void initialize() {
	}

	private LibraryMember member;
	private Stage myStage;
	SystemController sysController =new SystemController();

	public void populateControlls(LibraryMember lMember){
		member = lMember;

		txtMemberId.setText(member.getMemberId());
		txtMemberFirstName.setText(member.getFirstName());
		txtMemberLastName.setText(member.getLastName());
		txtMemberPhone.setText(member.getTelephone());

		if(member.getAddress() != null)
		{
			txtMemberStreet.setText(member.getAddress().getStreet());
			txtMemberZip.setText(member.getAddress().getZip());
			txtMemberCity.setText(member.getAddress().getCity());
			txtMemberState.setText(member.getAddress().getState());
		}

	}
	public void setDialogStage(Stage stage) {
		this.myStage = stage;
	}
	@FXML
	void btnSave_Clicked(ActionEvent event) {
		if (!isInputValid())
			return;
		Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to save?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES)
		   {
				Address a=new Address(txtMemberStreet.getText(),
						txtMemberCity.getText(),
						txtMemberState.getText(),
						txtMemberZip.getText());
				this.member.setMemberId(txtMemberId.getText());
				this.member.setFirstName(txtMemberFirstName.getText());
				this.member.setLastName(txtMemberLastName.getText());
				this.member.setTelephone(txtMemberPhone.getText());
				this.member.setAddress(a);

				try {
					sysController.addMember(this.member);
				} catch (Exception e) {
					e.printStackTrace();
				}
				myStage.close();
			}
	}

	private boolean isInputValid() {
		String errorMessage = "";

		if (txtMemberId.getText() == null || txtMemberId.getText().length() == 0 || sysController.checkMemberIdExist(txtMemberId.getText())) {
			errorMessage += "Invalid member ID!\n";
		}

		if (txtMemberFirstName.getText() == null || txtMemberFirstName.getText().length() == 0) {
			errorMessage += "Invalid first name!\n";
		}

		if (txtMemberLastName.getText() == null || txtMemberLastName.getText().length() == 0) {
			errorMessage += "Invalid last name!\n";
		}
		if (txtMemberPhone.getText() == null || txtMemberPhone.getText().length() == 0) {
			errorMessage += "Invalid phone!\n";
		}
		if (txtMemberStreet.getText() == null || txtMemberStreet.getText().length() == 0) {
			errorMessage += "Invalid street!\n";
		}

		if (txtMemberZip.getText() == null || txtMemberZip.getText().length() == 0) {
			errorMessage += "Invalid zip code!\n";
		}

		if (txtMemberCity.getText() == null || txtMemberCity.getText().length() == 0) {
			errorMessage += "Invalid city!\n";
		}

		if (txtMemberState.getText() == null || txtMemberState.getText().length() == 0) {
			errorMessage += "Invalid state!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid Fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

}