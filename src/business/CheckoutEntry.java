package business;

import java.io.Serializable;
import java.util.Date;

public class CheckoutEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6050615539255649506L;
	private BookCopy checkedoutBookCopy;
	private Date checkoutDate;
	private Date dueDate;

	public CheckoutEntry(Date checkoutDate, Date dueDate, BookCopy checkedoutBookCopy) {
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.checkedoutBookCopy = checkedoutBookCopy;
	}

	public BookCopy getCheckedoutBookCopy() {
		return checkedoutBookCopy;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setCheckedoutBookCopy(BookCopy checkedoutBookCopy) {
		this.checkedoutBookCopy = checkedoutBookCopy;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

}
