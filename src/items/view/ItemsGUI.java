package items.view;

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
import java.util.LinkedList;
import java.util.List;

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
import items.controller.ItemsController;

public class ItemsGUI extends JFrame {
	
	private JPanel contentPane;
	private JTabbedPane tabPaneRight;
	private JTabbedPane tabPaneLeft;
	private JButton refreshBtn;
	
	private ItemsFormAdd itemsFormAdd;
	private ItemsFormSearch itemsFormSearch;
	private ItemsFormEntryOfGoods itemsFormEntryOfGoods;
	
	private ItemsTableAdd itemsTableAdd;
	private ItemsTableSearch itemsTableSearch;
	private ItemsTableEntryOfGoods itemsTableEntryOfGoods;
	
	protected ItemsDetails itemsDetails;
	
	private ItemsController controller;
	private Password password;
	
	private StringWriter errors;
	
	public static final String FONT = "FreeSans.ttf";
	
	public ItemsGUI() {
		
		itemsFormAdd = new ItemsFormAdd();
		itemsFormSearch = new ItemsFormSearch();
		itemsFormEntryOfGoods = new ItemsFormEntryOfGoods();
		itemsTableAdd = new ItemsTableAdd();
		itemsTableSearch = new ItemsTableSearch();
		itemsTableEntryOfGoods = new ItemsTableEntryOfGoods();
		controller = new ItemsController();
		password = new Password();
		errors = new StringWriter();
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		setTitle("ARTIKLI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(1200, 600));
		setSize(1800, 800);
		setLocationRelativeTo(null);
		
		itemsTableAdd.setData(controller.getItemsAddList());
		itemsTableAdd.tableItemsAdd.setAutoCreateRowSorter(true);
		itemsTableSearch.setData(controller.getItemsSearchList());
		itemsTableEntryOfGoods.setData(controller.getEntryOfGoodsList());
		
		loadComponents();
		loadAndRefresh();
		setJMenuBar(createMenuBar());
		
		// SPREMI button - dodaje artikl na tablicu(desna strana) i sprema ga u bazu
		itemsFormAdd.setItemsFormAddListener(new ItemsFormAddListener() {
			@Override
			public void addItem(ItemsTemp item) {
				
				try {
					controller.saveItem(item);
					itemsTableAdd.refresh();
				} catch (Exception e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e1.getMessage(), errors.toString());
				}
			}

			@Override
			public List<String> loadSuppliers() {
				
				List<String> listDobavljaca = new LinkedList<String>();
				try {
					listDobavljaca = controller.loadSuppliers();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return listDobavljaca;
			}
			
		});
		
		// TRAŽI button - traži artikl
		itemsFormSearch.setItemsFormSearchListener(new ItemsFormSearchListener() {
			@Override
			public void searchItem(ItemsTemp item) {
				try {
					controller.searchItems(item);
				} catch (Exception e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e1.getMessage(), errors.toString());
				}
				itemsTableSearch.refresh();
			}

			@Override
			public List<String> loadSuppliers() {
				
				List<String> listDobavljaca = new LinkedList<String>();
				try {
					listDobavljaca = controller.loadSuppliers();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return listDobavljaca;
			}
		});

		// REFRESH button
		refreshBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadAndRefresh();
			}
		});
		
		// izbriši artikl
		itemsTableAdd.setItemsTableListener((row_index, itemCode) -> {
			
			JPasswordField passwordField = Utility.setPaneForEnterTheAccessPassword();
			
			password = Utility.checkPasswordFile();
			
			if (password == null || passwordField.getText().isEmpty() )
				return;

			if (passwordField.getText().equals(password.myPassword)) {

				try {
					controller.deleteItem(row_index, itemCode);
				} catch (SQLException e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e1.getMessage(), errors.toString());
				}

				itemsTableAdd.refresh();

			} else
				JOptionPane.showMessageDialog(null, "KRIVA LOZINKA", "GREŠKA", JOptionPane.ERROR_MESSAGE);
		
		});
		
		// ulaz robe button
		itemsFormEntryOfGoods.setItemsFormEntryOfGoodsListener(new ItemsFormEntryOfGoodsListener() {
			@Override
			public void addItemOnTableForEntryOfGoods(ItemsTemp item) {
				controller.addItemForEntryOfGoods(item);
				itemsTableEntryOfGoods.refresh();
			}

			@Override
			public ItemsTemp loadItemForEntryOfGoods(String barcode) {
				
				ItemsTemp item = null;

				try {
					item = controller.loadItemForEntryOfGoods(barcode);		
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return item;
			}
		});
		
		// izbriši tablicu ulaza robe kad stinemo button SPREMI
		itemsTableEntryOfGoods.setItemsTableEntryOfGoodsListener(new ItemsTableEntryOfGoodsListener() {
			@Override
			public void cleanEntryOfGoodsTableAfterSave() {
				for (int row_index = itemsTableEntryOfGoods.tableEntryOfGoods.getRowCount() - 1; itemsTableEntryOfGoods.tableEntryOfGoods.getRowCount() > 0; row_index--) {
					controller.cleanEntryOfGoodsTableAfterSave(row_index);
				}
				itemsTableEntryOfGoods.refresh();
			}

			@Override
			public void addToState(int itemCode, double amountInput) {
				try {
					controller.addToState(itemCode, amountInput);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		// disconnect
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				controller.disconnect();
				dispose();
				
				if (itemsTableAdd.itemsDetails != null) {
					itemsTableAdd.itemsDetails.dispose();
					itemsTableAdd.itemsDetails.controller.disconnect();
				}
				
				if (itemsTableSearch.itemsDetails != null) {
					itemsTableSearch.itemsDetails.dispose();
					itemsTableSearch.itemsDetails.controller.disconnect();
				}
			}
		});
		
	}
	
	private void loadComponents() {
		
		controller.connect();
		
		refreshBtn = new JButton("");
		refreshBtn.setIcon(new ImageIcon(ItemsDetails.class.getResource("/images/Refresh16.gif")));
		refreshBtn.setFocusable(false);
		
		//tabPaneLeft
		tabPaneLeft = new JTabbedPane();
		tabPaneLeft.addTab("Dodaj artikl", itemsFormAdd);
		tabPaneLeft.addTab("Traži artikl", itemsFormSearch);
		tabPaneLeft.addTab("Ulaz robe", itemsFormEntryOfGoods);
		tabPaneLeft.setFocusable(false);
		getContentPane().add(tabPaneLeft, BorderLayout.WEST);
		
		//tabPaneRight
		tabPaneRight = new JTabbedPane();
		tabPaneRight.addTab("Popis artikla", itemsTableAdd);
		tabPaneRight.addTab("Pretraživanje artikla", itemsTableSearch);
		tabPaneRight.addTab("Popis ulaza robe", itemsTableEntryOfGoods);
		getContentPane().add(tabPaneRight, BorderLayout.CENTER);
		
		// tabPane ChangeListener
		tabPaneLeft.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabPaneLeft.getSelectedIndex() == 0)
					tabPaneRight.setSelectedIndex(0);
				if (tabPaneLeft.getSelectedIndex() == 1)
					tabPaneRight.setSelectedIndex(1);
				if (tabPaneLeft.getSelectedIndex() == 2)
					tabPaneRight.setSelectedIndex(2);
			}
		});
		tabPaneRight.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabPaneRight.getSelectedIndex() == 0)
					tabPaneLeft.setSelectedIndex(0);
				if (tabPaneRight.getSelectedIndex() == 1)
					tabPaneLeft.setSelectedIndex(1);
				if (tabPaneRight.getSelectedIndex() == 2)
					tabPaneLeft.setSelectedIndex(2);
			}
		});
		
	}
	
	private void loadAndRefresh() {
		try {
			controller.loadItems();
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
		itemsTableAdd.refresh();
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

		// dodajemo 'IZBORNIK' na 'MenuBar'
		menuBar.add(menuFile);
		menuBar.add(separator1);
		menuBar.add(refreshBtn);

		// ActionListener za 'Print'
		itemPrint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				try {
					Document document = new Document();
					PdfWriter.getInstance(document, new FileOutputStream("Artikli.pdf"));
					document.open();
					com.itextpdf.text.Font f1 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true, 8);
					com.itextpdf.text.Font f2 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true, 12, Font.BOLD);
					com.itextpdf.text.Font f3 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true, 16, Font.BOLD);
					
					Chunk chunkTitle = new Chunk("POPIS ARTIKALA", f3);
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
					
					PdfPTable table1 = new PdfPTable(12);
					table1.setTotalWidth(560);
			        table1.setLockedWidth(true);
			        table1.setWidths(new float[]{3, 5, 6, 6, 6, (float) 3.5, 3, 3, 4, 4, 4, 5});
			        for(int i = 0; i < 1; i++) {
			        	table1.addCell(new PdfPCell(new Phrase("Šifra", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Naziv", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Barkod 1", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Barkod 2", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Dobavljač", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Rabat", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("PDV (%)", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Mj.jd.", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("VPC", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("MPC", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Marža", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Stanje", f2)));
			        }
			        document.add(table1);
					
					PdfPTable table2 = new PdfPTable(12);
					table2.setTotalWidth(560);
			        table2.setLockedWidth(true);
			        table2.setWidths(new float[]{3, 5, 6, 6, 6, (float) 3.5, 3, 3, 4, 4, 4, 5});
					
					for(int i = 0; i < itemsTableAdd.tableItemsAdd.getRowCount(); i++) {
						table2.addCell(new PdfPCell(new Phrase(String.valueOf(itemsTableAdd.tableItemsAdd.getValueAt(i, 0)), f1)));
						table2.addCell(new PdfPCell(new Phrase(String.valueOf(itemsTableAdd.tableItemsAdd.getValueAt(i, 1)), f1)));
						table2.addCell(new PdfPCell(new Phrase(String.valueOf(itemsTableAdd.tableItemsAdd.getValueAt(i, 2)), f1)));
						table2.addCell(new PdfPCell(new Phrase(String.valueOf(itemsTableAdd.tableItemsAdd.getValueAt(i, 3)), f1)));
						table2.addCell(new PdfPCell(new Phrase(String.valueOf(itemsTableAdd.tableItemsAdd.getValueAt(i, 4)), f1)));
						table2.addCell(new PdfPCell(new Phrase(String.valueOf(itemsTableAdd.tableItemsAdd.getValueAt(i, 5)), f1)));
						table2.addCell(new PdfPCell(new Phrase(String.valueOf(itemsTableAdd.tableItemsAdd.getValueAt(i, 6)), f1)));
						table2.addCell(new PdfPCell(new Phrase(String.valueOf(itemsTableAdd.tableItemsAdd.getValueAt(i, 7)), f1)));
						table2.addCell(new PdfPCell(new Phrase(String.valueOf(itemsTableAdd.tableItemsAdd.getValueAt(i, 10)), f1)));
						table2.addCell(new PdfPCell(new Phrase(String.valueOf(itemsTableAdd.tableItemsAdd.getValueAt(i, 11)), f1)));
						table2.addCell(new PdfPCell(new Phrase(String.valueOf(itemsTableAdd.tableItemsAdd.getValueAt(i, 12)), f1)));
						table2.addCell(new PdfPCell(new Phrase(String.valueOf(itemsTableAdd.tableItemsAdd.getValueAt(i, 13)), f1)));
						
					}
					document.add(table2);
					
					document.close();
					
					File pdfFile = new File("Artikli.pdf");
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
				
				if (itemsTableAdd.itemsDetails != null)
					itemsTableAdd.itemsDetails.dispose();
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
