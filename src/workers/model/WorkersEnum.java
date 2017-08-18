package workers.model;

public enum WorkersEnum {
	
	WORKER_ID(1, "id"),
	WORKER_NAME(2, "name"),
	WORKER_SURNAME(3, "surname"),
	WORKER_OIB(4, "oib"),
	BIRTH_YEAR(5, "birthYear"),
	SEX(6, "sex"),
	PASSWORD(7, "password");

    private final int key;
    private final String value;

    WorkersEnum(int key, String value) {
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
