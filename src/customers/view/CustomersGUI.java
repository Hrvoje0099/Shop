package customers.view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import common.Password;
import common.Utility;
import customers.controller.CustomersController;
import items.view.ItemsDetails;

public class CustomersGUI extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabPaneRight;
	private JTabbedPane tabPaneLeft;
	private JButton refreshBtn;
	
	private CustomersFormAdd customersFormaAdd;
	private CustomersFormSearch customersFormaSearch;
	private CustomersTableAdd customersTableAdd;
	private CustomersTableSearch customersTableSearch;
	private CustomersController controller;
	private Password password;
	
	private StringWriter errors;
	
	public static final String FONT = "FreeSans.ttf";

	public CustomersGUI() {
		
		customersFormaAdd = new CustomersFormAdd();
		customersFormaSearch = new CustomersFormSearch();
		customersTableAdd = new CustomersTableAdd();
		customersTableSearch = new CustomersTableSearch();
		controller = new CustomersController();
		password = new Password();
		errors = new StringWriter();

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		setTitle("KLIJENTI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(1200, 600));
		setSize(1700, 800);
		setLocationRelativeTo(null);
		
		customersTableAdd.setData(controller.getCustomersAddList());
		customersTableSearch.setData(controller.getCustomersSearchList());
		customersTableAdd.tableCustomersAdd.setAutoCreateRowSorter(true);
		
		loadComponents();
		loadAndRefresh();
		setJMenuBar(createMenuBar());

		// SPREMI button - dodaje klijenta na tablicu(desna strana) i sprema ga u bazu
		customersFormaAdd.setCustomersFormAddListener(new CustomersFormAddListener() {
			@Override
			public void addCustomer(CustomersTemp customer) {

				try {
					controller.connect();
					controller.saveCustomer(customer);
					customersTableAdd.refresh();
				} catch (Exception e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GRE�KA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e1.getMessage(), errors.toString());
				}
			}
		});

		// TRA�I button - traži klijenta
		customersFormaSearch.setCustomersFormSearchListener(new CustomersFormSearchListener() {
			@Override
			public void searchCustomer(CustomersTemp customer) {

				try {
					controller.connect();
					controller.searchCustomers(customer);
				} catch (Exception e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GRE�KA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e1.getMessage(), errors.toString());
				}
				customersTableSearch.refresh();
			}
		});
		
		// refresh button
		refreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadAndRefresh();
			}
		});

		// delete klijent
		customersTableAdd.setCustomersTableAddListener(new CustomersTableAddListener() {
			public void deleteCustomer(int row_index, int customerId) {
				
				JPasswordField passwordField = Utility.setPaneForEnterTheAccessPassword();
				
				password = Utility.checkPasswordFile();
				
				if (password == null || passwordField.getText().isEmpty() )
					return;

				if (passwordField.getText().equals(password.myPassword)) {

					try {
						controller.deleteCustomer(row_index, customerId);
					} catch (SQLException e1) {
						e1.printStackTrace(new PrintWriter(errors));
						JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
						
						Utility.saveException(e1.getMessage(), errors.toString());
					}

					customersTableAdd.refresh();

				} else
					JOptionPane.showMessageDialog(null, "KRIVA LOZINKA", "GREŠKA", JOptionPane.ERROR_MESSAGE);
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
		
		refreshBtn = new JButton("");
		refreshBtn.setIcon(new ImageIcon(ItemsDetails.class.getResource("/images/Refresh16.gif")));
		refreshBtn.setFocusable(false);

		// tabPaneLeft
		tabPaneLeft = new JTabbedPane();
		tabPaneLeft.addTab("Dodaj Klijenta", customersFormaAdd);
		tabPaneLeft.addTab("Traži Klijenta", customersFormaSearch);
		tabPaneLeft.setFocusable(false);
		getContentPane().add(tabPaneLeft, BorderLayout.WEST);

		// tabPaneRight
		tabPaneRight = new JTabbedPane();
		tabPaneRight.addTab("Popis Klijenata", customersTableAdd);
		tabPaneRight.addTab("Pretraživanje Klijenata", customersTableSearch);
		getContentPane().add(tabPaneRight, BorderLayout.CENTER);

		// tabPane ChangeListener
		tabPaneLeft.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabPaneLeft.getSelectedIndex() == 0)
					tabPaneRight.setSelectedIndex(0);
				if (tabPaneLeft.getSelectedIndex() == 1)
					tabPaneRight.setSelectedIndex(1);
			}
		});
		tabPaneRight.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabPaneRight.getSelectedIndex() == 0)
					tabPaneLeft.setSelectedIndex(0);
				if (tabPaneRight.getSelectedIndex() == 1)
					tabPaneLeft.setSelectedIndex(1);
			}
		});

	}
	
	private void loadAndRefresh() {
		try {
			controller.connect();
			controller.loadCustomers();
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
		customersTableAdd.refresh();
	}

	private JMenuBar createMenuBar() {

		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("FILE");
		JMenu menuDate = new JMenu();
		JMenu menuTime = new JMenu();
		JMenuItem itemPrint = new JMenuItem("Print");
		JMenuItem itemExit = new JMenuItem("Izlaz");
		menuFile.add(itemPrint);
		menuFile.addSeparator();
		menuFile.add(itemExit);
		JSeparator separator1 = new JSeparator(SwingConstants.VERTICAL);

		menuBar.add(menuFile);
		menuBar.add(separator1);
		menuBar.add(refreshBtn);

		// ActionListener za 'Print'
		itemPrint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Document document = new Document();
					PdfWriter.getInstance(document, new FileOutputStream("Klijenti.pdf"));
					document.open();
					com.itextpdf.text.Font f1 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true, 8);
					com.itextpdf.text.Font f2 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true, 11, Font.BOLD);
					com.itextpdf.text.Font f3 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true, 16, Font.BOLD);
					
					Chunk chunkTitle = new Chunk("POPIS KLIJENATA", f3);
					chunkTitle.setUnderline(0.1f, -2f);
					Calendar cal = new GregorianCalendar();
					Chunk chunkDate = new Chunk(cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.YEAR), f2);
					chunkDate.setUnderline(0.1f, -2f);
					Paragraph paragraphTitle = new Paragraph();
					paragraphTitle.add(chunkTitle);
					paragraphTitle.setAlignment(Element.ALIGN_CENTER);
					document.add(paragraphTitle);
					Paragraph paragraphDate = new Paragraph();
					paragraphDate.add(chunkDate);
					paragraphDate.setAlignment(Element.ALIGN_CENTER);
					document.add(paragraphDate);
					
					document.add(new Paragraph("\n"));
					
					PdfPTable table1 = new PdfPTable(13);
					table1.setTotalWidth(560);
			        table1.setLockedWidth(true);
			        table1.setWidths(new float[]{4, 5, 6, 5, 6, 5, 5, 4, 6, 6, 5, 5, 5});
			        for(int i = 0; i < 1; i++) {
			        	table1.addCell(new PdfPCell(new Phrase("ID", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Naziv", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Adresa", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Grad", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Pos.br.", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Država", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Telefon", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Fax", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Mail", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Mobitel", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("OIB", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Ugovor", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Osoba", f2)));
			        }
			        document.add(table1);
					
					PdfPTable table2 = new PdfPTable(13);
					table2.setTotalWidth(560);
			        table2.setLockedWidth(true);
			        table2.setWidths(new float[]{4, 5, 6, 5, 6, 5, 5, 4, 6, 6, 5, 5, 5});
					
					for(int i = 0; i < customersTableAdd.tableCustomersAdd.getRowCount(); i++) {
						for(int j = 0; j < 13; j++) {
							table2.addCell(new PdfPCell(new Phrase(String.valueOf(customersTableAdd.tableCustomersAdd.getValueAt(i, j)), f1)));
						}
					}
					document.add(table2);
					
					document.close();
					
					File pdfFile = new File("Klijenti.pdf");
					if (pdfFile.toString().endsWith(".pdf")) {
						Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pdfFile);
					} else {
						Desktop desktop = Desktop.getDesktop();
						desktop.open(pdfFile);
					}
				} catch (Exception e1) {
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
				controller.disconnect();
				dispose();
				System.gc();
			}
		});

		// Set up mnemonica and accelerators
		itemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));

		// Set up trenutni datum i vrijeme na 'MenuBar-u'
		Calendar cal = new GregorianCalendar();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int minute = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		menuDate.setText(day + "/" + (month + 1) + "/" + year);
		menuTime.setText(hour + ":" + minute + "h");
		menuBar.add(menuDate);
		menuBar.add(menuTime);

		return menuBar;
	}
	
}
