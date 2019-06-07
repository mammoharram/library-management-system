package ui;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import business.Author;
import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.fxml.FXML;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class addBookController {

	@FXML
	private TextField titleField;
	@FXML
	private TextField isbnField;
	@FXML
	private TextField maxCheckOutLengField;
	@FXML
	private ComboBox<String> authors;
	@FXML
	ListView<String> list;
	List<Author> autorsList;
	List<Author> bookAutorsList=new ArrayList<Author>();
	HashMap<Integer,Author> index=new HashMap<Integer,Author>();

	@FXML
	public void initialize() {
		authors.getItems().removeAll(authors.getItems());
		DataAccess da = new DataAccessFacade();
		List<Book> booksList = new ArrayList<Book>(da.readBooksMap().values());
		autorsList=new ArrayList<Author>();
		for(Book b:booksList) {
			autorsList.addAll(b.getAuthors());
		}
		int i=1;
		for(Author p:autorsList) {
			authors.getItems().add(i+" "+p.getFirstName()+" "+p.getLastName()+" "+p.getTelephone());
			authors.setId(""+1);
			index.put(i,p);
			i++;

		}

		authors.getSelectionModel().select("select author");
	}

	@FXML
	public void onPressAdd() {
		if(authors.getValue() == "select author") {
			return;
		}
		list.getItems().add(authors.getValue());
	}
	@FXML
	public void onPressSave() {
		DataAccess da = new DataAccessFacade();
		List<Integer> indexes=new ArrayList<Integer>();
		String joined = list.getItems().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
		String newJoin[]=joined.split(",");
		for(String str: newJoin) {
			String first=str.split(" ")[0];
			indexes.add(Integer.parseInt(first));
		}


		for(int i=0;i<indexes.size();i++) {
			bookAutorsList.add(index.get(indexes.get(i)));

		}
		Book book=new Book(isbnField.getText(),titleField.getText(),Integer.parseInt(maxCheckOutLengField.getText()),bookAutorsList);
		da.saveBook(book);
		titleField.clear();
		isbnField.clear();
		maxCheckOutLengField.clear();
		list.getItems().clear();
		authors.setValue(null);//
	}

}
