package common;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.JOptionPane;

public abstract class BaseController {
	
	public Connection con;
	private final StringWriter errors;
	
	public BaseController() {
		
		errors = new StringWriter();
		try {
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "GREŠKA", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void connect() {
		
		try {
			con = DataSourceSingleton.getInstance().getConnection();
//			System.out.println("connect");
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);

		}	
	}
	
	public void disconnect() {
		
		if (con != null) {
			try {
				con.close();
//				System.out.println("disconnect");
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
		
		try (CallableStatement csInsert = con.prepareCall(procInsertSql)) {
			
			csInsert.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			csInsert.setString(2, message);
			csInsert.setString(3, stackTrace);

			csInsert.executeUpdate();
		}

	}
	
}
