package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.ObservableList;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public void logout();
	public ObservableList<LibraryMember> getAllLibraryMember();
	public void DeleteMember(LibraryMember libMem) throws Exception;
	public void AddMember(LibraryMember libMem) throws Exception;
	public boolean checkMemberIdExist(String memberID);
	public boolean checkBookExist(String isbn);
	public String Checkout(String memID,String isbn) throws Exception;
//	public ObservableList<CheckoutRecordEntry> CheckoutList() throws Exception;
//	public ObservableList<CheckoutRecordEntry> CheckoutList(String memID)throws Exception;
	public LibraryMember getMemberById(String membID)throws Exception;
}
