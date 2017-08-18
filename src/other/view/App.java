package other.view;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import common.Utility;


public class App {
	
	private static StringWriter errors;
	
	public static void main(String[] args) {
		
		// Swing data structures aren't thread-safe!
		// Definicija: Thread predstavlja jedan sekvencijalni tok izvr�avanja unutar programa!
		// Ažuriranja GUI-a, preko Swing-a, mora uslijediti na Event Dispatch Thread (EDT), a kod koji radi bilo što drugo
		//		(npr pristupa nekim resursima na bazi podataka) treba koristiti jedan ili više drugih niti (thread) i
		//  	invokeLater() omogućuje ove druge niti da ažuriraju GUI s njihovim rezultatima!
		
		errors = new StringWriter();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
					

					Utility.saveException(e1.getMessage(), errors.toString());
				}
				new MainFrameGUI();
			}							
		});

	}
}