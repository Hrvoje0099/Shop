package workers.model;

public class WorkersModel {
	
	private final int id;
	private final String name;
	private final String surname;
	private final String oib;
	private final int birthYear;
	private final String sex;
	private final String password;
	
	// saveWorker() <- Controller <- WorkersGUI <- WorkersFormAddListener <- WorkersFormAdd
	// loadWorkers() <- Controller <- CashRegisterLogin i WorkersGUI
	// checkWorkerLoginInCashRegister() <- Controller <- CashRegisterLogin
	// checkWorkerBeforeSave() <- Controllr <- WorkersFormAdd
	public WorkersModel(int id, String name, String suname, String oib, int birthYear, String sex, String password) {
		this.id = id;
		this.name = name;
		this.surname = suname;
		this.oib = oib;
		this.birthYear = birthYear;
		this.sex = sex;
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
