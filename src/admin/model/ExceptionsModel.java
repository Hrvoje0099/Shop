package admin.model;

import java.sql.Date;
import java.sql.Time;

public class ExceptionsModel {
		
	private final int id;
	private final Date date;
	private final Time time;
	private final String message;
	
	// loadExceptions() <- Controller <- AdminGUI
	public ExceptionsModel(int id, Date date, Time time, String message) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public Time getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}

}
