package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public final class Utility {
	
	private static Password password = new Password();;
	private static File file = new File("password.ser");
	private static StringWriter errors = new StringWriter();
	private static BaseController controller = new BaseController() {};
		
	public static JPasswordField setPaneForEnterTheAccessPassword() {
		
	    JPanel panel = new JPanel();
	    JPasswordField passwordField = new JPasswordField(10);
	    panel.add(new JLabel("Upišite lozinku za pristup:"));
	    panel.add(passwordField);
	    JOptionPane pane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION) {
	        @Override
	        public void selectInitialValue() {
	            passwordField.requestFocusInWindow();
	        }
	    };
	    pane.createDialog(null, "UNOS LOZINKE").setVisible(true);
	    
	    return passwordField;
	}
	
	public static Password checkPasswordFile() {
		
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			password = (Password) ois.readObject();
			fis.close();
			ois.close();
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			try {
				controller.connect();
				controller.saveException(e1.getMessage(), errors.toString());
			} catch (Exception e2) {
//				JOptionPane.showMessageDialog(null, e2, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			}
			return null;
		}
		return password;
	}
	
	public static void saveException(String message, String stackTrace) {
		
		if (message == null)
			message = "NullPointerExceptin";
		
		try {
			controller.connect();
			controller.saveException(message, stackTrace);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e, "GREŠKA", JOptionPane.ERROR_MESSAGE);
		}
	}

}
