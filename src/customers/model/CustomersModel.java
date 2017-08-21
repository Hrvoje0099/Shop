package customers.model;

public class CustomersModel {
	
	private final int id;
	private final String name;
	private final String address;
	private final String city;
	private final String zipCode;
	private final String country;
	private final String phone;
	private final String fax;
	private final String mail;
	private final String mobilePhone;
	private final String oib;
	private final String contract;
	private final String person;
	
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
	
}
