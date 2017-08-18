package admin.model;

import java.sql.Date;
import java.sql.Time;

public class ExceptionsModel {
		
	private int id;
	private Date date;
	private Time time;
	private String message;
	
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
