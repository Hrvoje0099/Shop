package workers.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import workers.model.WorkersModel;
import workers.model.WorkersTableModel;

public class WorkersTableAdd extends JPanel {

	protected JTable tableWorkers;
	private WorkersTableModel workersTableModel;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemWorkerDelete;
	private JMenuItem menuItemWorkerPassword;
	
	private WorkersTableListener workersTableListener;

	public WorkersTableAdd() {

		workersTableModel = new WorkersTableModel();
		tableWorkers = new JTable(workersTableModel);
		popupMenu = new JPopupMenu();

		menuItemWorkerDelete = new JMenuItem("Izbrisati radnika");
		menuItemWorkerPassword = new JMenuItem("Pokaži lozinku");
		popupMenu.add(menuItemWorkerDelete);
		popupMenu.add(menuItemWorkerPassword);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(tableWorkers), BorderLayout.CENTER);
		
		// Set up 'Border'
		Border innerBorder = BorderFactory.createTitledBorder("Popis Radnika");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		// mouseListener za desni klik nad klijentom za otvranje popup menu
		tableWorkers.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				int row = tableWorkers.rowAtPoint(e.getPoint());
				tableWorkers.getSelectionModel().setSelectionInterval(row, row);

				if (e.getButton() == MouseEvent.BUTTON3) {
					popupMenu.show(tableWorkers, e.getX(), e.getY());
				}
			}
		});
		
		// ActionListener za brisnje klijenta
		menuItemWorkerDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				int row_index = tableWorkers.getSelectedRow();
				int workerId = (int) tableWorkers.getValueAt(row_index, 0);
				
				if (workersTableListener != null) {
					workersTableListener.deleteWorker(row_index, workerId);
					workersTableModel.fireTableDataChanged();
				}
			}
		});

		// ActionListener za prikaz lozinke radnika
		menuItemWorkerPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (workersTableListener != null) 
					workersTableListener.showWorkerPassword();
			}
		});

	}

	public void setData(List<WorkersModel> dbWorkersModel) {
		workersTableModel.setData(dbWorkersModel);
	}

	public void refresh() {
		workersTableModel.fireTableDataChanged();
	}

	public void setWorkersTableListener(WorkersTableListener workersTableListener) {
		this.workersTableListener = workersTableListener;
	}
 		
}
