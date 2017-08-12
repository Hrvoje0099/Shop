package workers.view;

public class WorkersTemp {
	
	private int id;
	private String name;
	private String surname;
	private String oib;
	private int birthYear;
	private String sex;
	private String password;
	
	// WorkersFormAdd: btnSave ActionListener
	public WorkersTemp(int id, String name, String surname, String oib, int birthYear, String sex, String password) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.oib = oib;
		this.birthYear = birthYear;
		this.sex = sex;
		this.password = password;
	}
	
	// CashRegisterLogin: btnLogin ActionListener
	public WorkersTemp(String name, String surname, String password) {
		this.name = name;
		this.surname = surname;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getOib() {
		return oib;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public String getSex() {
		return sex;
	}
	
	public String getPassword() {
		return password;
	}

}

// public class RadniciFormEvent extends EventObject {
// EventObject	- root klase iz koje æe biti izvedeni sve event state objects
//				- koristi je se kreæemo prema više Swing rodnoj arhitekturi kasnije
