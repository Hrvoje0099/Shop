package other.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import admin.view.AdminGUI;
import bills.view.BillsGUI;
import bills.view.ReviewSalesGUI;
import cashRegister.view.CashRegisterGUI;
import common.Password;
import common.Utility;
import customers.view.CustomersGUI;
import items.view.ItemsGUI;
import workers.view.WorkersGUI;

public class MainFrameGUI extends JFrame {
	
	public JButton btnCashRegister;
	private JButton btnItems;
	private JButton btnWorkers;
	private JButton btnCustomers;
	private JButton btnBills;
	private JButton btnReviewSales;
	
	private JPasswordField passwordField;
	private Password password;
	private StringWriter errors;
	
	public MainFrameGUI() {
		
		errors = new StringWriter();
		password = new Password();
		passwordField = new JPasswordField(10);
		
		setTitle("TRGOVINA");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(450, 525);
		setLocationRelativeTo(null);
		
		setVisible(true);
		setJMenuBar(createMenuBar());
		
		btnCashRegister = new JButton("BLAGAJNA");
		btnCashRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
/*				CashRegisterLogin login = new CashRegisterLogin(MainFrameGUI.this);
				login.setVisible(true);
				
				login.addWindowListener(new WindowAdapter() {
					@Override
					public void windowOpened(WindowEvent e) {
						btnCashRegister.setEnabled(false);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						btnCashRegister.setEnabled(true);
					}
				});	
*/				
				
				CashRegisterGUI cashRegisterGUI = new CashRegisterGUI("ime", "prezime");
				cashRegisterGUI.setVisible(true);
				
				cashRegisterGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowOpened(WindowEvent e) {
						btnCashRegister.setEnabled(false);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						btnCashRegister.setEnabled(true);
					}
				});	
				
			}
		});
		btnCashRegister.setBounds(40, 40, 150, 100);
		getContentPane().add(btnCashRegister);
		
		btnItems = new JButton("ARTIKLI");
		btnItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
/*				passwordField = Utility.setPaneForEnterTheAccessPassword();
				
				password = Utility.checkPasswordFile();
				
				if (password == null)
					return;
								
				if (passwordField.getText().equals(password.myPassword)) {
					ItemsGUI itemsGUI = new ItemsGUI();
					itemsGUI.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(MainFrameGUI.this, "KRIVA LOZINKA", "GREŠKA", JOptionPane.ERROR_MESSAGE);
*/						
				ItemsGUI itemsGUI = new ItemsGUI();
				itemsGUI.setVisible(true);
				
				itemsGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowOpened(WindowEvent e) {
						btnItems.setEnabled(false);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						btnItems.setEnabled(true);
					}
				});	
			
			}
		});
		btnItems.setBounds(245, 40, 150, 100);
		getContentPane().add(btnItems);
		
		btnWorkers = new JButton("RADNICI");
		btnWorkers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
