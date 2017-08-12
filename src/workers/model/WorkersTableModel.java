package workers.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class WorkersTableModel extends AbstractTableModel {
	
	private List<WorkersModel> dbRadniciModel;
	private String[] colNames = {"Radnik ID", "Ime", "Prezime", "OIB", "Godina roðenja", "Spol"};
	
	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}

	public void setData(List<WorkersModel> dbWorkersModel) {
		this.dbRadniciModel = dbWorkersModel;
	}
	
	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		return dbRadniciModel.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		WorkersModel workersModel = dbRadniciModel.get(row);
		
		switch(col) {
		case 0:
			return workersModel.getId();
		case 1:
			return workersModel.getName();
		case 2:
			return workersModel.getSurname();
		case 3:
			return workersModel.getOib();
		case 4:
			return workersModel.getBirthYear();
		case 5:
			return workersModel.getSex();
		}
		
		return null;
	}
	
}
