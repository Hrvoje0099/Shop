package items.model;

public class EntryOfGoodsModel {
	
	private int itemCode;
	private String name;
	private String barcode1;
	private String barcode2;
	private String supplier;
	private String discount;
	private String tax;
	private String unit;
	private String purchaseWP;
	private String purchaseRP;
	private String sellingWP;
	private String sellingRP;
	private String margin;
	private double amountInput;
	
	// addItemForEntryOfGoods() <- Controller <- ItemsGUI <- ItemsFormEntryOfGoodsListener <- ItemsFormEntryOfGoods
	public EntryOfGoodsModel(int itemCode, String name, String barcode1, String barcode2, String supplier, String discount,
			String tax, String unit, String purchaseWP, String purchaseRP, String sellingWP, String sellingRP,
			String margin, double amountInput) {
		this.itemCode = itemCode;
		this.name = name;
		this.barcode1 = barcode1;
		this.barcode2 = barcode2;
		this.supplier = supplier;
		this.discount = discount;
		this.tax = tax;
		this.unit = unit;
		this.purchaseWP = purchaseWP;
		this.purchaseRP = purchaseRP;
		this.sellingWP = sellingWP;
		this.sellingRP = sellingRP;
		this.margin = margin;
		this.amountInput = amountInput;
	}

	public int getItemCode() {
		return itemCode;
	}

	public String getName() {
		return name;
	}

	public String getBarcode1() {
		return barcode1;
	}

	public String getBarcode2() {
		return barcode2;
	}

	public String getSupplier() {
		return supplier;
	}

	public String getDiscount() {
		return discount;
	}

	public String getTax() {
		return tax;
	}

	public String getUnit() {
		return unit;
	}

	public String getPurchaseWP() {
		return purchaseWP;
	}

	public String getPurchaseRP() {
		return purchaseRP;
	}

	public String getSellingWP() {
		return sellingWP;
	}

	public String getSellingRP() {
		return sellingRP;
	}

	public String getMargin() {
		return margin;
	}

	public double getAmountInput() {
		return amountInput;
	}

}
