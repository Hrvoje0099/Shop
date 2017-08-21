package cashRegister.model;

public class CartModel {
		
	private final int itemCode;
	private final String name;
	private final String unit;
	private final String tax;
	private final double quantity;
	private final String sellingRP;
	private final double discount;
	private final String amount;
	
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
