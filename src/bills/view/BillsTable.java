package bills.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import bills.model.BillsModel;
import bills.model.BillsTableModel;

public class BillsTable extends JPanel {

	public JTable tableBills;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemReviewBill;
	private BillsTableModel billsTableModel;

	public BillsTable() {

		billsTableModel = new BillsTableModel();
		tableBills = new JTable(billsTableModel);
		popupMenu = new JPopupMenu();
		
		setLayout(new BorderLayout());
		add(new JScrollPane(tableBills), BorderLayout.CENTER);
		
		menuItemReviewBill = new JMenuItem("Pregled raèuna");
		popupMenu.add(menuItemReviewBill);
		
		// mouseListener az desnik klik nad raèunom za otvaranje popup menu
		tableBills.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				int row = tableBills.rowAtPoint(e.getPoint());
				tableBills.getSelectionModel().setSelectionInterval(row, row);
				
				if (e.getButton() == MouseEvent.BUTTON3)
					popupMenu.show(tableBills, e.getX(), e.getY());
			}
		});
		
		// ActionListener za otvaranje pregleda raèuna
		menuItemReviewBill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int row = tableBills.getSelectedRow();
				int cartID = (int) tableBills.getValueAt(row, 9);
				
				BillsReview billsReview = new BillsReview(cartID);
				billsReview.setVisible(true);
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
