package workers.model;

public class WorkersModel {
	
	private int id;
	private String name;
	private String surname;
	private String oib;
	private int birthYear;
	private String sex;
	private String password;
	
	// saveWorker() <- Controller <- WorkersGUI <- WorkersFormAddListener <- WorkersFormAdd
	// loadWorkers() <- Controller <- CashRegisterLogin i WorkersGUI
	public WorkersModel(int id, String name, String suname, String oib, int birthYear, String sex, String password) {
		this.id = id;
		this.name = name;
		this.surname = suname;
		this.oib = oib;
		this.birthYear = birthYear;
		this.sex = sex;
		this.password = password;
	}
	
	// checkWorkerLoginInCashRegister() <- Controller <- CashRegisterLogin
	public WorkersModel(String name, String surname, String password) {
		this.name = name;
		this.surname = surname;
		this.password = password;
	}
	
	// checkWorkerBeforeSave() <- Controllr <- WorkersFormAdd
	public WorkersModel(int id, String oib) {
		this.id = id;
		this.oib = oib;
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
