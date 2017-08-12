package cashRegister.model;

public enum CashRegisterEnum {
	
	ID(1, "id"),
	BILL_NUMBER(2, "billNumber"),
	CART_ID(3, "cartID"),
	NUMBER_OF_ITEMS(4, "numberOfItems"),
	AMOUNT_TOTAL(5, "amountTotal"),
	DISCOUNT(6, "discount"),
	CUSTOMER(7, "customer"),
	WORKER(8, "worker"),
	PAYMENT_METHOD(9, "paymentMethod");
	
    private final int key;
    private final String value;

    CashRegisterEnum(int key, String value) {
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
