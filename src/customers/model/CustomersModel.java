package customers.model;

public class CustomersModel {
	
	private int id;
	private String name;
	private String address;
	private String city;
	private String zipCode;
	private String country;
	private String phone;
	private String fax;
	private String mail;
	private String mobilePhone;
	private String oib;
	private String contract;
	private String person;
	private String message;
	
	// saveCustomer() <- Controller <- CustomersGUI <- CustomersFormAddListener <- CustomersFormAdd
	// loadCustomers() <- Controller <- CustomersGUI
	// searchCustomer() <- Controller <- CustomersGUI <- CustomersFormSearchListener <- CustomersFormSearch
	public CustomersModel(int id, String name, String address, String city, String zipCode, String country, String phone,
			String fax, String mail, String mobilePhone, String oib, String contract, String person) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
		this.phone = phone;
		this.fax = fax;
		this.mail = mail;
		this.mobilePhone = mobilePhone;
		this.oib = oib;
		this.contract = contract;
		this.person = person;
	}
	
	//  checkCustomerBeforeSave() <- Controller: CustomersFormAdd
	public CustomersModel(int id, String oib) {
		this.id = id;
		this.oib = oib;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getCountry() {
		return country;
	}

	public String getPhone() {
		return phone;
	}

	public String getFax() {
		return fax;
	}

	public String getMail() {
		return mail;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getOib() {
		return oib;
	}

	public String getContract() {
		return contract;
	}

	public String getPerson() {
		return person;
	}

	public String getMessage() {
		return message;
	}
	
}
