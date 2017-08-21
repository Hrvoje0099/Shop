package cashRegister.view;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

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
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import bills.view.BillsGUI;
import bills.view.ReviewSalesGUI;
import cashRegister.controller.CashRegisterController;
import common.FocusTraversalOnArray;
import common.Utility;
import items.view.ItemsTemp;

public class CashRegisterGUI extends JFrame {

	private JPanel contentPane;
	private CashRegisterTable tableCashRegister;
	private CashRegisterController controller;
	private ItemsTemp item;
	private CashRegisterTemp cashRegister;

	private JTextField txtAmountTotal;
	private JLabel lblWorker;
	private JTextField txtWorker;
	private JLabel lblBillNumber;
	private JTextField txtBillNumber;
	private JLabel lblNumberOfItems;
	private JTextField txtNumberOfItems;
	private JLabel lblR1Customer;
	private JTextField txtR1Customer;
	private JLabel lblItem;
	private JTextField txtItem;
	private JMenuBar menuBar;
	private Component horizontalGlue;
	private JMenu menuDate;
	private JMenu menuTime;
	private JButton btnEntry;
	private JLabel lblQuantity;
	private JTextField txtQuantity;
	private JLabel lblDiscount;
	private JTextField txtDiscount;
	private JLabel lblEntryR1;
	private JButton btnR1;
	private JButton btnRemoveR1;
	private JLabel lblPaymentMethod;
	private JButton btnCard;
	private JButton btnCash;
	private JLabel lblBills;
	private JButton btnBills;
	private JLabel lblReviewSales;
	private JButton btnReviewSales;
	private JLabel lblExit;
	private JButton btnExit;
	private JButton btnPlus;
	private JButton btnMinus;
	private JLabel lblEnterEntry;
	
	private JSeparator separator0;
	private JSeparator separator1;
	private JSeparator separator2;
	private JSeparator separator3;
	private JSeparator separator4;
	private JSeparator separator5;
	private JSeparator separator6;
	private JSeparator separator7;
	private JSeparator separator8;
	
	private Calendar cal;
	private DecimalFormat df2;
	private DecimalFormat df3;
	
	private StringWriter errors;
	
	public static final String FONT = "FreeSans.ttf";
	
	private static int QUANTITY = 1;
	private static int NUMBER_OF_ITEMS = 0;
	private double amountTotalWithoutDiscount = 0;
	private String amountWithDiscount;
	
	public CashRegisterGUI(String name, String surname) {
		
		controller = new CashRegisterController();
		tableCashRegister = new CashRegisterTable();
		errors = new StringWriter();
		
		cal = new GregorianCalendar();
		df2 = new DecimalFormat("0.00");
		df3 = new DecimalFormat("0.000");
		
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setTitle("BLAGAJNA");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(1400, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		
		loadComponents(name, surname);
		
		setJMenuBar(createMenuBar());
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtItem, contentPane, lblWorker, lblBillNumber, lblNumberOfItems, tableCashRegister, tableCashRegister.tableCashRegister, txtAmountTotal, txtWorker, txtBillNumber, txtNumberOfItems, lblR1Customer, txtR1Customer, lblItem, lblEntryR1, separator2, lblPaymentMethod, separator1, lblBills, separator3, separator0, separator5, lblExit, separator6, menuBar, horizontalGlue, menuDate, menuTime}));
		
		// mnemonics
		btnR1.setMnemonic(KeyEvent.VK_R);
		btnCard.setMnemonic(KeyEvent.VK_K);
		btnCash.setMnemonic(KeyEvent.VK_G);
		btnBills.setMnemonic(KeyEvent.VK_R);

		// ActionListener za R1 button
		btnR1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CashRegisterR1 cashRegisterR1 = new CashRegisterR1();
				cashRegisterR1.setVisible(true);
				
				// listener za postavit R1 kupca na R1 textfield
				cashRegisterR1.setCashRegisterListeners(new CashRegisterListeners() {
					public void setR1Customera(String customer) {
						txtR1Customer.setText(customer);
					}
				});
			}
		});
		
		// ActionLisener za brisanje R1 kupca
		btnRemoveR1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtR1Customer.setText("");
			}
		});
		
		// ActionListener za UNOS - unos artikla na tablicu
		btnEntry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
