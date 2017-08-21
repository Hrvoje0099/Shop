package bills.model;

import java.sql.Date;
import java.sql.Time;

public class BillsModel {
		
	private final Date date;
	private final Time time;
	private final String billNumber;
	private final int cartID;
	private final int numberOfItems;
	private final String amount;
	private final double discount;
	private final String customer;
	private final String worker;
	private final String paymentMethod;

	// loadBills() <- Controller <- BillsGUI
	// loadBillsByDate() <- Controller <- ReviewSaleseGUI
	// loadBillsByDateAndWorker() <- Controller <- ReviewSaleseGUI
	public BillsModel(Date date, Time time, String billNumber, int cartID, int numberOfItems, String amount, double discount,
			String customer, String worker, String paymentMethod) {
		this.date = date;
		this.time = time;
		this.billNumber = billNumber;
		this.cartID = cartID;
		this.numberOfItems = numberOfItems;
		this.amount = amount;
		this.discount = discount;
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
