package items.model;

public enum ItemsEnum {
	
	ITEM_CODE(1, "itemCode"),
	ITEM_NAME(2, "name"),
	BARCODE_1(3, "barcode1"),
	BARCODE_2(4, "barcode2"),
	SUPPLIER(5, "supplier"),
	DISCOUNT(6, "discount"),
	TAX(7, "tax"),
	UNIT(8, "unit"),
	PURCHASE_WP(9, "purchaseWP"),
	PURCHASE_RP(10, "purchaseRP"),
	SELLING_WP(11, "sellingWP"),
	SELLING_RP(12, "sellingRP"),
	MARGIN(13, "margin"),
	MESSAGE(14, "message"),
	ITEM_STATE(15, "itemState"),
	AMOUNT_INPUT(16, "amountInput");
	

    private final int key;
    private final String value;

    ItemsEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
