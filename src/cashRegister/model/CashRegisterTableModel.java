package cashRegister.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CashRegisterTableModel extends AbstractTableModel {
	
	private List<CartModel> dbCartModel;
	private String[] colNames = {"Šifra", "Naziv", "Mj.jed.", "PDV(%)", "Količina", "MPC", "Popust", "Iznos" };

	
	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}
	
	public void setData(List<CartModel> dbCartModelTable) {
		this.dbCartModel = dbCartModelTable;
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public int getRowCount() {
		return dbCartModel.size();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		switch (column) {
			case 4:
				return true;
			default:
				return false;
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		CartModel cartModel = dbCartModel.get(row);
		
		switch(col) {
		case 0:
			return cartModel.getItemCode();
		case 1:
			return cartModel.getName();
		case 2:
			return cartModel.getUnit();
		case 3:
			return cartModel.getTax();
		case 4:
			return cartModel.getQuantity();
		case 5:
			return cartModel.getSellingRP();
		case 6:
			return cartModel.getDiscount();
		case 7:
			return cartModel.getAmount();
		}

		return null;
	}	
	
}
