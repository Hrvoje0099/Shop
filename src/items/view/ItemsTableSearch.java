package items.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import items.model.ItemsModel;
import items.model.ItemsTableAddAndSearchModel;

public class ItemsTableSearch extends JPanel {
	
	private JTable tableSearchItem;
	private ItemsTableAddAndSearchModel itemsTableAddAndSearchModel;
	
	public ItemsTableSearch() {
		
		itemsTableAddAndSearchModel = new ItemsTableAddAndSearchModel();
		tableSearchItem = new JTable(itemsTableAddAndSearchModel);
		
		setLayout(new BorderLayout());
		
		add(new JScrollPane(tableSearchItem), BorderLayout.CENTER);
	}
	
	public void setData(List<ItemsModel> dbItemsModel) {
		itemsTableAddAndSearchModel.setData(dbItemsModel);
	}
	
	public void refresh() {
		itemsTableAddAndSearchModel.fireTableDataChanged();
	}
}
