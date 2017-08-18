package admin.view;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.Time;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import admin.controller.AdminController;
import common.Utility;

public class AdminStackTrace extends JFrame {

	private JPanel contentPane;
	private JLabel lblDateAndTime;
	private JTextField txtDateAndTime;
	private JLabel lblMessage;
	private JTextField txtMessage;
	private JLabel lblStackTrace;
	private JTextArea txtAreaStackTrace;
	
	private AdminController controller;
	private JScrollPane scrollPane;
	
	private StringWriter errors;

	public AdminStackTrace(int id, Date date, Time time, String message) {
		
		controller = new AdminController();
		errors = new StringWriter();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setTitle("STACK TRACE");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 771, 652);
		
		loadComponents();
		
		// load Stack Trace iz određenog Exception-a
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				
				txtDateAndTime.setText(date + " "+ time);
				
				txtMessage.setText(message);
				
				try {
					controller.connect();
					String stackTrace = controller.loadExceptionsStackTrace(id);
					txtAreaStackTrace.setText(stackTrace);
				} catch (Exception e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e1.getMessage(), errors.toString());
				}
			}	
		});
		
		// disconnect
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				controller.disconnect();
				dispose();
				System.gc();
			}
		});
	}
	
	private void loadComponents() {
		
		lblDateAndTime = new JLabel("Datum i vrijeme:");
		lblDateAndTime.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDateAndTime.setBounds(10, 13, 100, 15);
		contentPane.add(lblDateAndTime);
		
		txtDateAndTime = new JTextField();
		txtDateAndTime.setEditable(false);
		txtDateAndTime.setBounds(120, 8, 130, 25);
		contentPane.add(txtDateAndTime);
		txtDateAndTime.setColumns(10);
		
		txtMessage = new JTextField();
		txtMessage.setEditable(false);
		txtMessage.setBounds(120, 44, 635, 25);
		contentPane.add(txtMessage);
		txtMessage.setColumns(10);
		
		lblMessage = new JLabel("Message:");
		lblMessage.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMessage.setBounds(10, 49, 65, 15);
		contentPane.add(lblMessage);
		
		lblStackTrace = new JLabel("Stack Trace:");
		lblStackTrace.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStackTrace.setBounds(10, 87, 80, 15);
		contentPane.add(lblStackTrace);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 113, 745, 500);
		contentPane.add(scrollPane);
		
		txtAreaStackTrace = new JTextArea();
		scrollPane.setViewportView(txtAreaStackTrace);
		txtAreaStackTrace.setEditable(false);
	}
}
