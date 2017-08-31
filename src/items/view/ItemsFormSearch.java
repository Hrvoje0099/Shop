package items.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ItemsFormSearch extends JPanel {

	private JLabel lblItemCode;
	private JTextField txtItemCode;
	private JLabel lblName;
	private JTextArea txtAreaName;
	private JLabel lblBarcode;
	private JTextField txtBarcode;
	private JLabel lblSupplier;
	private JComboBox<String> comboBoxSupplier;
	private JButton btnSearch;
	
	private List<String> listSupplier;
	
	private ItemsFormSearchListener itemsFormSearchListener;
	
	public ItemsFormSearch() {
		
		Dimension dim = getPreferredSize();
		dim.width = 360;
		setPreferredSize(dim);
		
		loadComponents();
		layoutComponents();
		
		// ActionListener za 'TRAŽI' button
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int itemCode = 0;
				
				if (txtItemCode.getText().equals("")) {
					itemCode = 0;
				} else if (!(Pattern.matches("^[0-9]+$", txtItemCode.getText()))) {
					JOptionPane.showMessageDialog(null, "ŠIFRA MOŽE SADRŽAVATI SAMO BROJEVE", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else {
					itemCode = Integer.parseInt(txtItemCode.getText());
				}
				
				ItemsTemp item = new ItemsTemp(itemCode, txtAreaName.getText(), txtBarcode.getText(), comboBoxSupplier.getSelectedItem().toString());
				
				if (itemsFormSearchListener != null)
					itemsFormSearchListener.searchItem(item);

			}
		});
		
	}

	public void setItemsFormSearchListener(ItemsFormSearchListener itemsFormSearchListener) {
		this.itemsFormSearchListener = itemsFormSearchListener;
	}
	
	private void loadComponents() {
		
		lblItemCode = new JLabel("Šifra: ");
		txtItemCode = new JTextField(20);
		lblName = new JLabel("Naziv: ");
		txtAreaName = new JTextArea(3, 20);
		txtAreaName.setPreferredSize(new Dimension(0, 0));
		lblBarcode = new JLabel("Barkod: ");
		txtBarcode = new JTextField(20);
		
		lblSupplier = new JLabel("Dobavljač: ");
		comboBoxSupplier = new JComboBox<String>();
		comboBoxSupplier.setPrototypeDisplayValue("qwqwqwqwqwqwqwqwqwqwqwqwq");
		comboBoxSupplier.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				
				if (itemsFormSearchListener != null) {
					
					listSupplier = new LinkedList<String>();
					listSupplier = itemsFormSearchListener.loadSuppliers();
					
					for (int i = 0; i < listSupplier.size(); i++) {
						comboBoxSupplier.addItem(listSupplier.get(i));
					}
					comboBoxSupplier.addItem("");
				}
				comboBoxSupplier.setSelectedItem("");
			}
		});
		comboBoxSupplier.setSelectedItem("test");
			
		btnSearch = new JButton("TRAŽI");
	}
	
	private void layoutComponents() {
		
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		/////////////////////// 1.RED ///////////////////////

		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblItemCode, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtItemCode, gc);
		
		/////////////////////// 2.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblName, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtAreaName, gc);
		
		/////////////////////// 3.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblBarcode, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtBarcode, gc);

		/////////////////////// 4.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblSupplier, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(comboBoxSupplier, gc);
		
		/////////////////////// zadnji.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 2;

		gc.gridx = 1;
		gc.insets = new Insets(10, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(btnSearch, gc);
	}
	
	//kad stisnemo ENTER da reagira na button U�ITAJ
	@Override
    public void addNotify() {
        super.addNotify();
        SwingUtilities.getRootPane(btnSearch).setDefaultButton(btnSearch);
    }
	
}
