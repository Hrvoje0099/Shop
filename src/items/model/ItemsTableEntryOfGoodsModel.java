package items.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ItemsTableEntryOfGoodsModel extends AbstractTableModel {
	
	private List<EntryOfGoodsModel> dbEntryOfGoodsModel;
	private String[] colNames = {"Šifra", "Naziv", "Barkod 1", "Barkod 2", "Dobavljać", "Rabat", "Porez", "Mjera", "Nabavna(VP)", "Nabavna(MP)", "Prodajna(VP)", "Prodajna(MP)", "Marža(%)", "Ulaz(količina)" };

	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}
	
	public void setData(List<EntryOfGoodsModel> dbEntryOfGoodsModel) {
		this.dbEntryOfGoodsModel = dbEntryOfGoodsModel;
	}
	
	@Override
	public int getColumnCount() {
		return 14;
	}

	@Override
	public int getRowCount() {
		return dbEntryOfGoodsModel.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		EntryOfGoodsModel entryOfGoodsModel = dbEntryOfGoodsModel.get(row);
		
		switch(col) {
		case 0:
			return entryOfGoodsModel.getItemCode();
		case 1:
			return entryOfGoodsModel.getName();
		case 2:
			return entryOfGoodsModel.getBarcode1();
		case 3:
			return entryOfGoodsModel.getBarcode2();
		case 4:
			return entryOfGoodsModel.getSupplier();
		case 5:
			return entryOfGoodsModel.getDiscount();
		case 6:
			return entryOfGoodsModel.getTax();
		case 7:
			return entryOfGoodsModel.getUnit();
		case 8:
			return entryOfGoodsModel.getPurchaseWP();
		case 9:
			return entryOfGoodsModel.getPurchaseRP();
		case 10:
			return entryOfGoodsModel.getSellingWP();
		case 11:
			return entryOfGoodsModel.getSellingRP();
		case 12:
			return entryOfGoodsModel.getMargin();
		case 13:
			return entryOfGoodsModel.getAmountInput();
		}
		
		return null;
	}

}