//				item = null;

				if (txtItem.getText().isEmpty())
					JOptionPane.showMessageDialog(null, "NISTE NIŠTA UNIJELI!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				else {
					// sprema samo na tablicu(ne u bazu jo�)

					String barcode = txtItem.getText().toString();

					try {
//						controller.connect();
						item = controller.searchItemByBarcode(barcode);

						if (item != null) {

							double quantityFormat = QuantitiyEntryToTable();
							if (quantityFormat < 1)
								return;

							double discount = DiscountEntryForTable();
							String amountWithDiscount = AmountEntryForTable(discount);

							controller.addItemToTableOfCashRegister(item, quantityFormat, discount, amountWithDiscount);
							tableCashRegister.refresh();

							NUMBER_OF_ITEMS++;
							txtNumberOfItems.setText("" + NUMBER_OF_ITEMS);
							txtItem.setText("");
							txtQuantity.setText("1");
							txtDiscount.setText("0");
						} else
							JOptionPane.showMessageDialog(null, "BARKOD NE POSTOJI!", "GRE�KA", JOptionPane.ERROR_MESSAGE);

					} catch (Exception e1) {
						e1.printStackTrace(new PrintWriter(errors));
						JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);

						Utility.saveException(e1.getMessage(), errors.toString());
					}
				}
			}
		});
		
		// ActionListener za način plaćanja KARTICOM gdje ćemo spremiti i košaricu i blagajnu u bazu
		btnCard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (tableCashRegister.tableCashRegister.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "NISTE UNIJELI NITI JEDAN ARTIKL!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String listOfCards[] = {"Visa", "Maestro", "MasterCard", "Amex", "Diners"};
				String card = (String) JOptionPane.showInputDialog(null, "Izaberite karticu: ", "KARTICE", JOptionPane.QUESTION_MESSAGE, null, listOfCards, "");
				if (card == null) return;
				
				saveCart();
				printBill(card);
				saveCashRegister(card);
			}
		});
		
		// ActionListener za način plaćanja GOTOVINOM gdje ćemo spremiti i košaricu i blagajnu u bazu
		btnCash.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (tableCashRegister.tableCashRegister.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "NISTE UNIJELI NITI JEDAN ARTIKL!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {
					String entry = JOptionPane.showInputDialog(null, "Unesite iznos gotovine: ", "UNOS", JOptionPane.QUESTION_MESSAGE);
					if (entry == null) return;

					if (Double.valueOf(txtAmountTotal.getText().toString().replaceAll(",", ".")) <= 0) {
						JOptionPane.showMessageDialog(null, "NISTE UNIJELI NITI JEDAN ARTIKL!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (Double.valueOf(entry.replaceAll(",", ".")) > Double.valueOf(txtAmountTotal.getText().toString().replaceAll(",", "."))) {
						double returnAmount = (Double.valueOf(entry.replaceAll(",", ".")) - Double.valueOf(txtAmountTotal.getText().toString().replaceAll(",", ".")));
						JOptionPane.showMessageDialog(null, "ZA VRATITI: " + df2.format(returnAmount) + " KN", "INFO", JOptionPane.INFORMATION_MESSAGE);
						
						saveCart();
						printBill("gotovina");
						saveCashRegister("gotovina");
					} else
						JOptionPane.showMessageDialog(null, "UNOS GOTOVINE MORA BITI VEĆI OD IZNOSA RAČUNA!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e1.getMessage(), errors.toString());
					return;
				}
			}
		});
		
		// ActionListener za PLUS i MINUS button
		btnPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (QUANTITY > 0) {
					QUANTITY++;
					txtQuantity.setText("" + QUANTITY);
				}
			}
		});
		btnMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (QUANTITY > 1) {
					QUANTITY--;
					txtQuantity.setText("" + QUANTITY);
				}
			}
		});
		
		// ActionListener za IZLAZ button
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.disconnect();
				dispose();
			}
		});
		
		// ActionListener za RAČUNI button
		btnBills.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BillsGUI billsGui = new BillsGUI();
				billsGui.setVisible(true);
			}
		});
		
		// ActionListener za PREGLED PRODAJE button
		btnReviewSales.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReviewSalesGUI reviewSalesGui = new ReviewSalesGUI();
				reviewSalesGui.setVisible(true);
			}
		});
		
		// disconnect
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				controller.disconnect();
				dispose();
			}
		});

		// izbriši označeni red u tablici kada stisnemo DELETE na tipkovnici
		tableCashRegister.tableCashRegister.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// 127 je DELETE
				if ( e.getKeyCode() == 127 ) {
					int i = tableCashRegister.tableCashRegister.getSelectedRow();
					
					NUMBER_OF_ITEMS--;
					txtNumberOfItems.setText("" + NUMBER_OF_ITEMS);
					
					double amountTotal = Double.valueOf(txtAmountTotal.getText().replaceAll(",", "."));
					String amountWithoutDiscount = (String) tableCashRegister.tableCashRegister.getValueAt(i, 5);
					String amountWithDiscount = (String) tableCashRegister.tableCashRegister.getValueAt(i, 7);
					txtAmountTotal.setText("" + df2.format(amountTotal - Double.valueOf(amountWithDiscount.replaceAll(",", "."))));
					amountTotalWithoutDiscount = amountTotalWithoutDiscount - Double.valueOf(amountWithoutDiscount.replace(",", "."));
					
					controller.removeItemFromCashResgisterTable(i);
					tableCashRegister.refresh();
				}
			}
		});
		
	}
	
	private double QuantitiyEntryToTable() {
		
		double quantityFormat = 0;
		double quantity;
			if (item.getUnit().equals("KG")) {
				quantity = Double.valueOf(txtQuantity.getText().toString().replaceAll(",", "."));
				quantityFormat = Double.valueOf(df3.format(quantity).replace(",", "."));
			} else {
				if (!(Pattern.matches("^[0-9]+$", txtQuantity.getText()))) {
					JOptionPane.showMessageDialog(null, "KOLIČINA NE MOŽE BITI DECIMALNA", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				}
				else
					quantityFormat = Integer.valueOf(txtQuantity.getText().toString());
			}
			
		return quantityFormat;
	}
	
	private double DiscountEntryForTable() {
		
		double discount = 0;
		if (item.getDiscount().equals("DA")) {
			try {
				if (txtDiscount.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "KRIVO UNESEN POPUST!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if ((Integer.valueOf(txtDiscount.getText()) < 0) || (Integer.valueOf(txtDiscount.getText()) > 100)) {
					JOptionPane.showMessageDialog(null, "KRIVO UNESEN POPUST!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
					return discount;
				}
				discount = Integer.valueOf(txtDiscount.getText());
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "KRIVO UNESEN POPUST!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "POPUST NA OVAJ ARTIKL NIJE DOZVOLJEN!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
		}
		return discount;
	}
	
	private String AmountEntryForTable(double discount) {
		
		String amount;
		if (item.getUnit().equals("KG")) {
			amount = String.valueOf((Double.valueOf(item.getSellingRP().replace(",", "."))) * Double.valueOf(txtQuantity.getText().toString().replaceAll(",", ".")));
			amountWithDiscount = String.valueOf(df2.format(Double.valueOf(amount) - ((discount / 100) * Double.valueOf(amount))));
		}
		else {
			amount = String.valueOf((Double.valueOf(item.getSellingRP().replace(",", "."))) * Integer.valueOf(txtQuantity.getText().toString()));
			amountWithDiscount = String.valueOf(df2.format(Double.valueOf(amount) - ((discount / 100) * Double.valueOf(amount))));
		}

		//ukupan iznos
		amountTotalWithoutDiscount = amountTotalWithoutDiscount + Double.valueOf(amount);
		double amountTotalWithDiscount = Double.valueOf(txtAmountTotal.getText().replaceAll(",", ".")) + Double.valueOf(amountWithDiscount.replace(",", "."));
		txtAmountTotal.setText("" + df2.format(amountTotalWithDiscount));
		
		return amountWithDiscount;
	}

	private JMenuBar createMenuBar() {
		
		menuBar = new JMenuBar();
		menuDate = new JMenu();
		menuTime = new JMenu();

		// Set up trenutni datum i vrijeme na 'MenuBar-u'
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int minute = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		menuDate.setText(day + "/" + (month + 1) + "/" + year);
		menuTime.setText(hour + ":" + minute + "h");
		
		horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);  //postavlja menu bar u desni �o�ak
		menuBar.add(menuDate);
		menuBar.add(menuTime);

		return menuBar;
	}
	
	private void saveCart() {
		
		int cartID = getCount1();
		
		for (int i = 0; i < tableCashRegister.tableCashRegister.getRowCount(); i++) {

			int id = getCount2();
			int itemCode = (int) tableCashRegister.tableCashRegister.getValueAt(i, 0);
			String itemName = (String) tableCashRegister.tableCashRegister.getValueAt(i, 1);
			String unit = (String) tableCashRegister.tableCashRegister.getValueAt(i, 2);
			String tax = (String) tableCashRegister.tableCashRegister.getValueAt(i, 3);
			double quantity = (double) tableCashRegister.tableCashRegister.getValueAt(i, 4);
			String sellingRP = (String) tableCashRegister.tableCashRegister.getValueAt(i, 5);
			double discount = (double) tableCashRegister.tableCashRegister.getValueAt(i, 6);
			String amount = (String) tableCashRegister.tableCashRegister.getValueAt(i, 7);

			CartTemp cart = new CartTemp(id, cartID, itemCode, itemName, unit, tax, quantity, sellingRP, discount, amount);

			try {
//				controller.connect();
				controller.saveCart(cart);
				controller.removeFromState(itemCode, quantity);
			} catch (Exception e1) {
				e1.printStackTrace(new PrintWriter(errors));
				JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
				
				Utility.saveException(e1.getMessage(), errors.toString());
			}

			putCount2(--id);
		}
	}
	
	private void printBill(String paymentMethod) {
				
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream("Racun.pdf"));
			document.open();
			com.itextpdf.text.Font f1 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true);
			com.itextpdf.text.Font f2 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true, 13, Font.BOLD);

			document.add(new Paragraph("TRGOVINA d.o.o.", f2));
			document.add(new Paragraph("Savska cesta 44, 10360 Sesvete"));
			document.add(new Paragraph("OIB: 12345678901"));
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("\n"));

			Chunk chunkDate = new Chunk(new VerticalPositionMark());
			Paragraph paragraphBillNumber = new Paragraph("Broj računa: " + txtBillNumber.getText(), f1);
			paragraphBillNumber.add(new Chunk(chunkDate));
			paragraphBillNumber.add(new Paragraph("Datum: " + cal.get(Calendar.DAY_OF_MONTH) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR) + " " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + "h"));
			document.add(paragraphBillNumber);

			document.add(new Paragraph("Radnik: " + txtWorker.getText(), f1));
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("Klijent: " + txtR1Customer.getText(), f1));
			document.add(new Paragraph("\n"));

			PdfPTable table1 = new PdfPTable(8);
			table1.setTotalWidth(525);
			table1.setLockedWidth(true);
			table1.setWidths(new float[]{3, 7, 2, 2, 3, 3, 3, 3});
			for (int i = 0; i < 1; i++) {
				table1.addCell(new PdfPCell(new Phrase("Šifra", f2)));
				table1.addCell(new PdfPCell(new Phrase("Naziv", f2)));
				table1.addCell(new PdfPCell(new Phrase("Mj.jd.", f2)));
				table1.addCell(new PdfPCell(new Phrase("PDV (%)", f2)));
				table1.addCell(new PdfPCell(new Phrase("Količina", f2)));
				table1.addCell(new PdfPCell(new Phrase("MPC", f2)));
				table1.addCell(new PdfPCell(new Phrase("Popust", f2)));
				table1.addCell(new PdfPCell(new Phrase("Iznos", f2)));

			}
			document.add(table1);

			PdfPTable table2 = new PdfPTable(8);
			table2.setTotalWidth(525);
			table2.setLockedWidth(true);
			table2.setWidths(new float[]{3, 7, 2, 2, 3, 3, 3, 3});

			for (int i = 0; i < tableCashRegister.tableCashRegister.getRowCount(); i++) {
				for (int j = 0; j < 8; j++) {
					table2.addCell(new PdfPCell(new Phrase(String.valueOf(tableCashRegister.tableCashRegister.getValueAt(i, j)), f1)));
				}
			}
			document.add(table2);
			document.add(new Paragraph("\n"));

			Chunk chunkPaymentMethod = new Chunk("Način plaćanja: " + paymentMethod, f1);
			Paragraph paragraphPaymentMethod = new Paragraph();
			paragraphPaymentMethod.add(chunkPaymentMethod);
			paragraphPaymentMethod.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraphPaymentMethod);

			Chunk chunkNumberOfItems = new Chunk("Broj stavki: " + txtNumberOfItems.getText());
			Paragraph paragraphNumberOfItems = new Paragraph();
			paragraphNumberOfItems.add(chunkNumberOfItems);
			paragraphNumberOfItems.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraphNumberOfItems);
			document.add(new Paragraph("\n"));

			Chunk chunkAmount = new Chunk("Iznos (kn): " + txtAmountTotal.getText(), f2);
			Paragraph paragraphAmount = new Paragraph();
			paragraphAmount.add(chunkAmount);
			paragraphAmount.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraphAmount);
			document.add(new Paragraph("\n"));

			document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

			Chunk chunkFooter = new Chunk("OTISNUTI RAČUN JE PUNOVAŽAN BEZ POTPISA I PEČATA SUKLADNO ODREDBAMA ZAKONA", f2);
			Paragraph paragraphFooter = new Paragraph();
			paragraphFooter.add(chunkFooter);
			paragraphFooter.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraphFooter);

			document.close();
			File pdfFile = new File("Racun.pdf");
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
	
	private void saveCashRegister(String paymentMethod) {
		
		int cartID = getCount1();
		
		int id = getCount2();
		String billNumber = txtBillNumber.getText().toString();
		int numberOfItems = Integer.valueOf(txtNumberOfItems.getText().toString());
		String amountTotal = txtAmountTotal.getText().toString().replaceAll(",", ".");
		double discountTotal = amountTotalWithoutDiscount - Double.valueOf(amountTotal.replace(",", "."));
		double discountTotalFormat = Double	.valueOf(df2.format(discountTotal).replace(",", "."));
		String customer = txtR1Customer.getText().toString();
		String worker = txtWorker.getText().toString();

		cashRegister = new CashRegisterTemp(id, billNumber, cartID, numberOfItems, amountTotal, discountTotalFormat, customer, worker, paymentMethod);

		try {
//			controller.connect();
			controller.saveCashRegister(cashRegister);
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}

		putCount1(++cartID);
		putCount2(--id);
		int billNumberCount = getCount3();
		putCount3(++billNumberCount);

		NUMBER_OF_ITEMS = 0;
		txtNumberOfItems.setText(" " + NUMBER_OF_ITEMS);
		txtAmountTotal.setText("0,00");
		txtItem.setText("");
		txtBillNumber.setText("" + getCount3() + "/" + cal.get(Calendar.YEAR));
		txtR1Customer.setText("");
		QUANTITY = 1;
		txtQuantity.setText("" + QUANTITY);
		txtDiscount.setText("0");

		for (int row_index = tableCashRegister.tableCashRegister.getRowCount() - 1; tableCashRegister.tableCashRegister.getRowCount() > 0; row_index--) {
			controller.removeItemFromCashResgisterTable(row_index);
		}
		tableCashRegister.refresh();
	}
	
	// count za CartID
	private int getCount1() {

		int count = 0;
		try {
			if (!new File("cartIDCount.txt").exists())
				return 1;
			else {
				BufferedReader br = new BufferedReader(new FileReader(new File("cartIDCount.txt")));
				String s = br.readLine();
				count = Integer.parseInt(s);
				br.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
		return count;
	}

	private void putCount1(int count) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("cartIDCount.txt")));
			bw.write(Integer.toString(count));
			bw.close();
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
	}

	// count za ID u blagajni
	private int getCount2() {

		int count = 2147483646;
		try {
			if (!new File("idCount.txt").exists())
				return 1;
			else {
				BufferedReader br = new BufferedReader(new FileReader(new File("idCount.txt")));
				String s = br.readLine();
				count = Integer.parseInt(s);
				br.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
		return count;
	}

	private void putCount2(int count) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("idCount.txt")));
			bw.write(Integer.toString(count));
			bw.close();
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
	}

	// count za brojRa�una
	private int getCount3() {

		int count = 0;
		try {
			if (!new File("billNumberCount.txt").exists())
				return 1;
			else {
				BufferedReader br = new BufferedReader(new FileReader(new File("billNumberCount.txt")));
				String s = br.readLine();
				count = Integer.parseInt(s);
				br.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
		return count;
	}

	private void putCount3(int count) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("billNumberCount.txt")));
			bw.write(Integer.toString(count));
			bw.close();
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
	}
	
	private void loadComponents(String name, String surname) {
		
		controller.connect();
		
		lblWorker = new JLabel("PRIJAVLJEN  :");
		lblWorker.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblWorker.setBounds(10, 11, 90, 14);
		contentPane.add(lblWorker);
		
		lblBillNumber = new JLabel("RAČUN           :");
		lblBillNumber.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBillNumber.setBounds(10, 44, 90, 14);
		contentPane.add(lblBillNumber);
		
		lblNumberOfItems = new JLabel("STAVKI          :");
		lblNumberOfItems.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNumberOfItems.setBounds(10, 74, 90, 14);
		contentPane.add(lblNumberOfItems);
		
		tableCashRegister.setSize(1364, 401);
		tableCashRegister.setLocation(10, 164);
		tableCashRegister.setData(controller.getCartList());
		contentPane.add(tableCashRegister);
				
		txtAmountTotal = new JTextField();
		txtAmountTotal.setFocusable(false);
		txtAmountTotal.setEditable(false);
		txtAmountTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtAmountTotal.setText("0,00");
		txtAmountTotal.setFont(new Font("Tahoma", Font.BOLD, 65));
		txtAmountTotal.setBounds(904, 11, 470, 142);
		contentPane.add(txtAmountTotal);
		txtAmountTotal.setColumns(10);
		
		txtWorker = new JTextField();
		txtWorker.setBackground(SystemColor.controlHighlight);
		txtWorker.setFocusable(false);
		txtWorker.setEditable(false);
		txtWorker.setBounds(96, 8, 200, 25);
		contentPane.add(txtWorker);
		txtWorker.setColumns(10);
		txtWorker.setText(name + " " + surname);
		
		txtBillNumber = new JTextField();
		txtBillNumber.setFocusable(false);
		txtBillNumber.setEditable(false);
		txtBillNumber.setColumns(10);
		txtBillNumber.setBounds(96, 39, 200, 25);
		txtBillNumber.setText("" + getCount3() + "/" + cal.get(Calendar.YEAR) );
		contentPane.add(txtBillNumber);
		
		txtNumberOfItems = new JTextField();
		txtNumberOfItems.setFocusable(false);
		txtNumberOfItems.setEditable(false);
		txtNumberOfItems.setColumns(10);
		txtNumberOfItems.setBounds(96, 69, 50, 25);
		txtNumberOfItems.setText(" " + NUMBER_OF_ITEMS);
		contentPane.add(txtNumberOfItems);
		
		lblR1Customer = new JLabel("R1:");
		lblR1Customer.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblR1Customer.setBounds(10, 131, 30, 14);
		contentPane.add(lblR1Customer);
		
		txtR1Customer = new JTextField();
		txtR1Customer.setFocusable(true);
		txtR1Customer.setEditable(false);
		txtR1Customer.setBounds(34, 128, 761, 25);
		contentPane.add(txtR1Customer);
		txtR1Customer.setColumns(10);
		
		lblItem = new JLabel("ARTIKL:");
		lblItem.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblItem.setBounds(10, 576, 125, 60);
		contentPane.add(lblItem);
		
		txtItem = new JTextField();
		txtItem.setFont(new Font("Tahoma", Font.PLAIN, 30));
		txtItem.setBounds(145, 576, 831, 60);
		contentPane.add(txtItem);
		txtItem.setColumns(10);
		
		lblEntryR1 = new JLabel("UNOS R1 KUPCA");
		lblEntryR1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEntryR1.setBounds(30, 726, 100, 14);
		contentPane.add(lblEntryR1);
		
		separator2 = new JSeparator();
		separator2.setOrientation(SwingConstants.VERTICAL);
		separator2.setBounds(420, 726, 5, 14);
		contentPane.add(separator2);
		
		lblPaymentMethod = new JLabel("NAČIN PLAĆANJA: GOTOVINA ili KARTICA");
		lblPaymentMethod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPaymentMethod.setBounds(175, 726, 230, 14);
		contentPane.add(lblPaymentMethod);
		
		separator1 = new JSeparator();
		separator1.setOrientation(SwingConstants.VERTICAL);
		separator1.setBounds(155, 726, 5, 14);
		contentPane.add(separator1);
		
		lblBills = new JLabel("RAČUNI: PREGLED, ŠTAMPANJE");
		lblBills.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBills.setBounds(440, 726, 175, 14);
		contentPane.add(lblBills);
		
		separator3 = new JSeparator();
		separator3.setOrientation(SwingConstants.VERTICAL);
		separator3.setBounds(630, 726, 5, 14);
		contentPane.add(separator3);
		
		separator0 = new JSeparator();
		separator0.setBounds(10, 713, 1364, 2);
		contentPane.add(separator0);
		
		separator5 = new JSeparator();
		separator5.setOrientation(SwingConstants.VERTICAL);
		separator5.setBounds(1239, 726, 5, 14);
		contentPane.add(separator5);
		
		lblExit = new JLabel("ALT+F4: IZLAZ");
		lblExit.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExit.setBounds(1264, 726, 90, 14);
		contentPane.add(lblExit);
		
		separator6 = new JSeparator();
		separator6.setOrientation(SwingConstants.VERTICAL);
		separator6.setBounds(1369, 726, 5, 14);
		contentPane.add(separator6);
		
		btnR1 = new JButton("R1");
		btnR1.setBounds(20, 647, 100, 55);
		contentPane.add(btnR1);
		
		btnCard = new JButton("KARTICA");
		btnCard.setBounds(175, 647, 100, 55);
		contentPane.add(btnCard);
		
		btnBills = new JButton("RAČUNI");
		btnBills.setBounds(458, 647, 135, 55);
		contentPane.add(btnBills);
		
		btnExit = new JButton(" IZLAZ");
		btnExit.setIcon(new ImageIcon(CashRegisterGUI.class.getResource("/images/exit.png")));
		btnExit.setBounds(1239, 647, 135, 55);
		contentPane.add(btnExit);
		
		btnEntry = new JButton("UNOS");
		btnEntry.setIcon(new ImageIcon(CashRegisterGUI.class.getResource("/images/left.png")));
		btnEntry.setBounds(1239, 576, 135, 60);
		contentPane.add(btnEntry);
		
		lblEnterEntry = new JLabel("ENTER: UNOS");
		lblEnterEntry.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEnterEntry.setBounds(1147, 726, 75, 14);
		contentPane.add(lblEnterEntry);
		
		separator4 = new JSeparator();
		separator4.setOrientation(SwingConstants.VERTICAL);
		separator4.setBounds(1127, 726, 5, 14);
		contentPane.add(separator4);
		
		btnRemoveR1 = new JButton("MAKNI R1");
		btnRemoveR1.setBounds(805, 128, 89, 25);
		contentPane.add(btnRemoveR1);
		
		btnPlus = new JButton("+");
		btnPlus.setBounds(1096, 576, 40, 28);
		contentPane.add(btnPlus);
		
		btnMinus = new JButton("-");
		btnMinus.setBounds(1097, 608, 40, 28);
		contentPane.add(btnMinus);
		
		lblQuantity = new JLabel("KOLIČINA");
		lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblQuantity.setBounds(1010, 576, 55, 14);
		contentPane.add(lblQuantity);
		
		txtQuantity = new JTextField();
		txtQuantity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtQuantity.setText("1");
		txtQuantity.setHorizontalAlignment(SwingConstants.CENTER);
		txtQuantity.setBounds(986, 596, 100, 40);
		contentPane.add(txtQuantity);
		txtQuantity.setColumns(10);
		
		btnCash = new JButton("GOTOVINA");
		btnCash.setBounds(305, 647, 100, 55);
		contentPane.add(btnCash);
		
		lblDiscount = new JLabel("POPUST(%)");
		lblDiscount.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDiscount.setBounds(1162, 576, 67, 14);
		contentPane.add(lblDiscount);
		
		txtDiscount = new JTextField();
		txtDiscount.setText("0");
		txtDiscount.setHorizontalAlignment(SwingConstants.CENTER);
		txtDiscount.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtDiscount.setBounds(1162, 600, 67, 36);
		contentPane.add(txtDiscount);
		txtDiscount.setColumns(10);
		
		separator7 = new JSeparator();
		separator7.setOrientation(SwingConstants.VERTICAL);
		separator7.setBounds(1147, 576, 5, 60);
		contentPane.add(separator7);
		
		lblReviewSales = new JLabel("PREGLED PRODAJE\r\n");
		lblReviewSales.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblReviewSales.setBounds(685, 726, 110, 14);
		contentPane.add(lblReviewSales);
		
		separator8 = new JSeparator();
		separator8.setOrientation(SwingConstants.VERTICAL);
		separator8.setBounds(840, 726, 5, 14);
		contentPane.add(separator8);
		
		btnReviewSales = new JButton("PREGLED PRODAJE");
		btnReviewSales.setBounds(667, 647, 145, 55);
		contentPane.add(btnReviewSales);
	}
	
	//kad stisnemo ENTER da reagira na button UNOS
	@Override
    public void addNotify() {
        super.addNotify();
        SwingUtilities.getRootPane(btnEntry).setDefaultButton(btnEntry);
	}

}
