package cashRegister.view;

public class CartTemp {

	private int id;
	private int cartID;
	private int itemCode;
	private String name;
	private String unit;
	private String tax;
	private double quantity;
	private String sellingRP;
	private double discount;
	private String amount;
	
	// saveCart() <- CashRegisterGUI -> btnCash ActionListener i btnCard ActionListener
	public CartTemp(int id, int cartID, int itemCode, String name, String unit, String tax, double quantity,
			String sellingRP, double discount, String amount) {
		this.id = id;
		this.cartID = cartID;
		this.itemCode = itemCode;
		this.name = name;
		this.unit = unit;
		this.tax = tax;
		this.quantity = quantity;
		this.sellingRP = sellingRP;
		this.discount = discount;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public int getCartID() {
		return cartID;
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
