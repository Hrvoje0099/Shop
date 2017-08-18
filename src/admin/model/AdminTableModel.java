package admin.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class AdminTableModel extends AbstractTableModel {
	
	private List<ExceptionsModel> dbExceptionsModelTable;
	private String[] colNames = {"ID", "Datum", "Vrijeme", "Message" };
	
	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}
	
	public void setData(List<ExceptionsModel> dbExceptionsModelTable) {
		this.dbExceptionsModelTable = dbExceptionsModelTable;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return dbExceptionsModelTable.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		ExceptionsModel exceptionsModelTable = dbExceptionsModelTable.get(row);
		
		switch(col) {
		case 0:
			return exceptionsModelTable.getId();
		case 1:
			return exceptionsModelTable.getDate();
		case 2:
			return exceptionsModelTable.getTime();
		case 3:
			return exceptionsModelTable.getMessage();
		}
		
		return null;
	}
}
