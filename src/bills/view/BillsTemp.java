package bills.view;

import java.sql.Date;
import java.sql.Time;

public class BillsTemp {
	
	private Date date;
	private Time time;
	private String billNumber;
	private int cartID;
	private int numberOfItems;
	private String amount;
	private double discount;
	private String customer;
	private String worker;
	private String paymentMethod;

	public BillsTemp(Date date, Time time, String billNumber, int cartID, int numberOfItems, String amount, String customer, String worker, String paymentMethod) {
		this.date = date;
		this.time = time;
		this.billNumber = billNumber;
		this.cartID = cartID;
		this.numberOfItems = numberOfItems;
		this.amount = amount;
		this.customer = customer;
		this.worker = worker;
		this.paymentMethod = paymentMethod;
	}

	public Date getDate() {
		return date;
	}

	public Time getTime() {
		return time;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public int getCartID() {
		return cartID;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}

	public String getAmount() {
		return amount;
	}

	public double getDiscount() {
		return discount;
	}

	public String getCustomer() {
		return customer;
	}

	public String getWorker() {
		return worker;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

}
