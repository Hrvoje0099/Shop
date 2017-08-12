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
		// A�uriranja GUI-a, preko Swing-a, mora uslijediti na Event Dispatch Thread (EDT), a kod koji radi bilo �to drugo
		//		(npr pristupa nekim resursima na bazi podataka) treba koristiti jedan ili vi�e drugih niti (thread) i
		//  	invokeLater() omogu�uje ove druge niti da a�uriraju GUI s njihovim rezultatima.
		
		errors = new StringWriter();	// za spremanje Exception
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GRE�KA", JOptionPane.ERROR_MESSAGE);
					

					Utility.saveException(e1.getMessage(), errors.toString());
				}
				new MainFrameGUI();
			}							
		});

	}
}