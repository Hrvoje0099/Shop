package workers.view;

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
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

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
import items.view.ItemsDetails;
import workers.controller.WorkersController;

public class WorkersGUI extends JFrame {

	private JPanel contentPane;
	private WorkersFormAdd workersFormAdd;
	private WorkersTableAdd workersTableAdd;
	private WorkersController controller;
	private Password password;
	
	private JButton btnRefresh;
	
	public static final String FONT = "FreeSans.ttf";
	
	private StringWriter errors;

	public WorkersGUI() {
		
		workersFormAdd = new WorkersFormAdd();
		workersTableAdd = new WorkersTableAdd();
		controller = new WorkersController();
		password = new Password();
		errors = new StringWriter();

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		setTitle("RADNICI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(1100, 500));
		setSize(1500, 800);
		setLocationRelativeTo(null);
		
		controller.connect();
		
		workersTableAdd.setData(controller.getWorkersList());
		workersTableAdd.tableWorkers.setAutoCreateRowSorter(true);
		
		getContentPane().add(workersFormAdd, BorderLayout.WEST);
		getContentPane().add(workersTableAdd, BorderLayout.CENTER);
		
		btnRefresh = new JButton("");
		btnRefresh.setIcon(new ImageIcon(ItemsDetails.class.getResource("/images/Refresh16.gif")));
		btnRefresh.setFocusable(false);

		loadAndRefresh();
		setJMenuBar(createMenuBar());
		
		// SPREMI button - dodaje radnika na tablicu(desna strana) i sprema ga u bazu
		workersFormAdd.setWorkersFormAddListener(new WorkersFormAddListener() {
			public void addWorker(WorkersTemp radnik) {
				
				try {
					controller.saveWorker(radnik);
					workersTableAdd.refresh();		
				} catch (Exception e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e1.getMessage(), errors.toString());
				}
			}
		});

		// delete i pokaži lozinku
		workersTableAdd.setWorkersTableListener(new WorkersTableListener() {

			// delete radnik
			public void deleteWorker(int row_index, int workerId) {

				JPasswordField passwordField = Utility.setPaneForEnterTheAccessPassword();
				
				password = Utility.checkPasswordFile();
				
				if (password == null || passwordField.getText().isEmpty() )
					return;

				if (passwordField.getText().equals(password.myPassword)) {

					try {
						controller.deleteWorker(row_index, workerId);
					} catch (SQLException e1) {
						e1.printStackTrace(new PrintWriter(errors));
						JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
						
						Utility.saveException(e1.getMessage(), errors.toString());
					}

					workersTableAdd.refresh();

				} else
					JOptionPane.showMessageDialog(null, "KRIVA LOZINKA", "GREŠKA", JOptionPane.ERROR_MESSAGE);
			}

			// pokaži lozinku radnika
			public void showWorkerPassword() {

				JPasswordField passwordField = Utility.setPaneForEnterTheAccessPassword();
				
				password = Utility.checkPasswordFile();
				
				if (password == null || passwordField.getText().isEmpty() )
					return;

				if (passwordField.getText().equals(password.myPassword)) {
					String password = null;
					int row_index = workersTableAdd.tableWorkers.getSelectedRow();
					int workerId = (int) workersTableAdd.tableWorkers.getValueAt(row_index, 0);

					try {
						password = controller.showWorkerPassword(workerId);
					} catch (Exception e1) {
						e1.printStackTrace(new PrintWriter(errors));
						JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
						
						Utility.saveException(e1.getMessage(), errors.toString());
					}
					JOptionPane.showMessageDialog(null, "LOZINKA OVOG RADNIKA: " + password, "INFO", JOptionPane.INFORMATION_MESSAGE);
				} else
					JOptionPane.showMessageDialog(null, "KRIVA LOZINKA", "GREŠKA", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		// REFRESH button
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadAndRefresh();
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

	}
	
	private void loadAndRefresh() {
		try {
			controller.loadWorkers();
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
		workersTableAdd.refresh();
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
		menuBar.add(btnRefresh);

		// ActionListener za 'Print'
		itemPrint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Document document = new Document();
					PdfWriter.getInstance(document, new FileOutputStream("Radnici.pdf"));
					document.open();
					com.itextpdf.text.Font f1 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true, 8);
					com.itextpdf.text.Font f2 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true, 12, Font.BOLD);
					com.itextpdf.text.Font f3 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, true, 16, Font.BOLD);
					
					Chunk chunkTitle = new Chunk("POPIS RADNIKA", f3);
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
					
					PdfPTable table1 = new PdfPTable(6);
					table1.setTotalWidth(525);
			        table1.setLockedWidth(true);
			        table1.setWidths(new float[]{4, 5, 6, 6, 6, 3});
			        for(int i = 0; i < 1; i++) {
			        	table1.addCell(new PdfPCell(new Phrase("Radnik ID", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Ime", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Prezime", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("OIB", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Godina rođenja", f2)));
			        	table1.addCell(new PdfPCell(new Phrase("Spol", f2)));
			        }
			        document.add(table1);
					
					PdfPTable table2 = new PdfPTable(6);
					table2.setTotalWidth(525);
			        table2.setLockedWidth(true);
			        table2.setWidths(new float[]{4, 5, 6, 6, 6, 3});
					
					for(int i = 0; i < workersTableAdd.tableWorkers.getRowCount(); i++) {
						for(int j = 0; j < 6; j++) {
							table2.addCell(new PdfPCell(new Phrase(String.valueOf(workersTableAdd.tableWorkers.getValueAt(i, j)), f1)));
						}
					}
					document.add(table2);
					
					document.close();
					
					File pdfFile = new File("Radnici.pdf");
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
			}
		});

		// Set up mnemonica and accelerators
		// izbornikMenu.setMnemonic(KeyEvent.VK_F);
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
