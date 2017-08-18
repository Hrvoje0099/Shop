package customers.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import customers.model.CustomersModel;
import customers.model.CustomersTableModel;

public class CustomersTableSearch extends JPanel {

	private JTable tableSearchCustomers;
	private CustomersTableModel customersTableModel;
	
	public CustomersTableSearch() {
		
		customersTableModel = new CustomersTableModel();
		tableSearchCustomers = new JTable(customersTableModel);
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(tableSearchCustomers), BorderLayout.CENTER);
	}
	
	public void setData(List<CustomersModel> dbCustomersModel) {
		customersTableModel.setData(dbCustomersModel);
	}
	
	public void refresh() {
		customersTableModel.fireTableDataChanged();
	}
}
