package cashRegister.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cashRegister.model.CartModel;
import cashRegister.model.CashRegisterTableModel;

public class CashRegisterTable extends JPanel {
	
	public JTable tableCashRegister;
	private CashRegisterTableModel cashRegisterTableModel;
	
	public CashRegisterTable() {
		
		cashRegisterTableModel = new CashRegisterTableModel();
		tableCashRegister = new JTable(cashRegisterTableModel);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(tableCashRegister), BorderLayout.CENTER);
	}

	public void setData(List<CartModel> dbCartModel) {
		cashRegisterTableModel.setData(dbCartModel);
	}
	
	public void refresh() {
		cashRegisterTableModel.fireTableDataChanged();
	}
	
}
