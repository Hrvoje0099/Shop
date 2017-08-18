package admin.model;

public enum ExceptionsEnum {
	
	ID("id"),
	DATE("date"),
	TIME("time"),
	MESSAGE("message"),
	STACK_TRACE("stackTrace");
	
	private final String value;
	
	ExceptionsEnum(String value) {
		this.value = value;
	}
		
	public String getValue() {
		return value;
	}

}
