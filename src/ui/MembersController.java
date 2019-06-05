package ui;

import java.util.ArrayList;
import java.util.List;

import business.LibraryMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MembersController {

	/*public MembersController (){
		List<LibraryMember> allMembers = new ArrayList<LibraryMember>();

		lstMembers.getItems().addAll(allMembers);
	}*/

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

	public void printMembers(ActionEvent event) {

	}

	public void addMember(ActionEvent event) {
		try {
			//BorderPane root = new BorderPane();
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/ui/AddEditMember.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void editMember(ActionEvent event) {

	}
}
