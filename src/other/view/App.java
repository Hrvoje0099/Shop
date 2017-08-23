package other.view;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import common.Utility;
import other.controller.OtherController;


public class App {
	
	private static StringWriter errors;
	private static OtherController controller;
	
	public static void main(String[] args) {
		
		// Swing data structures aren't thread-safe!
		// Definicija: Thread predstavlja jedan sekvencijalni tok izvravanja unutar programa!
		// Ažuriranja GUI-a, preko Swing-a, mora uslijediti na Event Dispatch Thread (EDT), a kod koji radi bilo što drugo
		//		(npr pristupa nekim resursima na bazi podataka) treba koristiti jedan ili više drugih niti (thread) i
		//  	invokeLater() omogućuje ove druge niti da ažuriraju GUI s njihovim rezultatima!
		
		errors = new StringWriter();
		controller = new OtherController();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					new MainFrameGUI();
//					controller.disconnect();
				} catch (Exception e) {
					e.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e, "GREŠKA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e.getMessage(), errors.toString());
				} 
				
			}
		});
		
	}
}