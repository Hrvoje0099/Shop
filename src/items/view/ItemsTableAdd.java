package items.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import items.model.ItemsModel;
import items.model.ItemsTableAddAndSearchModel;

public class ItemsTableAdd extends JPanel {
	
	protected JTable tableItemsAdd;
	private JPopupMenu popupMenu;
	private JMenuItem menuItemDeleteItem;
	protected JMenuItem menuItemDetailsItem;
	
	private ItemsTableAddAndSearchModel itemsTableAddAndSearchModel;
	private ItemsTableListener itemsTableListener;
	protected ItemsDetails itemsDetails;
	
	public ItemsTableAdd() {
		
		itemsTableAddAndSearchModel = new ItemsTableAddAndSearchModel();
		tableItemsAdd = new JTable(itemsTableAddAndSearchModel);
		popupMenu = new JPopupMenu();
		
		menuItemDeleteItem = new JMenuItem("Izbrisati artikl");
		menuItemDetailsItem = new JMenuItem("Detalji artikla");
		popupMenu.add(menuItemDeleteItem);
		popupMenu.add(menuItemDetailsItem);
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(tableItemsAdd), BorderLayout.CENTER);
		
		// mouseListener za desni klik nad klijentom za otvranje popup menu
		tableItemsAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				int row = tableItemsAdd.rowAtPoint(e.getPoint());
				tableItemsAdd.getSelectionModel().setSelectionInterval(row, row);

				if (e.getButton() == MouseEvent.BUTTON3) {
					popupMenu.show(tableItemsAdd, e.getX(), e.getY());
				}
			}
		});
		
		// actionListener za brisnje klijenta
		menuItemDeleteItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int row_index = tableItemsAdd.getSelectedRow();
				int itemCode = (int) tableItemsAdd.getValueAt(row_index, 0);
				
				if (itemsTableListener != null) {
					itemsTableListener.deleteItem(row_index, itemCode);
					itemsTableAddAndSearchModel.fireTableDataChanged();
				}
			}
		});
		
		//actionListener za otvaranje maske artikla
		menuItemDetailsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row_index = tableItemsAdd.getSelectedRow();
				int itemCode = (int) tableItemsAdd.getValueAt(row_index, 0);
				String name = (String) tableItemsAdd.getValueAt(row_index, 1);
				
				itemsDetails = new ItemsDetails(itemCode, name);
				itemsDetails.setVisible(true);
				
				itemsDetails.addWindowListener(new WindowAdapter() {
					@Override
					public void windowOpened(WindowEvent e) {
						menuItemDetailsItem.setEnabled(false);
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						menuItemDetailsItem.setEnabled(true);
					}
				});	
				
			}
		});
		
	}
	
	public void setData(List<ItemsModel> dbItemsModel) {
		itemsTableAddAndSearchModel.setData(dbItemsModel);
	}
	
	public void refresh() {
		itemsTableAddAndSearchModel.fireTableDataChanged();
	}
	
	public void setItemsTableListener(ItemsTableListener itemsTableListener) {
		this.itemsTableListener = itemsTableListener;
	}
}
