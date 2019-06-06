package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import business.CheckoutEntry;
import business.LibraryMember;
import business.SystemController;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MembersController {
	@FXML
	private TableView<LibraryMember> lstMembers;
	@FXML
	private TableColumn<LibraryMember, String> colMemberId;
	@FXML
	private TableColumn<LibraryMember, String> colMemberName;
	@FXML
	private Button btnPrint;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnAdd;
	@FXML
	private TextField txtSearch;

	private SystemController sysController;
	ObservableList<LibraryMember> allMembers;

	@FXML
	private void initialize() {
		// Initialize the Member table with the two columns.
		colMemberId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMemberId()));
		colMemberName.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName()));

		if (Objects.deepEquals(SystemController.currentAuth, Auth.LIBRARIAN)) {
			btnAdd.setVisible(false);
			btnEdit.setVisible(false);
			btnPrint.setVisible(true);
		} else if (Objects.deepEquals(SystemController.currentAuth, Auth.ADMIN)) {
			btnAdd.setVisible(true);
			btnEdit.setVisible(true);
			btnPrint.setVisible(false);
		}

		sysController = new SystemController();

		populateMemberGrid();
	}

	void populateMemberGrid() {
		allMembers = sysController.getAllLibraryMember();

		lstMembers.setItems(allMembers);
	}

	public void searchById(ActionEvent event) {
		if (txtSearch.getText() == null || txtSearch.getText().length() == 0)
			populateMemberGrid();
		else {
			allMembers = FXCollections.observableArrayList();
			try {
				LibraryMember lm = sysController.getMemberById(txtSearch.getText());
				if (lm != null)
					allMembers.add(lm);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lstMembers.setItems(allMembers);
			lstMembers.refresh();
		}
	}

	public void printMembers(ActionEvent event) {
		LibraryMember member = lstMembers.getSelectionModel().getSelectedItem();
		List<CheckoutEntry> checkoutEntries = member.getCheckoutRecord().getCheckoutEntries();
		System.out.println("Checkout records for Member : " + member.getFirstName() + " " + member.getLastName());
		for (CheckoutEntry checkoutEntry : checkoutEntries) {
			System.out.println("Book Title : " + checkoutEntry.getCheckedoutBookCopy().getBook().getTitle()
					+ " | Book Copy # : " + checkoutEntry.getCheckedoutBookCopy().getCopyNum() + " | checkoutDate : "
					+ checkoutEntry.getCheckoutDate() + " | dueDate : " + checkoutEntry.getDueDate());
		}
	}

	public void addMember(ActionEvent event) {
		openAddEditDialog(null, "");
	}

	public void editMember(ActionEvent event) {
		LibraryMember selectedPerson = lstMembers.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			openAddEditDialog(selectedPerson, "Edit Member: " + selectedPerson.getMemberId());
		} else {
			Alert errorAlert = new Alert(AlertType.ERROR);

			errorAlert.setTitle("No Selection");
			errorAlert.setContentText("Please select member from the table.");
			errorAlert.showAndWait();

		}
	}

	private void openAddEditDialog(LibraryMember member, String title) {
		try {
			// Stage primaryStage = (Stage)
			// memberIdLabel.getScene().getWindow();
			Stage stage = new Stage();
			stage.setTitle(title);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/AddEditMember.fxml"));

			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			// Stage dialogStage = new Stage();
			// dialogStage.setTitle("Edit Member");
			stage.initModality(Modality.WINDOW_MODAL);
			// stage.initOwner(primaryStage);
			// Scene scene = new Scene(page);
			// dialogStage.setScene(scene);

			Scene scene = new Scene(page);
			stage.setScene(scene);

			// Set the person into the controller.
			MembersAddEditController controller = loader.getController();
			controller.setDialogStage(stage);
			controller.setIsEditMode(title != "");
			controller.populateControlls(member);

			// Show the dialog and wait until the user closes it
			stage.showAndWait();
			populateMemberGrid();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
