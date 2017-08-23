package customers.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import customers.model.CustomersModel;
import customers.model.CustomersTableModel;

public class CustomersTableSearch extends JPanel {

	private JTable tableSearchCustomers;
	private CustomersTableModel customersTableModel;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemCustomerDelete;
	private JMenuItem menuItemCustomerDetails;
	private CustomersTableListener customersTableListener;
	protected CustomersDetails customersDetails;
	
	public CustomersTableSearch() {
		
		customersTableModel = new CustomersTableModel();
		tableSearchCustomers = new JTable(customersTableModel);
		
		popupMenu = new JPopupMenu();
		menuItemCustomerDelete = new JMenuItem("Izbrisati klijenta");
		menuItemCustomerDetails = new JMenuItem("Detalji klijenta");
		popupMenu.add(menuItemCustomerDelete);
		popupMenu.add(menuItemCustomerDetails);
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(tableSearchCustomers), BorderLayout.CENTER);
		
		// MouseListener za desni klik nad klijentom za otvranje popup menu
		tableSearchCustomers.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				int row = tableSearchCustomers.rowAtPoint(e.getPoint());
				tableSearchCustomers.getSelectionModel().setSelectionInterval(row, row);

				if (e.getButton() == MouseEvent.BUTTON3) {
					popupMenu.show(tableSearchCustomers, e.getX(), e.getY());
				}
			}
		});
		
		// ActionListener za otvranje maske klijenta
		menuItemCustomerDetails.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row_index = tableSearchCustomers.getSelectedRow();
				int customerId = (int) tableSearchCustomers.getValueAt(row_index, 0);
				String name = (String) tableSearchCustomers.getValueAt(row_index, 1);
				
				customersDetails = new CustomersDetails(customerId, name);
				customersDetails.setVisible(true);
				
				customersDetails.addWindowListener(new WindowAdapter() {
					@Override
					public void windowOpened(WindowEvent e) {
						menuItemCustomerDetails.setEnabled(false);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						menuItemCustomerDetails.setEnabled(true);
					}
				});	
				
			}
		});
		
		// actionListener za brisnje klijenta
		menuItemCustomerDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				int row_index = tableSearchCustomers.getSelectedRow();
				int customerId = (int) tableSearchCustomers.getValueAt(row_index, 0);
				
				if (customersTableListener != null) {
					customersTableListener.deleteCustomer(row_index, customerId);
					//klijentiTableModel.fireTableRowsDeleted(row_index - 1, row_index - 1);
					customersTableModel.fireTableDataChanged();
				}
			}
		});
		
	}
	
	public void setData(List<CustomersModel> dbCustomersModel) {
		customersTableModel.setData(dbCustomersModel);
	}
	
	public void refresh() {
		customersTableModel.fireTableDataChanged();
	}
	
	public void setCustomersTableListener(CustomersTableListener customersTableListener) {
		this.customersTableListener = customersTableListener;
	}
}
