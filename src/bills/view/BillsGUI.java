package bills.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import bills.controller.BillsController;
import common.Utility;

public class BillsGUI extends JFrame {

	private JMenuBar menuBar;
	private Component horizontalGlue;
	private JMenu menuDate;
	private JMenu menuTime;
	private JButton btnSearch;
	private JLabel lblRightClick;
	private JLabel lblSearchByBill;
	private JTextField txtSearch;
	
	private JPanel contentPane;
	public BillsController controller;
	private BillsTable billsTable;
	
	private StringWriter errors;

	public BillsGUI() {
		
		controller = new BillsController();
		errors = new StringWriter();
		
		billsTable = new BillsTable();
		billsTable.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Popis računa", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setTitle("RAČUNI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1148, 649);
		setSize(1400, 731);
		setLocationRelativeTo(null);
		setResizable(false);
		
		loadComponents();		
		loadBills();
		setJMenuBar(createMenuBar());
		
		// ActionListener za button TRAŽI - označi račun ako ga nađe
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String trazi = txtSearch.getText();

			    for(int i = 0; i < billsTable.tableBills.getRowCount(); i++){
			        for(int j = 2; j < 3; j++){
			            if(billsTable.tableBills.getModel().getValueAt(i, j).equals(trazi)){
			                billsTable.tableBills.setRowSelectionInterval(i, i);
			                return;
			            }
			        }
			    }
			}
		});
		
		// disconnect
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				controller.disconnect();
				dispose();
				
				if (billsTable.billsReview != null) {
					billsTable.billsReview.dispose();
					billsTable.billsReview.controller.disconnect();
				}
				
			}
		});
		
	}
	
	//kad stisnemo ENTER da reagira na button TRAŽI
	@Override
    public void addNotify() {
        super.addNotify();
        SwingUtilities.getRootPane(btnSearch).setDefaultButton(btnSearch );
    }
	
	private void loadBills() {
		try {
			controller.connect();
			controller.loadBills();
			
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
		billsTable.refresh();
	}
	
	private JMenuBar createMenuBar() {
		
		menuBar = new JMenuBar();
		menuDate = new JMenu();
		menuTime = new JMenu();

		// Set up trenutni datum i vrijeme na 'MenuBar-u'
		Calendar cal = new GregorianCalendar();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int minute = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		menuDate.setText(day + "/" + (month + 1) + "/" + year);
		menuTime.setText(hour + ":" + minute + "h");
		
		horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);  //postavlja menu bar u desni čošak
		menuBar.add(menuDate);
		menuBar.add(menuTime);

		return menuBar;
	}
	
	private void loadComponents() {
		
		lblSearchByBill = new JLabel("TRAŽI RAČUN PO BROJ:");
		lblSearchByBill.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSearchByBill.setBounds(10, 13, 140, 15);
		contentPane.add(lblSearchByBill);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(160, 8, 100, 25);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		
		billsTable.setSize(1374, 592);
		billsTable.setLocation(10, 54);
		billsTable.setData(controller.getBillsList());
		contentPane.add(billsTable);
		
		lblRightClick = new JLabel("DVOKLIK ZA OTVARANJE PREGLEDA RAČUNA(pregled, print)");
		lblRightClick.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRightClick.setBounds(10, 657, 485, 14);
		contentPane.add(lblRightClick);
		
		btnSearch = new JButton("TRAŽI");
		btnSearch.setBounds(270, 8, 62, 25);
		contentPane.add(btnSearch);
	}
}
