package customers.view;

public class CustomersTemp {
	
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
	
	// CustomersFormAdd: btnSave ActionListener
	public CustomersTemp(int id, String name, String address, String city, String zipCode, String country, String phone,
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
	
	// CustomersFormSearch: btnSearch ActionListener
	public CustomersTemp(String name, String address, String city, String country, String phone,
			String fax, String mail, String mobilePhone, String oib, String contract, String person) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.fax = fax;
		this.mail = mail;
		this.mobilePhone = mobilePhone;
		this.oib = oib;
		this.contract = contract;
		this.person = person;
	}
	
	// loadCustomer() <- Controller <- CustomersDetails -> Window Listener
	public CustomersTemp(int id, String name, String address, String city, String zipCode, String country, String phone,
			String fax, String mail, String mobilePhone, String oib, String contract, String person, String message) {
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
		this.message = message;
	}

	// CustomersDetails: btnSave ActionListener
	public CustomersTemp(String name, String address, String city, String zipCode, String country, String phone,
			String fax, String mail, String mobilePhone, String contract, String person, String message) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
		this.phone = phone;
		this.fax = fax;
		this.mail = mail;
		this.mobilePhone = mobilePhone;
		this.contract = contract;
		this.person = person;
		this.message = message;
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
