package customers.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import customers.model.CustomersModel;
import customers.model.CustomersTableModel;

public class CustomersTableAdd extends JPanel {

	protected JTable tableCustomersAdd;
	private CustomersTableModel customersTableModel;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemCustomerDelete;
	private JMenuItem menuItemCustomerDetails;
	private CustomersTableAddListener customersTableAddListener;
	
	public CustomersTableAdd() {
		
		customersTableModel = new CustomersTableModel();
		tableCustomersAdd = new JTable(customersTableModel);
		
		popupMenu = new JPopupMenu();
		menuItemCustomerDelete = new JMenuItem("Izbrisati klijenta");
		menuItemCustomerDetails = new JMenuItem("Detalji klijenta");
		popupMenu.add(menuItemCustomerDelete);
		popupMenu.add(menuItemCustomerDetails);
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(tableCustomersAdd), BorderLayout.CENTER);
		
		// keyListener za otvaranje maske klijenta(ENTER) & keyListener za brisanje klijenta(DELETE)
		tableCustomersAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				//System.out.println(e.getKeyCode());
								
				// 10 je ENTER
				if ( e.getKeyCode() == 10 ) {
					int row_index = tableCustomersAdd.getSelectedRow();
					int customerId = (int) tableCustomersAdd.getValueAt(row_index, 0);
					String name = (String) tableCustomersAdd.getValueAt(row_index, 1);
					
					CustomersDetails customersDetails = new CustomersDetails(customerId, name);
					customersDetails.setVisible(true);
				}
				
				// 127 je DELETE
				if ( e.getKeyCode() == 127 ) {
					int row_index = tableCustomersAdd.getSelectedRow();
					int customerId = (int) tableCustomersAdd.getValueAt(row_index, 0);
					
					if (customersTableAddListener != null) {
						customersTableAddListener.deleteCustomer(row_index, customerId);
						customersTableModel.fireTableDataChanged();
					}
				}
			}
		});
		
		// MouseListener za desni klik nad klijentom za otvranje popup menu
		tableCustomersAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				int row = tableCustomersAdd.rowAtPoint(e.getPoint());
				tableCustomersAdd.getSelectionModel().setSelectionInterval(row, row);

				if (e.getButton() == MouseEvent.BUTTON3) {
					popupMenu.show(tableCustomersAdd, e.getX(), e.getY());
				}
			}
		});
		
		// ActionListener za otvranje maske klijenta
		menuItemCustomerDetails.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row_index = tableCustomersAdd.getSelectedRow();
				int customerId = (int) tableCustomersAdd.getValueAt(row_index, 0);
				String name = (String) tableCustomersAdd.getValueAt(row_index, 1);
				
				CustomersDetails customersDetails = new CustomersDetails(customerId, name);
				customersDetails.setVisible(true);
			}
		});
		
		// actionListener za brisnje klijenta
		menuItemCustomerDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				int row_index = tableCustomersAdd.getSelectedRow();
				int customerId = (int) tableCustomersAdd.getValueAt(row_index, 0);
				
				if (customersTableAddListener != null) {
					customersTableAddListener.deleteCustomer(row_index, customerId);
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
	
	public void setCustomersTableAddListener(CustomersTableAddListener customersTableAddListener) {
		this.customersTableAddListener = customersTableAddListener;
	}
	
}
