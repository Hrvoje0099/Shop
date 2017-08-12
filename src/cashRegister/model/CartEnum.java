package cashRegister.model;

public enum CartEnum {
	
	ID(1, "id"),
	CART_ID(2, "cartID"),
	ITEM_CODE(3, "itemCode"),
	ITEM_NAME(4, "name"),
	UNIT(5, "unit"),
	TAX(6, "tax"),
	QUANTITY(7, "quantity"),
	SELLING_RP(8, "sellingRP"),
	DISCOUNT(9, "discount"),
	AMOUNT(10, "amount");
	
    private final int key;
    private final String value;

    CartEnum(int key, String value) {
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