/*				passwordField = Utility.setPaneForEnterTheAccessPassword();
				
				password = Utility.checkPasswordFile();
				
				if (password == null)
					return;
						
				if (passwordField.getText().equals(password.myPassword)) {
					WorkersGUI workersGUI = new WorkersGUI();
					workersGUI.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(MainFrameGUI.this, "KRIVA LOZINKA", "GREŠKA", JOptionPane.ERROR_MESSAGE);
*/						
				WorkersGUI workersGUI = new WorkersGUI();
				workersGUI.setVisible(true);
				
				workersGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowOpened(WindowEvent e) {
						btnWorkers.setEnabled(false);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						btnWorkers.setEnabled(true);
					}
				});		
			
			}
		});
		btnWorkers.setBounds(40, 180, 150, 100);
		getContentPane().add(btnWorkers);
		
		btnCustomers = new JButton("KLIJENTI");
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				  
/*				passwordField = Utility.setPaneForEnterTheAccessPassword();
				
				password = Utility.checkPasswordFile();
				
				if (password == null)
					return;
								
				if (passwordField.getText().equals(password.myPassword)) {
					CustomersGUI customersGUI = new CustomersGUI();
					customersGUI.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(MainFrameGUI.this, "KRIVA LOZINKA", "GREŠKA", JOptionPane.ERROR_MESSAGE);		
*/		
				CustomersGUI customersGUI = new CustomersGUI();
				customersGUI.setVisible(true);
				
				customersGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowOpened(WindowEvent e) {
						btnCustomers.setEnabled(false);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						btnCustomers.setEnabled(true);
					}
				});	
			
			}
		});
		btnCustomers.setBounds(245, 180, 150, 100);
		getContentPane().add(btnCustomers);
		
		btnBills = new JButton("RAČUNI");
		btnBills.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {	
				
				BillsGUI billsGUI = new BillsGUI();
				billsGUI.setVisible(true);
				
				billsGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowOpened(WindowEvent e) {
						btnBills.setEnabled(false);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						btnBills.setEnabled(true);
					}
				});	

			}
		});
		btnBills.setBounds(40, 320, 150, 100);
		getContentPane().add(btnBills);
		
		btnReviewSales = new JButton("PREGLED PRODAJE");
		btnReviewSales.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ReviewSalesGUI reviewSalesGUI = new ReviewSalesGUI();
				reviewSalesGUI.setVisible(true);
				
				reviewSalesGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowOpened(WindowEvent e) {
						btnReviewSales.setEnabled(false);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						btnReviewSales.setEnabled(true);
					}
				});	
				
			}
		});
		btnReviewSales.setBounds(245, 320, 150, 100);
		getContentPane().add(btnReviewSales);
		
		// Set up mnemonics
		btnWorkers.setMnemonic(KeyEvent.VK_R);
		btnCustomers.setMnemonic(KeyEvent.VK_K);
		btnItems.setMnemonic(KeyEvent.VK_A);
		btnCashRegister.setMnemonic(KeyEvent.VK_B);
		btnBills.setMnemonic(KeyEvent.VK_I);
		btnReviewSales.setMnemonic(KeyEvent.VK_P);
		
		// disconnect
		MainFrameGUI.this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
			
		});
		
	}
	
	public void enableAndDisableCashRegisterButton(boolean state) {
		if (state == false)
			btnCashRegister.setEnabled(false);
		else
			btnCashRegister.setEnabled(true);
	}
	
	// Set up 'MenuBar' - IZBORNIK
	private JMenuBar createMenuBar() {
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("IZBORNIK");
		JMenu menuDate = new JMenu();
		JMenu menuTime = new JMenu();
		JMenuItem itemChangePassword = new JMenuItem("Promjena lozinke");
		JMenuItem itemAbout = new JMenuItem("O programu");
		JMenuItem itemExit = new JMenuItem("Izlaz");
		JMenuItem itemAdmin = new JMenuItem("ADMIN DIO");
		menu.add(itemAdmin);
		menu.addSeparator();
		menu.add(itemChangePassword);
		menu.addSeparator();
		menu.add(itemAbout);
		menu.addSeparator();
		menu.add(itemExit);
		JSeparator separator1 = new JSeparator(SwingConstants.VERTICAL);
		menuBar.add(menu);
		menuBar.add(separator1);
		
		// ActionListener za 'O programu'
		itemAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AboutAppGUI aboutAppGUI = new AboutAppGUI();
				aboutAppGUI.setVisible(true);
				
				aboutAppGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowOpened(WindowEvent e) {
						itemAbout.setEnabled(false);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						itemAbout.setEnabled(true);
					}
				});	
			}
		});
		
		// ActionListener za promijenu lozinke
		itemChangePassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangePasswordGUI changePasswordGUI;
				try {
					changePasswordGUI = new ChangePasswordGUI();
					changePasswordGUI.setVisible(true);
					
					changePasswordGUI.addWindowListener(new WindowAdapter() {
						@Override
						public void windowOpened(WindowEvent e) {
							itemChangePassword.setEnabled(false);
						}
						
						@Override
						public void windowClosed(WindowEvent e) {
							itemChangePassword.setEnabled(true);
						}
					});	
					
				} catch (IOException e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e1.getMessage(), errors.toString());
				}
			}
		});
		
		// ActionListener za 'Exit'
		itemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// ActionListener za "ADMIN DIO"
		itemAdmin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				passwordField = Utility.setPaneForEnterTheAccessPassword();
				
				password = Utility.checkPasswordFile();
				
				if (password == null || passwordField.getText().isEmpty() )
					return;
				
				if (passwordField.getText().equals(password.myPassword)) {
					AdminGUI adminGUI = new AdminGUI();
					adminGUI.setVisible(true);
					
					adminGUI.addWindowListener(new WindowAdapter() {
						@Override
						public void windowOpened(WindowEvent e) {
							itemAdmin.setEnabled(false);
						}
						
						@Override
						public void windowClosed(WindowEvent e) {
							itemAdmin.setEnabled(true);
						}
					});	
					
				}
				else
					JOptionPane.showMessageDialog(MainFrameGUI.this, "KRIVA LOZINKA", "GREŠKA", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		// Set up mnemonica and accelerators
		itemChangePassword.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		itemAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
		
		// Set up trenutni datum i vrijeme na 'MenuBar-u'
		Calendar cal = new GregorianCalendar();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int mounth = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int minute = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		menuDate.setText(day + "/" + (mounth+1) + "/" + year);
		menuTime.setText(hour + ":" + minute  + "h");
		menuBar.add(menuDate);
		menuBar.add(menuTime);
		
		return menuBar;
	}
	

}
