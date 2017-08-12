package bills.view;

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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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

import bills.controller.BillsController;
import cashRegister.view.CashRegisterTable;
import common.Utility;

public class BillsReview extends JFrame {

	private JPanel contentPane;
	private JLabel lblDate;
	private JTextField txtDate;
	private JLabel lblBillNumber;
	private JTextField txtBillNumber;
	private JLabel lblNumberOfItems;
	private JTextField txtNumberOfItems;
	private JLabel lblAmount;
	private JTextField txtAmount;
	private JLabel lblPaymentMethod;
	private JTextField txtPaymentMethod;
	private JLabel lblWorker;
	private JTextField txtWorker;
	private JLabel lblCustomer;
	private JTextField txtCustomer;
	private JButton btnPrint;

	private BillsController controller;
	private CashRegisterTable cashRegisterTable;
	private BillsTemp billsTemp;

	private StringWriter errors;

	public static final String FONT = "FreeSans.ttf";

	public BillsReview(int cartID) {
		
		controller = new BillsController();
		cashRegisterTable = new CashRegisterTable();
		errors = new StringWriter();

		loadComponents();
		loadBill(cartID);
		
		// ActionListener za PRINT
		btnPrint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Document document = new Document();
					PdfWriter.getInstance(document, new FileOutputStream("Pregled racuna.pdf"));
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
					paragraphBillNumber.add("Datum: " + txtDate.getText());
					document.add(paragraphBillNumber);

					document.add(new Paragraph("Radnik: " + txtWorker.getText(), f1));
					document.add(new Paragraph("\n"));
					document.add(new Paragraph("Klijent: " + txtCustomer.getText(), f1));
					document.add(new Paragraph("\n"));

					PdfPTable table1 = new PdfPTable(8);
					table1.setTotalWidth(525);
					table1.setLockedWidth(true);
					table1.setWidths(new float[]{3, 7, 2, 2, 3, 3, 3, 3});
					for(int i = 0; i < 1; i++) {
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

					for(int i = 0; i < cashRegisterTable.tableCashRegister.getRowCount(); i++) {
						for(int j = 0; j < 8; j++) {
							table2.addCell(new PdfPCell(new Phrase(String.valueOf(cashRegisterTable.tableCashRegister.getValueAt(i, j)), f1)));
						}
					}
					document.add(table2);
					document.add(new Paragraph("\n"));

					Chunk chunkPaymentMethod = new Chunk("Način plaćanja: " + txtPaymentMethod.getText(), f1);
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

					Chunk chunkAmount = new Chunk("Iznos (kn): " + txtAmount.getText(), f2);
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
					File pdfFile = new File("Pregled racuna.pdf");
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
	
	private void loadBill(int cartID) {
		try {
			controller.connect();
			controller.loadBillToTable(cartID);
			billsTemp = controller.loadBillToFields(cartID);
			
			txtDate.setText(String.valueOf(billsTemp.getDate()) + "  "+ String.valueOf(billsTemp.getTime()));
			txtBillNumber.setText(billsTemp.getBillNumber());
			txtNumberOfItems.setText(String.valueOf(billsTemp.getNumberOfItems()));
			txtWorker.setText(billsTemp.getWorker());
			txtCustomer.setText(billsTemp.getCustomer());
			txtAmount.setText(billsTemp.getAmount());
			txtPaymentMethod.setText(billsTemp.getPaymentMethod());
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
		cashRegisterTable.refresh();
	}
	
	private void loadComponents() {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setTitle("PREGLED RAČUNA");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1126, 660);
		setLocationRelativeTo(null);
		setResizable(false);

		lblDate = new JLabel("Datum:");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDate.setBounds(10, 14, 46, 15);
		contentPane.add(lblDate);

		lblBillNumber = new JLabel("Broj ra\u010Duna:");
		lblBillNumber.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBillNumber.setBounds(10, 55, 75, 15);
		contentPane.add(lblBillNumber);

		lblNumberOfItems = new JLabel("Broj stavki:");
		lblNumberOfItems.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNumberOfItems.setBounds(308, 96, 70, 15);
		contentPane.add(lblNumberOfItems);

		lblAmount = new JLabel("Iznos(kn):");
		lblAmount.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAmount.setBounds(10, 96, 60, 15);
		contentPane.add(lblAmount);

		txtDate = new JTextField();
		txtDate.setEditable(false);
		txtDate.setBounds(95, 6, 150, 30);
		contentPane.add(txtDate);
		txtDate.setColumns(10);

		txtBillNumber = new JTextField();
		txtBillNumber.setEditable(false);
		txtBillNumber.setBounds(95, 47, 150, 30);
		contentPane.add(txtBillNumber);
		txtBillNumber.setColumns(10);

		txtNumberOfItems = new JTextField();
		txtNumberOfItems.setEditable(false);
		txtNumberOfItems.setBounds(417, 88, 50, 30);
		contentPane.add(txtNumberOfItems);
		txtNumberOfItems.setColumns(10);

		txtAmount = new JTextField();
		txtAmount.setEditable(false);
		txtAmount.setBounds(95, 88, 150, 30);
		contentPane.add(txtAmount);
		txtAmount.setColumns(10);

		lblWorker = new JLabel("Radnik:");
		lblWorker.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblWorker.setBounds(308, 11, 50, 15);
		contentPane.add(lblWorker);

		lblCustomer = new JLabel("Klijent:");
		lblCustomer.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCustomer.setBounds(633, 14, 50, 15);
		contentPane.add(lblCustomer);

		lblPaymentMethod = new JLabel("Na\u010Din pla\u0107anja:");
		lblPaymentMethod.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPaymentMethod.setBounds(308, 55, 90, 15);
		contentPane.add(lblPaymentMethod);

		txtPaymentMethod = new JTextField();
		txtPaymentMethod.setEditable(false);
		txtPaymentMethod.setBounds(417, 47, 100, 30);
		contentPane.add(txtPaymentMethod);
		txtPaymentMethod.setColumns(10);

		txtWorker = new JTextField();
		txtWorker.setEditable(false);
		txtWorker.setBounds(417, 6, 150, 30);
		contentPane.add(txtWorker);
		txtWorker.setColumns(10);

		txtCustomer = new JTextField();
		txtCustomer.setEditable(false);
		txtCustomer.setBounds(693, 6, 400, 30);
		contentPane.add(txtCustomer);
		txtCustomer.setColumns(10);

		cashRegisterTable.setSize(1083, 492);
		cashRegisterTable.setLocation(10, 129);
		cashRegisterTable.setData(controller.getCartList());
		contentPane.add(cashRegisterTable);

		btnPrint = new JButton("PRINT");
		btnPrint.setBounds(1013, 71, 80, 40);
		contentPane.add(btnPrint);
	}
}