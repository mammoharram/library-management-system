package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import business.LibraryMember;
import business.SystemController;
import javafx.beans.property.SimpleStringProperty;
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

	@FXML
	private void initialize(){
		// Initialize the Member table with the two columns.
		colMemberId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMemberId()));
		colMemberName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()+" "+cellData.getValue().getLastName()));

		sysController = new SystemController();

		populateMemberGrid();
	}
	void populateMemberGrid() {
		ObservableList<LibraryMember> allMembers = sysController.getAllLibraryMember();

		lstMembers.setItems(allMembers);
	}
	public void printMembers(ActionEvent event) {

	}

	public void addMember(ActionEvent event) {
		openAddEditDialog(new LibraryMember(), "");
	}

	public void editMember(ActionEvent event) {
		LibraryMember selectedPerson = lstMembers.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			openAddEditDialog(selectedPerson, "Edit Member: " + selectedPerson.getMemberId());
		}
		else{
			Alert errorAlert = new Alert(AlertType.ERROR);

			errorAlert.setTitle("No Selection");
			errorAlert.setContentText("Please select member from the table.");
			errorAlert.showAndWait();

		}
	}

	private void openAddEditDialog(LibraryMember member, String title){
		try {
			//Stage primaryStage = (Stage) memberIdLabel.getScene().getWindow();
			Stage stage = new Stage();
			stage.setTitle(title);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/AddEditMember.fxml"));

			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			//Stage dialogStage = new Stage();
			//dialogStage.setTitle("Edit Member");
			stage.initModality(Modality.WINDOW_MODAL);
			//stage.initOwner(primaryStage);
			//Scene scene = new Scene(page);
			//dialogStage.setScene(scene);

			Scene scene = new Scene(page);
			stage.setScene(scene);

			// Set the person into the controller.
			MembersAddEditController controller = loader.getController();
			controller.setDialogStage(stage);
			controller.populateControlls(member);

			// Show the dialog and wait until the user closes it
			stage.showAndWait();
			populateMemberGrid();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		/*try {
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/ui/MembersList.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.showAndWait();
		}
		catch(Exception e) {
			e.printStackTrace();
		}*/
	}
}
