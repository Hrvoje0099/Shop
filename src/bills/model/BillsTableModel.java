package bills.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class BillsTableModel extends AbstractTableModel {
	
	private List<BillsModel> dbBillsModel;
	private String[] colNames = {"Datum", "Vrijeme", "Broj raèuna", "Broj stavki", "Ukupan iznos(KN)", "Ukupan popust(KN)", "Klijent", "Radnik", "Naèin plaæanja", "Košarica ID" };

	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}
	
	public void setData(List<BillsModel> dbBillsModel) {
		this.dbBillsModel = dbBillsModel;
	}
	
	@Override
	public int getColumnCount() {
		return 10;
	}

	@Override
	public int getRowCount() {
		return dbBillsModel.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		BillsModel billsModel = dbBillsModel.get(row);
		
		switch(col) {
		case 0:
			return billsModel.getDate();
		case 1:
			return billsModel.getTime();
		case 2:
			return billsModel.getBillNumber();
		case 3:
			return billsModel.getNumberOfItems();
		case 4:
			return billsModel.getAmount();
		case 5:
			return billsModel.getDiscount();
		case 6:
			return billsModel.getCustomer();
		case 7:
			return billsModel.getWorker();
		case 8:
			return billsModel.getPaymentMethod();
		case 9:
			return billsModel.getCartID();
		}
		
		return null;
	}
}
