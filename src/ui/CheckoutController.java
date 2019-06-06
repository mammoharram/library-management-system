package ui;

import java.util.Date;
import java.util.HashMap;

import business.Book;
import business.BookCopy;
import business.CheckoutEntry;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CheckoutController {

	private static final String BOOK_NOT_AVAILABLE = "Book not available";
	private static final String MEMBER_NOT_FOUND = "Member not found";
	private static final String BOOK_NOT_FOUND = "book not found";
	@FXML
	private TextField memberIdTextField;
	@FXML
	private TextField isbnTextField;
	@FXML
	private Label errorLabel;
	@FXML
	private Button checkoutButton;

	public void onPressCheckoutButton() {

		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> members = da.readMemberMap();
		HashMap<String, Book> books = da.readBooksMap();
		if (members.containsKey(memberIdTextField.getText())) {
			LibraryMember member = members.get(memberIdTextField.getText());
			if (books.containsKey(isbnTextField.getText())) {
				Book book = books.get(isbnTextField.getText());
				if (book.isAvailable()) {
					BookCopy bookCopy = book.getNextAvailableCopy();
					bookCopy.changeAvailability();
					book.updateCopies(bookCopy);
					Date checkoutDate = new Date();
					Date dueDate = new Date(checkoutDate.getTime() + book.getMaxCheckoutLength() * 24 * 60 * 60 * 1000);
					CheckoutEntry checkoutEntry = new CheckoutEntry(new Date(), dueDate, bookCopy);
					member.getCheckoutRecord().getCheckoutEntries().add(checkoutEntry);
					da.saveBook(book);
					da.saveNewMember(member);
					errorLabel.setVisible(false);
					Start.hideAllWindows();
				} else {
					showError(BOOK_NOT_AVAILABLE);

				}
			} else {
				showError(BOOK_NOT_FOUND);
			}
		} else {
			showError(MEMBER_NOT_FOUND);
		}

	}

	private void showError(String errorMessage) {
		errorLabel.setText(errorMessage);
		errorLabel.setVisible(true);
	}
}
