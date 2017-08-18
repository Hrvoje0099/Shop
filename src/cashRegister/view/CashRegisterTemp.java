package cashRegister.view;

public class CashRegisterTemp {
	
	private int id;
	private String billNumber;
	private int cartID;
	private int numberOfItems;
	private String amountTotal;
	private double discount;
	private String r1Client;
	private String worker;
	private String paymentMethode;
	
	// saveCashRegister() <- CashRegisterGUI -> btnCash ActionListener i btnCard ActionListener
	public CashRegisterTemp(int id, String billNumber, int cartID, int numberOfItems, String amountTotal, double discount,
			String r1Client, String worker, String paymentMethode) {
		this.id = id;
		this.billNumber = billNumber;
		this.cartID = cartID;
		this.numberOfItems = numberOfItems;
		this.amountTotal = amountTotal;
		this.discount = discount;
		this.r1Client = r1Client;
		this.worker = worker;
		this.paymentMethode = paymentMethode;
	}

	public int getId() {
		return id;
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

	public String getAmountTotal() {
		return amountTotal;
	}

	public double getDiscount() {
		return discount;
	}

	public String getR1Client() {
		return r1Client;
	}

	public String getWorker() {
		return worker;
	}

	public String getPaymentMethode() {
		return paymentMethode;
	}
	
}
