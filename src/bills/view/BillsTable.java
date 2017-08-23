package bills.view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import bills.model.BillsModel;
import bills.model.BillsTableModel;

public class BillsTable extends JPanel {

	public JTable tableBills;
	private BillsTableModel billsTableModel;
	protected BillsReview billsReview;

	public BillsTable() {

		billsTableModel = new BillsTableModel();
		tableBills = new JTable(billsTableModel);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(tableBills), BorderLayout.CENTER);
		
		// mouseListener za dvoklik nad redom za otvaranje pregleda računa
		tableBills.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				if (e.getClickCount() == 2) {
					int row_index = tableBills.getSelectedRow();
					int cartID = (int) tableBills.getValueAt(row_index, 9);
					
					billsReview = new BillsReview(cartID);
					billsReview.setVisible(true);
					
					billsReview.addWindowListener(new WindowAdapter() {
						@Override
						public void windowOpened(WindowEvent e) {
							tableBills.setVisible(false);
						}
						
						@Override
						public void windowClosed(WindowEvent e) {
							tableBills.setVisible(true);
						}
					});	
				}
			}
			
		});
		
	}

	public void setData(List<BillsModel> dbBillsModel) {
		billsTableModel.setData(dbBillsModel);
	}
	
	public void refresh() {
		billsTableModel.fireTableDataChanged();
	}

}
