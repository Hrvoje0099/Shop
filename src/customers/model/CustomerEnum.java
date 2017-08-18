package customers.model;

public enum CustomerEnum {
	
	CUSTOMER_ID(1, "id"),
	CUSTOMER_NAME(2, "name"),
	ADDRESS(3, "address"),
	CITY(4, "city"),
	ZIP_CODE(5, "zipCode"),
	COUNTRY(6, "country"),
	PHONE(7, "phone"),
	FAX(8, "fax"),
	MAIL(9, "mail"),
	MOBILE_PHONE(10, "mobilePhone"),
	OIB(11, "oib"),
	CONTRACT(12, "contract"),
	PERSON(13, "person"),
	MESSAGE(14, "message");
	
    private final int key;
    private final String value;

    CustomerEnum(int key, String value) {
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
