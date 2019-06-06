package ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.CheckoutEntry;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class CheckoutRecordController {
	@FXML
	private TextField isbnTextField;
	@FXML
	private TableView<Pair<LibraryMember, CheckoutEntry>> checkoutRecordTable;
	@FXML
	private TableColumn<Pair<LibraryMember, CheckoutEntry>, String> copyNumCol;
	@FXML
	private TableColumn<Pair<LibraryMember, CheckoutEntry>, String> isbnCol;
	@FXML
	private TableColumn<Pair<LibraryMember, CheckoutEntry>, String> libraryMemberCol;
	@FXML
	private TableColumn<Pair<LibraryMember, CheckoutEntry>, String> checkoutDateCol;
	@FXML
	private TableColumn<Pair<LibraryMember, CheckoutEntry>, String> dueCol;
	@FXML
	private TableColumn<Pair<LibraryMember, CheckoutEntry>, String> isOverDue;
	@FXML
	private CheckBox showOverDueCheckBox;

	@FXML
	private void initialize() {
		loadData(null);
		initializeView();
	}

	private void initializeView() {
		isbnCol.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getValue().getCheckedoutBookCopy().getBook().getIsbn()));
		copyNumCol.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getValue().getCheckedoutBookCopy().getCopyNum() + ""));
		libraryMemberCol.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getKey().getFirstName() + " " + cellData.getValue().getKey().getLastName()));
		checkoutDateCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getValue().getCheckoutDate().toString()));
		dueCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getValue().getDueDate().toString()));
		isOverDue.setCellValueFactory(cellData -> new SimpleStringProperty(
				new Boolean(cellData.getValue().getValue().getDueDate().before(new Date())).toString()));
	}

	private void loadData(String isbnFilter) {

		DataAccess da = new DataAccessFacade();
		List<LibraryMember> memberList = new ArrayList<LibraryMember>(da.readMemberMap().values());
		List<Pair<LibraryMember, CheckoutEntry>> membersAndCheckoutEntryList = new ArrayList<Pair<LibraryMember, CheckoutEntry>>();
		for (LibraryMember member : memberList) {
			List<CheckoutEntry> checkoutEntries = member.getCheckoutRecord().getCheckoutEntries();
			for (CheckoutEntry checkoutEntry : checkoutEntries) {
				if (isbnFilter == null ||checkoutEntry.getCheckedoutBookCopy().getBook().getIsbn().contains(isbnFilter)) {
					if (showOverDueCheckBox.isSelected()) {
						if (checkoutEntry.getDueDate().before(new Date())) {
							membersAndCheckoutEntryList
									.add(new Pair<LibraryMember, CheckoutEntry>(member, checkoutEntry));
						}
					} else {
						membersAndCheckoutEntryList.add(new Pair<LibraryMember, CheckoutEntry>(member, checkoutEntry));
					}
				}
			}
		}
		ObservableList<Pair<LibraryMember, CheckoutEntry>> membersAndCheckoutEntries = FXCollections
				.observableArrayList(membersAndCheckoutEntryList);
		checkoutRecordTable.setItems(membersAndCheckoutEntries);

	}

	public void OnIsbnTextFieldChanged(ActionEvent event) {
		loadData(isbnTextField.getText());
	}

	public void onChangeOverdueCheckBox(ActionEvent event) {
		loadData(isbnTextField.getText());
	}
}
