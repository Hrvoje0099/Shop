package items.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ItemsTableAddAndSearchModel extends AbstractTableModel {
	
	private List<ItemsModel> dbItemsModel;
	private String[] colNames = {"Šifra", "Naziv", "Barkod 1", "Barkod 2", "Dobavljač", "Rabat", "Porez", "Mjera", "Nabavna(VP)", "Nabavna(MP)", "Prodajna(VP)", "Prodajna(MP)", "Marža(%)", "Stanje" };
	
	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}
	
	public void setData(List<ItemsModel> dbItemsModel) {
		this.dbItemsModel = dbItemsModel;
	}
	
	@Override
	public int getColumnCount() {
		return 14;
	}
	
	@Override
	public int getRowCount() {
		return dbItemsModel.size();
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		ItemsModel itemsModel = dbItemsModel.get(row);
		
		switch(col) {
		case 0:
			return itemsModel.getItemCode();
		case 1:
			return itemsModel.getName();
		case 2:
			return itemsModel.getBarcode1();
		case 3:
			return itemsModel.getBarcode2();
		case 4:
			return itemsModel.getSupplier();
		case 5:
			return itemsModel.getDiscount();
		case 6:
			return itemsModel.getTax();
		case 7:
			return itemsModel.getUnit();
		case 8:
			return itemsModel.getPurchaseWP();
		case 9:
			return itemsModel.getPurchaseRP();
		case 10:
			return itemsModel.getSellingWP();
		case 11:
			return itemsModel.getSellingRP();
		case 12:
			return itemsModel.getMargin();
		case 13:
			return itemsModel.getItemState();
		}
		
		return null;
	}

}
