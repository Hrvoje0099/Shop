package cashRegister.model;

public class CartModel {
		
	private int itemCode;
	private String name;
	private String unit;
	private String tax;
	private double quantity;
	private String sellingRP;
	private double discount;
	private String amount;
	
	// addItemToTableOfCashRegister() <- Controller <- CashRegisterGUI
	// loadBill() <- Controlelr <- BillsReview
	public CartModel(int itemCode, String name, String unit, String tax, double quantity, String sellingRP,
			double discount, String amount) {
		this.itemCode = itemCode;
		this.name = name;
		this.unit = unit;
		this.tax = tax;
		this.quantity = quantity;
		this.sellingRP = sellingRP;
		this.discount = discount;
		this.amount = amount;
	}
	
	public int getItemCode() {
		return itemCode;
	}

	public String getName() {
		return name;
	}

	public String getUnit() {
		return unit;
	}

	public String getTax() {
		return tax;
	}

	public double getQuantity() {
		return quantity;
	}

	public String getSellingRP() {
		return sellingRP;
	}
	
	public double getDiscount() {
		return discount;
	}

	public String getAmount() {
		return amount;
	}

}
