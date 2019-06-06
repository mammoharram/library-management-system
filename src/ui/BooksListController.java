package ui;

import java.util.ArrayList;
import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BooksListController {

	@FXML
	private TextField isbnTextField;
	@FXML
	private TableView<Book> booksTable;
	@FXML
	private TableColumn<Book, String> titleCol;
	@FXML
	private TableColumn<Book, String> isbnCol;
	@FXML
	private TableColumn<Book, String> authorsCol;
	@FXML
	private TableColumn<Book, String> numOfCopysCol;
	@FXML
	private TableColumn<Book, String> availabilityCol;
	@FXML
	private Button addCopyButton;

	private Book selectedBook;

	@FXML
	private void initialize() {
		loadBooks(null);
		initilizeView();
	}

	private void loadBooks(String isbnFilter) {
		DataAccess da = new DataAccessFacade();
		List<Book> booksList = new ArrayList<Book>(da.readBooksMap().values());
		ObservableList<Book> books = FXCollections.observableArrayList(booksList);
		books = books.filtered(book -> isbnFilter == null || book.getIsbn().contains(isbnFilter));
		booksTable.setItems(books);
	}

	public void OnIsbnTextFieldChanged(ActionEvent event) {
		loadBooks(isbnTextField.getText());
	}

	public void onAddCopyAction(ActionEvent event) {
		DataAccess da = new DataAccessFacade();
		selectedBook.addCopy();
		da.saveBook(selectedBook);
		loadBooks(isbnTextField.getText());
		booksTable.refresh();
	}

	public void initilizeView() {
		titleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
		isbnCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));
		authorsCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthorsNames()));
		availabilityCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(new Boolean(cellData.getValue().isAvailable()).toString()));
		numOfCopysCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(new Integer(cellData.getValue().getNumCopies()).toString()));

		booksTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSelectedBook(newValue));
		booksTable.refresh();
		addCopyButton.setDisable(true);
	}

	private void setSelectedBook(Book book) {
		addCopyButton.setDisable(false);
		selectedBook = book;
	}
}
