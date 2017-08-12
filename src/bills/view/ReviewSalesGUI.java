package bills.view;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

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

import bills.controller.BillsController;
import common.DateLabelFormatter;
import common.Utility;

public class ReviewSalesGUI extends JFrame {

	private JLabel lblBillNumber;
	private JTextField txtBillNumber;
	private JLabel lblTotalAmount;
	private JTextField txtTotalAmount;
	private JLabel lblTotalDiscount;
	private JTextField txtTotalDiscount;
	private JSeparator separator1;
	private JSeparator separator2;
	private JLabel lblTotalCash;
	private JSeparator separator3;
	private JLabel lblTotalCards;
	private JTextField txtTotalCash;
	private JTextField txtTotalCards;
	private JButton btnReload;
	private JComboBox<String> comboBoxWorkers;
	private JCheckBox chckbxReviewByWorker;
	private JButton btnPrintTable;
	private JButton btnPrintTotal;
	
	private JLabel lblDateReviewSales;
	private JDatePickerImpl datePicker;
	private UtilDateModel model;
	private Properties p;
	private JDatePanelImpl datePanel;
	
	private JPanel contentPane;
	private BillsController controller;
	private BillsTable billsTable;
	private List<String> listWorkers;
	
	private DecimalFormat df;
	
	private StringWriter errors;
	
	public static final String FONT = "FreeSans.ttf";

	public ReviewSalesGUI() {
		
		controller = new BillsController();
		billsTable = new BillsTable();
		errors = new StringWriter();
		df = new DecimalFormat("0.00");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setTitle("PREGLED PRODAJE");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 1230, 590);
		
		loadComponents();
		setJMenuBar(createMenuBar());
		
