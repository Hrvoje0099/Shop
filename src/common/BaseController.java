package common;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.JOptionPane;

public abstract class BaseController {
	
	protected Connection con;
	
	private StringWriter errors;
	
	public BaseController() {
		
		// za spremanje Exception
		errors = new StringWriter();
	}

	
	public void connect() throws Exception {
		
		if(con != null) return;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			try {
				saveException(e1.getMessage(), errors.toString());
			} catch (SQLException e2) {
				JOptionPane.showMessageDialog(null, e2, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		String url = "jdbc:mysql://localhost:3306/zavrsni?autoReconnect=true&useSSL=false";
		
		con = DriverManager.getConnection(url, "root", "88rabil");
	}
	
	public void disconnect() {
		
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace(new PrintWriter(errors));
				JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
				
				try {
					saveException(e1.getMessage(), errors.toString());
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null, e2, "GREŠKA", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else return;
	}
	
	public void saveException(String message, String stackTrace) throws SQLException {
		
		String procInsertSql = "{ call zavrsni.saveException(?,?,?) }";
		CallableStatement csInsert = con.prepareCall(procInsertSql);

		csInsert.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
		csInsert.setString(2, message);
		csInsert.setString(3, stackTrace);

		csInsert.executeUpdate();

		csInsert.close();
	}
	
}
