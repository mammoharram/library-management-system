package ui;

import business.Author;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class BookController {

	@FXML
	private TextField txtTitle;
	@FXML
	private TextField txtISBN;
	@FXML
	private ComboBox<Author> cmbAuthors;
	@FXML
	private Button btnAddAuthorToList;
	@FXML
	private ListView<Author> lstAuther;
	@FXML
	private Button btnSave;

	public void AddAuthor(ActionEvent event){

	}

	public void SaveBook(ActionEvent event) {

	}
}
