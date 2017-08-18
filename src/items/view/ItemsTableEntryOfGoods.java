package items.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import common.Utility;
import items.controller.ItemsController;
import items.model.EntryOfGoodsModel;
import items.model.ItemsTableEntryOfGoodsModel;

public class ItemsTableEntryOfGoods extends JPanel {
	
	protected JTable tableEntryOfGoods;
	private JButton btnSave;
	
	private ItemsTableEntryOfGoodsModel itemsTableEntryOfGoodsModel;
	private ItemsTableEntryOfGoodsListener itemsTableEntryOfGoodsListener;
	
	private ItemsController controller;
	
	private StringWriter errors;
	
	public ItemsTableEntryOfGoods() {
		
		controller = new ItemsController();
		errors = new StringWriter();
		
		itemsTableEntryOfGoodsModel = new ItemsTableEntryOfGoodsModel();
		tableEntryOfGoods = new JTable(itemsTableEntryOfGoodsModel);
		
		btnSave = new JButton("SPREMI");
		
		setLayout(new BorderLayout());
		add(new JScrollPane(tableEntryOfGoods), BorderLayout.CENTER);
		add(btnSave, BorderLayout.PAGE_END);
		
		// ActionListener za button SPREMI
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					controller.connect();
					
					for (int i = 0; i < tableEntryOfGoods.getRowCount(); i++) {
						int itemCode = Integer.valueOf(tableEntryOfGoods.getValueAt(i, 0).toString());
						double amountInput = Double.valueOf(tableEntryOfGoods.getValueAt(i, 13).toString());
						
						controller.addToState(itemCode, amountInput);
					}
					JOptionPane.showMessageDialog(null, "KOLIČINE USPIJEŠNO DODANE NA STANJE!", "INFO", JOptionPane.INFORMATION_MESSAGE);

					if (itemsTableEntryOfGoodsListener != null) 
						itemsTableEntryOfGoodsListener.cleanEntryOfGoodsTableAfterSave();
					
				} catch (Exception e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e1.getMessage(), errors.toString());
				}
			}
		});
		
	}

	public void setItemsTableEntryOfGoodsListener(ItemsTableEntryOfGoodsListener itemsTableEntryOfGoodsListener) {
		this.itemsTableEntryOfGoodsListener = itemsTableEntryOfGoodsListener;
	}

	public void setData(List<EntryOfGoodsModel> dbEntryOfGoodsModel) {
		itemsTableEntryOfGoodsModel.setData(dbEntryOfGoodsModel);
	}
	
	public void refresh() {
		itemsTableEntryOfGoodsModel.fireTableDataChanged();
	}
}
