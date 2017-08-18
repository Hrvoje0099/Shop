package bills.model;

public enum BillsEnum {
	
	DATA_AND_TIME_OF_SALE("dateAndTimeOfSale"),
	BILL_NUMBER("billNumber"),
	CART_ID("cartID"),
	NUMBER_OF_ITEMS("numberOfItems"),
	AMOUNT_TOTAL("amountTotal"),
	DISCOUNT_TOTAL("discountTotal"),
	CUSTOMER("customer"),
	WORKER("worker"),
	PAYMENT_METHOD("paymentMethod");
	
	private final String value;

	private BillsEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	

}
