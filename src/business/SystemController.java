package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ui.Start;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	public static boolean successLogin;

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		System.out.println(id+" "+password);
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		if (map.containsKey(id)&&passwordFound.equals(password)) {
			successLogin=true;
		}
		currentAuth = map.get(id).getAuthorization();

	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	public ObservableList<LibraryMember> getAllLibraryMember() {
		// TODO Auto-generated method stub
		ObservableList<LibraryMember> MemberData = FXCollections.observableArrayList();
		DataAccessFacade da = new DataAccessFacade();
		MemberData.clear();
		MemberData.addAll(da.readMemberMap().values());
		return MemberData;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	public void addMember(LibraryMember libMem) throws Exception {
		DataAccessFacade da = new DataAccessFacade();
		ArrayList<LibraryMember> memberList = new ArrayList<LibraryMember>();

		memberList.addAll(da.readMemberMap().values());
		if (memberList.contains(libMem)) {
			int index = memberList.indexOf(libMem);
			memberList.set(index, libMem);
			DataAccessFacade.loadMemberMap(memberList);
		}
		else {
//		LibraryMember lastMember = 	memberList.get(memberList.size()-1);
//		Integer lastId = 0;
//		if(lastMember!=null)
//			lastId =new Integer(lastMember.getMemberId())+1;
//
//		libMem.setMemberId(lastId.toString());
		da.saveNewMember(libMem);
		}
	}
	@Override
	public boolean checkMemberIdExist(String memberID) {
		// TODO Auto-generated method stub
		DataAccessFacade dc = new DataAccessFacade();
		HashMap<String, LibraryMember> MembersList = dc.readMemberMap();
		return MembersList.containsKey(memberID) ? true : false;

	}
//	public void addAdress(LibraryMember libMem) throws Exception {
//		DataAccessFacade da = new DataAccessFacade();
//		//ArrayList<LibraryMember> memberList = new ArrayList<LibraryMember>();
//
//		memberList.addAll(da.readMemberMap().values());
//		if (memberList.contains(libMem)) {
//			int index = memberList.indexOf(libMem);
//			memberList.set(index, libMem);
//			DataAccessFacade.loadMemberMap(memberList);
//		}
//		else {
//			da.saveNewMember(libMem);
//		}
//	}
	@Override
	public void logout() {
		// TODO Auto-generated method stub

	}
	@Override
	public void DeleteMember(LibraryMember libMem) throws Exception {
		// TODO Auto-generated method stub

	}
	@Override
	public void AddMember(LibraryMember libMem) throws Exception {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean checkBookExist(String isbn) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String Checkout(String memID, String isbn) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public LibraryMember getMemberById(String membID) throws Exception {
		DataAccessFacade dc = new DataAccessFacade();
		HashMap<String, LibraryMember> MembersList = dc.readMemberMap();
		return MembersList.get(membID);
	}


}
