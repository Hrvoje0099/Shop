package admin.view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import admin.model.AdminTableModel;
import admin.model.ExceptionsModel;

public class AdminTable extends JPanel {
	
	protected JTable tableAdmin;
	private AdminTableModel adminTableModel;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemStackTrace;

	public AdminTable() {
		
		adminTableModel = new AdminTableModel();
		tableAdmin = new JTable(adminTableModel);
		popupMenu = new JPopupMenu();
		
		menuItemStackTrace = new JMenuItem("Stack Trace");
		popupMenu.add(menuItemStackTrace);
		
		tableAdmin.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableAdmin.getColumnModel().getColumn(0).setPreferredWidth(110);
		tableAdmin.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableAdmin.getColumnModel().getColumn(2).setPreferredWidth(80);
		tableAdmin.getColumnModel().getColumn(3).setPreferredWidth(610);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(tableAdmin), BorderLayout.CENTER);
		
		// mouseListener za dvoklik nad redom za otvaranje Stack Trace
		tableAdmin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				if (e.getClickCount() == 2) {
					int row_index = tableAdmin.getSelectedRow();
					int id = (int) tableAdmin.getValueAt(row_index, 0);
					Date date = (Date) tableAdmin.getValueAt(row_index, 1);
					Time time = (Time) tableAdmin.getValueAt(row_index, 2);
					String message = (String) tableAdmin.getValueAt(row_index, 3);
					
					AdminStackTrace adminStackTrace = new AdminStackTrace(id, date, time, message);
					adminStackTrace.setVisible(true);
				}
			}
		});

	}

	public void setData(List<ExceptionsModel> dbExceptionsModelTable) {
		adminTableModel.setData(dbExceptionsModelTable);
	}
	
	public void refresh() {
		adminTableModel.fireTableDataChanged();
	}
}
