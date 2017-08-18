package customers.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CustomersTableModel extends AbstractTableModel {

	private List<CustomersModel> dbCustomersModel;
	private String[] colNames = {"Klijent ID", "Naziv", "Adresa", "Grad", "Poštanski broj", "Država", "Telefon", "Fax", "Mail", "Mobitel", "OIB", "Ugovor", "Osoba" };
	
	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}

	public void setData(List<CustomersModel> dbCustomersModel) {
		this.dbCustomersModel = dbCustomersModel;
	}
	
	@Override
	public int getColumnCount() {
		return 13;
	}

	@Override
	public int getRowCount() {
		return dbCustomersModel.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		CustomersModel customersModel = dbCustomersModel.get(row);
		
		switch(col) {
		case 0:
			return customersModel.getId();
		case 1:
			return customersModel.getName();
		case 2:
			return customersModel.getAddress();
		case 3:
			return customersModel.getCity();
		case 4:
			return customersModel.getZipCode();
		case 5:
			return customersModel.getCountry();
		case 6:
			return customersModel.getPhone();
		case 7:
			return customersModel.getFax();
		case 8:
			return customersModel.getMail();
		case 9:
			return customersModel.getMobilePhone();
		case 10:
			return customersModel.getOib();
		case 11:
			return customersModel.getContract();
		case 12:
			return customersModel.getPerson();
		}
		
		return null;
	}
}
