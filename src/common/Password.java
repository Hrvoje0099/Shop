package common;

import java.io.Serializable;

public class Password implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String myPassword;

	public void setPassword(String newPassword) {
		this.myPassword = newPassword;
	}

}
