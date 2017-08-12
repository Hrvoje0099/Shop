package admin.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import admin.controller.AdminController;
import common.Utility;

public class AdminGUI extends JFrame {
	
	private JMenuBar menuBar;
	private JMenu menuLabel;
	private Component horizontalGlue;
	private JMenu menuDate;
	private JMenu menuTime;
	private JPanel contentPane;
	
	private StringWriter errors;
	
	private AdminTable tableAdmin;
	private AdminController controller;
	
	public AdminGUI() {
		
		errors = new StringWriter();
		tableAdmin = new AdminTable();
		controller = new AdminController();
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		setTitle("EXCEPTION'S");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(900, 900);
		setLocationRelativeTo(null);
		
		getContentPane().add(tableAdmin, BorderLayout.CENTER);
		tableAdmin.setData(controller.getExceptionsList());

		loadExceptionsToTable();
		tableAdmin.refresh();
			
		// disconnect
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				controller.disconnect();
				dispose();
				System.gc();
			}
		});

		setJMenuBar(createMenuBar());
	}
	
	private void loadExceptionsToTable() {
		try {
			controller.connect();
			controller.loadExceptions();
		} catch (Exception e1) {	
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
	}
	
	private JMenuBar createMenuBar() {
		menuBar = new JMenuBar();
		menuLabel = new JMenu();
		menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		menuDate = new JMenu();
		menuTime = new JMenu();

		// Set up trenutni datum i vrijeme na 'MenuBar-u'
		menuLabel.setText("DVOKLIK ZA OTVARANJE STACK TRACE");
		Calendar cal = new GregorianCalendar();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int minute = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		menuDate.setText(day + "/" + (month + 1) + "/" + year);
		menuTime.setText(hour + ":" + minute + "h");
		
		menuBar.add(menuLabel);
		horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);  //postavlja menu bar u desni æošak
		menuBar.add(menuDate);
		menuBar.add(menuTime);

		return menuBar;
	}
}