		// ActionListener za otvaranje/zatvarajne CheckBox-a izbora radnika
		chckbxReviewByWorker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chckbxReviewByWorker.isSelected()) {
					comboBoxWorkers.setEnabled(true);
					btnReload.setEnabled(true);
					
					try {
						controller.connect();
						
						listWorkers = new LinkedList<String>();
						
						listWorkers = controller.loadWorkersReviewSales();
						
						for (int j = 0; j < listWorkers.size(); j++) {
							comboBoxWorkers.addItem(listWorkers.get(j));
						}
						
						String date = datePicker.getJFormattedTextField().getText();
						String worker = (String) comboBoxWorkers.getSelectedItem();
						controller.loadBillsByDateAndWorker(date, worker);
						
					} catch (Exception e1) {
						e1.printStackTrace(new PrintWriter(errors));
						JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
						
						Utility.saveException(e1.getMessage(), errors.toString());
					}
				}
				else {
					comboBoxWorkers.setEnabled(false);
					comboBoxWorkers.removeAllItems();
					btnReload.setEnabled(false);
					String date = datePicker.getJFormattedTextField().getText();
					try {
						controller.loadBillsByDate(date);
					} catch (SQLException e1) {
						e1.printStackTrace(new PrintWriter(errors));
						JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
						
						Utility.saveException(e1.getMessage(), errors.toString());
					}
				}
				
				billsTable.refresh();
				setTextfields();
			}
		});

		// ActionListener kod izbora datuma pregleda prodaje
		datePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// load raèuna odreðenog datuma
				try {
					String date = datePicker.getJFormattedTextField().getText();
					controller.connect();
					
					if (chckbxReviewByWorker.isSelected()) {
						
						// load raèuna odreðenog datuma i odreðenog radnika
						try {
							String worker = (String) comboBoxWorkers.getSelectedItem();
							controller.loadBillsByDateAndWorker(date, worker);
						} catch (Exception e1) {
							e1.printStackTrace(new PrintWriter(errors));
							JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
							
							Utility.saveException(e1.getMessage(), errors.toString());
						}
						
					} else {
						controller.loadBillsByDate(date);
					}

				} catch (Exception e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e1.getMessage(), errors.toString());
				}
				
				billsTable.refresh();
				setTextfields();
			}
		});
		
		// ActionListener za UÈITAJ button
		btnReload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// load raèuna odreðenog datuma i odreðenog radnika
				try {
					String date = datePicker.getJFormattedTextField().getText();
					String worker = (String) comboBoxWorkers.getSelectedItem();
					controller.connect();
					controller.loadBillsByDateAndWorker(date, worker);

				} catch (Exception e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e1.getMessage(), errors.toString());
				}
				
				billsTable.refresh();
				setTextfields();
			}
		});
		
		// ActionListener za PRINT - UKUPNO
		btnPrintTotal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Document document = new Document();
					PdfWriter.getInstance(document, new FileOutputStream("Pregled prodaje - ukupno.pdf"));
					document.open();
					com.itextpdf.text.Font f1 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true);
					com.itextpdf.text.Font f2 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true, 16, Font.BOLD);
					
					Chunk chunkTitle = new Chunk("PREGLED PRODAJE UKUPNO", f2);
					chunkTitle.setUnderline(0.1f, -2f);
					Paragraph paragraphTitle = new Paragraph();
					paragraphTitle.add(chunkTitle);
					paragraphTitle.setAlignment(Element.ALIGN_CENTER);
					document.add(paragraphTitle);
					document.add(new Paragraph("\n"));

					document.add(new Paragraph("Datum: " + datePicker.getJFormattedTextField().getText()));

					document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

					if (comboBoxWorkers.getSelectedItem() != null)
						document.add(new Paragraph("Radnik: " + comboBoxWorkers.getSelectedItem().toString(), f1));
					
					document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
					
					document.add(new Paragraph("Ukupan broj raèuna: " + txtBillNumber.getText(), f1));
					
					document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
					
					document.add(new Paragraph("Ukupan iznos: " + txtTotalAmount.getText()));
					document.add(new Paragraph("Ukupan popust: " + txtTotalDiscount.getText()));
					
					document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
					
					document.add(new Paragraph("Ukupano gotovine: " + txtTotalCash.getText()));
					document.add(new Paragraph("Ukupano kartice: " + txtTotalCards.getText()));
					
					document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

					document.close();
					
					File pdfFile = new File("Pregled prodaje - ukupno.pdf");
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
		
		// ActionListener za PRINT - TABLICA
		btnPrintTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Document document = new Document();
					PdfWriter.getInstance(document, new FileOutputStream("Pregled prodaje - tablica.pdf"));
					document.open();
					com.itextpdf.text.Font f1 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true);
					com.itextpdf.text.Font f2 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true, 13, Font.BOLD);
					com.itextpdf.text.Font f3 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true, 16, Font.BOLD);
					
					Chunk chunkTitle = new Chunk("PREGLED PRODAJE POJEDINAÈNO", f3);
					chunkTitle.setUnderline(0.1f, -2f);
					Chunk chunkDate = new Chunk(datePicker.getJFormattedTextField().getText(), f2);
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
				
					PdfPTable table1 = new PdfPTable(10);
					table1.setTotalWidth(560);
			        table1.setLockedWidth(true);
			        table1.setWidths(new float[]{5, 4, 5, 1, 4, 4, 4, 6, 4, 2});
			        for(int i = 0; i < 1; i++) {
			        	table1.addCell(new PdfPCell(new Phrase("Datum", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Vrijeme", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Br. raèuna", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("S", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Ukupan iznos", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Ukupan popust", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Klijent", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Radnik", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Naèin plaæanja", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("ID", f2)));
			        }
			        document.add(table1);
					
					PdfPTable table2 = new PdfPTable(10);
					table2.setTotalWidth(560);
			        table2.setLockedWidth(true);
			        table2.setWidths(new float[]{5, 4, 5, 1, 4, 4, 4, 6, 4, 2});
					
					for(int i = 0; i < billsTable.tableBills.getRowCount(); i++) {
						for(int j = 0; j < 10; j++) {
							table2.addCell(new PdfPCell(new Phrase(String.valueOf(billsTable.tableBills.getValueAt(i, j)), f1)));
						}
					}
					document.add(table2);
					document.add(new Paragraph("\n"));
					
					if (comboBoxWorkers.getSelectedItem() != null)
						document.add(new Paragraph("Radnik: " + comboBoxWorkers.getSelectedItem().toString(), f1));
					
					document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
					
					document.add(new Paragraph("Ukupan broj raèuna: " + txtBillNumber.getText(), f1));
					
					document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
					
					document.add(new Paragraph("Ukupan iznos: " + txtTotalAmount.getText()));
					document.add(new Paragraph("Ukupan popust: " + txtTotalDiscount.getText()));
					
					document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
					
					document.add(new Paragraph("Ukupano gotovine: " + txtTotalCash.getText()));
					document.add(new Paragraph("Ukupano kartice: " + txtTotalCards.getText()));
					
					document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

					document.close();
					
					File pdfFile = new File("Pregled prodaje - tablica.pdf");
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
	
	private void setTextfields() {
		
		// ukupan broj raèuna
		txtBillNumber.setText(String.valueOf(billsTable.tableBills.getRowCount()));

		double totalAmount = 0;
		double totalDiscount = 0;
		double totalCash = 0;
		double totalCards = 0;
		for (int i = 0; i < billsTable.tableBills.getRowCount(); i++) {

			// ukupan iznos
			double amount = Double.valueOf((billsTable.tableBills.getValueAt(i, 4).toString().replaceAll(",", ".") + ""));
			totalAmount = amount + totalAmount;

			// ukupan popust
			double discount = Double.valueOf(billsTable.tableBills.getValueAt(i, 5) + "");
			totalDiscount = discount + totalDiscount;

			// ukupno gotovina i ukupno kartice
			if (billsTable.tableBills.getValueAt(i, 8).equals("gotovina")) {
				double cash = Double.valueOf((billsTable.tableBills.getValueAt(i, 4).toString().replaceAll(",", ".") + ""));
				totalCash = cash + totalCash;
			} else {
				double cards = Double.valueOf((billsTable.tableBills.getValueAt(i, 4).toString().replaceAll(",", ".") + ""));
				totalCards = cards + totalCards;
			}

		}
		txtTotalAmount.setText(String.valueOf(df.format(totalAmount)));
		txtTotalDiscount.setText(String.valueOf(df.format(totalDiscount)));
		txtTotalCash.setText(String.valueOf(df.format(totalCash)));
		txtTotalCards.setText(String.valueOf(df.format(totalCards)));	
	}
	
	private JMenuBar createMenuBar() {

		JMenuBar menuBar = new JMenuBar();
		JMenu menuDate = new JMenu();
		JMenu menuTime = new JMenu();

		// Set up trenutni datum i vrijeme na 'MenuBar-u'
		Calendar cal = new GregorianCalendar();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int minute = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		menuDate.setText(day + "/" + (month + 1) + "/" + year);
		menuTime.setText(hour + ":" + minute + "h");
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);  //postavlja menu bar u desni æošak
		menuBar.add(menuDate);
		menuBar.add(menuTime);

		return menuBar;
	}
	
	private void loadComponents() {
		
		lblDateReviewSales = new JLabel("Datum pregled prodaje:");
		lblDateReviewSales.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDateReviewSales.setBounds(10, 11, 145, 25);
		contentPane.add(lblDateReviewSales);
		
		model = new UtilDateModel();
		Calendar cal = new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		model.setDate( (cal.get(Calendar.YEAR)), (cal.get(Calendar.MONTH)), (cal.get(Calendar.DAY_OF_MONTH)) );
		model.setSelected(true);
		p = new Properties();
		p.put("text.today", "Danas");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setSize(120, 25);
		datePicker.setLocation(165, 11);
		contentPane.add(datePicker);
		
		billsTable.setSize(1204, 350);
		billsTable.setLocation(10, 78);
		billsTable.setData(controller.getBillsList());
		contentPane.add(billsTable);
		
		lblBillNumber = new JLabel("Ukupan broj ra\u010Duna:");
		lblBillNumber.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBillNumber.setBounds(10, 439, 120, 20);
		contentPane.add(lblBillNumber);
		
		txtBillNumber = new JTextField();
		txtBillNumber.setEditable(false);
		txtBillNumber.setBounds(140, 439, 60, 25);
		contentPane.add(txtBillNumber);
		txtBillNumber.setColumns(10);
		
		lblTotalAmount = new JLabel("Ukupan iznos:");
		lblTotalAmount.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalAmount.setBounds(10, 477, 90, 20);
		contentPane.add(lblTotalAmount);
		
		txtTotalAmount = new JTextField();
		txtTotalAmount.setEditable(false);
		txtTotalAmount.setBounds(140, 475, 90, 25);
		contentPane.add(txtTotalAmount);
		txtTotalAmount.setColumns(10);
		
		lblTotalDiscount = new JLabel("Ukupan popust:");
		lblTotalDiscount.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalDiscount.setBounds(10, 513, 100, 20);
		contentPane.add(lblTotalDiscount);
		
		txtTotalDiscount = new JTextField();
		txtTotalDiscount.setEditable(false);
		txtTotalDiscount.setBounds(140, 511, 90, 25);
		contentPane.add(txtTotalDiscount);
		txtTotalDiscount.setColumns(10);
		
		separator1 = new JSeparator();
		separator1.setBounds(10, 468, 1204, 2);
		contentPane.add(separator1);
		
		separator2 = new JSeparator();
		separator2.setBounds(10, 539, 1204, 2);
		contentPane.add(separator2);
		
		lblTotalCash = new JLabel("Ukupno gotovina:");
		lblTotalCash.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalCash.setBounds(290, 480, 110, 20);
		contentPane.add(lblTotalCash);
		
		separator3 = new JSeparator();
		separator3.setOrientation(SwingConstants.VERTICAL);
		separator3.setBounds(260, 472, 2, 61);
		contentPane.add(separator3);
		
		lblTotalCards = new JLabel("Ukupno kartice:");
		lblTotalCards.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotalCards.setBounds(290, 513, 100, 20);
		contentPane.add(lblTotalCards);
		
		txtTotalCash = new JTextField();
		txtTotalCash.setEditable(false);
		txtTotalCash.setBounds(410, 477, 90, 25);
		contentPane.add(txtTotalCash);
		txtTotalCash.setColumns(10);
		
		txtTotalCards = new JTextField();
		txtTotalCards.setEditable(false);
		txtTotalCards.setBounds(410, 511, 90, 25);
		contentPane.add(txtTotalCards);
		txtTotalCards.setColumns(10);
		
		comboBoxWorkers = new JComboBox<String>();
		comboBoxWorkers.setEnabled(false);
		comboBoxWorkers.setBounds(165, 47, 192, 20);
		contentPane.add(comboBoxWorkers);
		
		chckbxReviewByWorker = new JCheckBox("Pregled po radniku:");
		chckbxReviewByWorker.setFont(new Font("Tahoma", Font.BOLD, 11));
		chckbxReviewByWorker.setBounds(10, 45, 145, 25);
		contentPane.add(chckbxReviewByWorker);
		
		btnReload = new JButton("U\u010CITAJ");
		btnReload.setEnabled(false);
		btnReload.setBounds(367, 46, 70, 23);
		contentPane.add(btnReload);
		
		btnPrintTable = new JButton("PRINT - TABLICA");
		btnPrintTable.setBounds(1084, 11, 130, 55);
		contentPane.add(btnPrintTable);
		
		btnPrintTotal = new JButton("PRINT - UKUPNO");
		btnPrintTotal.setBounds(1084, 478, 130, 55);
		contentPane.add(btnPrintTotal);
	}
}
