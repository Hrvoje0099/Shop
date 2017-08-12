package items.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import common.Utility;
import customers.view.CustomersGUI;
import items.controller.ItemsController;
import items.model.TaxCategory;

public class ItemsDetails extends JFrame {

	private JPanel contentPane;
	private JLabel lblItemCode;
	private JTextField txtItemCode;
	private JLabel lblName;
	private JTextArea txtAreaName;
	private JLabel lblSuplier;
	private JComboBox<String> comboBoxSuplier;
	private JLabel lblDiscount;
	private JList<String> listDiscount;
	private JLabel lblTax;
	private JList<TaxCategory> listTax;
	private JLabel lblUnit;
	private JList<String> listUnit;
	private JLabel lblBarcode1;
	private JTextField txtBarcode1;
	private JLabel lblBarcode2;
	private JTextField txtBarcode2;
	private JLabel lblPurchaseWP;
	private JTextField txtPurchaseWP;
	private JLabel lblPurchaseRP;
	private JTextField txtPurchaseRP;
	private JLabel lblSellingWP;
	private JTextField txtSellingWP;
	private JLabel lblSellingRP;
	private JTextField txtSellingRP;
	private JLabel lblMargin;
	private JTextField txtMargin;
	private JLabel lblMessage;
	private JTextArea txtAreaMessage;
	private JLabel lblItemState;
	private JTextField txtItemState;
	private JCheckBox checkBoxBarcode1;
	private JCheckBox checkBoxBarcode2;
	private JButton btnAddSupplier;
	private JButton btnSave;
	private JButton btnRefresh;
	
	private ItemsController controller;
	private ItemsTemp item;
	private List<String> listSuppliera;
	
	private DecimalFormat df;

	private StringWriter errors;

	public ItemsDetails(int itemCode, String name) {
		
		controller = new ItemsController();
		errors = new StringWriter();
		df = new DecimalFormat("#.00");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 625, 515);

		loadComponents();
	
		// load artikla u masku
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				setTitle("" + itemCode + " - " + name);
				loadAndRefresh(itemCode);
			}	
		});
		
		// update artikl na klik gumba SPREMI
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (txtAreaName.getText().equals("") || txtPurchaseWP.getText().equals("") || txtSellingWP.getText().equals("") || txtBarcode1.getText().equals("") ) {
					JOptionPane.showMessageDialog(null, "NISTE UNJELI SVE PODATKE!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else {
					
					ItemsTemp itemsTemp = new ItemsTemp(txtAreaName.getText(), comboBoxSuplier.getSelectedItem().toString(), txtBarcode1.getText(), txtBarcode2.getText(),
							listDiscount.getSelectedValue().toString(), listTax.getSelectedValue().toString(), listUnit.getSelectedValue().toString(),
							txtPurchaseWP.getText(), txtPurchaseRP.getText(), txtSellingWP.getText(), txtSellingRP.getText(), txtMargin.getText(), txtAreaMessage.getText());

					try {
						controller.connect();
						controller.updateItem(itemsTemp, itemCode);
					} catch (Exception e1) {
						e1.printStackTrace(new PrintWriter(errors));
						JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
						
						Utility.saveException(e1.getMessage(), errors.toString());
					}
				}
			}
		});
		
		// dodavanje novog dobavljaca
		btnAddSupplier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomersGUI customersGUI = new CustomersGUI();
				customersGUI.setVisible(true);
			}
		});
		
		// barkod1 chech box
		checkBoxBarcode1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (checkBoxBarcode1.isSelected()) {
					txtBarcode1.setEditable(false);
					checkBoxBarcode1.setText("BARKOD ZAKLJUÈAN");
				}
				else {
					txtBarcode1.setEditable(true);
					checkBoxBarcode1.setText("BARKOD OTKLJUÈAN");
				}
			}
		});
		
		// barkod2 chech box
		checkBoxBarcode2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (checkBoxBarcode2.isSelected()) {
					txtBarcode2.setEditable(false);
					checkBoxBarcode2.setText("BARKOD ZAKLJUÈAN");
				}
				else {
					txtBarcode2.setEditable(true);
					checkBoxBarcode2.setText("BARKOD OTKLJUÈAN");
				}
			}
		});
		
		// refresh button
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadAndRefresh(itemCode);
			}
		});
		
		// disconnect
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				controller.disconnect();
				dispose();
				System.gc();
			}
		});
	}
	
	private void loadComponents() {
		
		lblItemCode = new JLabel("\u0160ifra:");
		lblItemCode.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblItemCode.setBounds(454, 11, 46, 14);
		contentPane.add(lblItemCode);
		
		lblName = new JLabel("Naziv:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblName.setBounds(10, 11, 46, 14);
		contentPane.add(lblName);
		
		lblBarcode1 = new JLabel("Barkod 1:");
		lblBarcode1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBarcode1.setBounds(10, 146, 65, 14);
		contentPane.add(lblBarcode1);
		
		lblBarcode2 = new JLabel("Barkod 2:");
		lblBarcode2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBarcode2.setBounds(10, 177, 65, 14);
		contentPane.add(lblBarcode2);
		
		lblSuplier = new JLabel("Dobavlja\u010D:");
		lblSuplier.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSuplier.setBounds(10, 105, 65, 14);
		contentPane.add(lblSuplier);
		
		lblTax = new JLabel("Porez:");
		lblTax.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTax.setBounds(10, 209, 40, 14);
		contentPane.add(lblTax);
		
		lblPurchaseWP = new JLabel("Nabavna VP cijena:");
		lblPurchaseWP.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPurchaseWP.setBounds(10, 287, 115, 14);
		contentPane.add(lblPurchaseWP);
		
		comboBoxSuplier = new JComboBox<String>();
		comboBoxSuplier.setBounds(85, 100, 250, 25);			
		contentPane.add(comboBoxSuplier);
		
		txtAreaName = new JTextArea();
		txtAreaName.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtAreaName.setBounds(85, 11, 332, 65);
		txtAreaName.setPreferredSize(new Dimension(0, 0));
		contentPane.add(txtAreaName);
		
		txtItemCode = new JTextField();
		txtItemCode.setEditable(false);
		txtItemCode.setBounds(510, 6, 100, 25);
		contentPane.add(txtItemCode);
		txtItemCode.setColumns(10);
		txtItemCode.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		
		btnAddSupplier = new JButton("DODAJ DOBAVLJA\u010CA");
		btnAddSupplier.setBounds(345, 100, 265, 25);
		contentPane.add(btnAddSupplier);
		
		txtBarcode1 = new JTextField();
		txtBarcode1.setEditable(false);
		txtBarcode1.setBounds(85, 143, 250, 25);
		contentPane.add(txtBarcode1);
		txtBarcode1.setColumns(10);
		
		txtBarcode2 = new JTextField();
		txtBarcode2.setEditable(false);
		txtBarcode2.setBounds(84, 174, 251, 25);
		contentPane.add(txtBarcode2);
		txtBarcode2.setColumns(10);
		
		checkBoxBarcode1 = new JCheckBox("BARKOD ZAKLJUÈAN");
		checkBoxBarcode1.setSelected(true);
		checkBoxBarcode1.setBounds(345, 141, 155, 25);
		contentPane.add(checkBoxBarcode1);
		
		checkBoxBarcode2 = new JCheckBox("BARKOD ZAKLJUÈAN");
		checkBoxBarcode2.setSelected(true);
		checkBoxBarcode2.setBounds(345, 174, 155, 25);
		contentPane.add(checkBoxBarcode2);
		
		listTax = new JList<TaxCategory>();
		DefaultListModel<TaxCategory> listModelTax = new DefaultListModel<TaxCategory>();
		listModelTax.addElement(new TaxCategory(0, 1.05, "P5"));
		listModelTax.addElement(new TaxCategory(1, 1.13, "P13"));
		listModelTax.addElement(new TaxCategory(2, 1.25, "P25"));
		listTax.setModel(listModelTax);
		listTax.setBounds(85, 208, 40, 60);
		listTax.setSelectionBackground(Color.cyan);
		contentPane.add(listTax);
		
		listTax.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				calculateWithTaxPurchaseaPrice();
				calculateWithTaxSellingPrice();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {}		
			@Override
			public void mouseExited(MouseEvent e) {}		
			@Override
			public void mouseEntered(MouseEvent e) {}			
			@Override
			public void mouseClicked(MouseEvent e) {}
			
		});	
		
		lblDiscount = new JLabel("Rabat:");
		lblDiscount.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDiscount.setBounds(135, 209, 46, 14);
		contentPane.add(lblDiscount);
		
		listDiscount = new JList<String>();
		listDiscount.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {"DA", "NE"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		listDiscount.setSelectedIndex(0);
		listDiscount.setBounds(191, 208, 40, 40);
		listDiscount.setSelectionBackground(Color.cyan);
		contentPane.add(listDiscount);
		
		lblUnit = new JLabel("Mjera:");
		lblUnit.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUnit.setBounds(239, 209, 46, 14);
		contentPane.add(lblUnit);
		
		listUnit = new JList<String>();
		listUnit.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {"KOM", "KG"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		listUnit.setSelectedIndex(0);
		listUnit.setBounds(295, 208, 40, 40);
		listUnit.setSelectionBackground(Color.cyan);
		contentPane.add(listUnit);
		
		lblPurchaseRP = new JLabel("Nabavna MP cijena:");
		lblPurchaseRP.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPurchaseRP.setBounds(10, 323, 115, 14);
		contentPane.add(lblPurchaseRP);
		
		txtPurchaseWP = new JTextField();
		txtPurchaseWP.setBounds(135, 282, 60, 25);
		contentPane.add(txtPurchaseWP);
		txtPurchaseWP.setColumns(10);
		
		txtPurchaseRP = new JTextField();
		txtPurchaseRP.setEditable(false);
		txtPurchaseRP.setBounds(135, 318, 60, 25);
		contentPane.add(txtPurchaseRP);
		txtPurchaseRP.setColumns(10);
		
		txtPurchaseWP.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				calculateWithTaxPurchaseaPrice();
				calculateMargin();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				calculateWithTaxPurchaseaPrice();
				calculateMargin();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}

		});
			
		txtSellingWP = new JTextField();
		txtSellingWP.setColumns(10);
		txtSellingWP.setBounds(331, 282, 60, 25);
		contentPane.add(txtSellingWP);
		
		txtSellingRP = new JTextField();
		txtSellingRP.setEditable(false);
		txtSellingRP.setColumns(10);
		txtSellingRP.setBounds(330, 318, 60, 25);
		contentPane.add(txtSellingRP);
		
		txtSellingWP.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				calculateWithTaxSellingPrice();
				calculateMargin();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				calculateWithTaxSellingPrice();
				calculateMargin();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}

		});
				
		lblSellingWP = new JLabel("Prodajna VP cijena:");
		lblSellingWP.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSellingWP.setBounds(205, 287, 115, 14);
		contentPane.add(lblSellingWP);
		
		lblSellingRP = new JLabel("Prodajna MP cijena:");
		lblSellingRP.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSellingRP.setBounds(205, 323, 115, 14);
		contentPane.add(lblSellingRP);
		
		lblMargin = new JLabel("Mar\u017Ea(%):");
		lblMargin.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMargin.setBounds(414, 305, 70, 14);
		contentPane.add(lblMargin);
		
		txtMargin = new JTextField();
		txtMargin.setEditable(false);
		txtMargin.setBounds(494, 300, 105, 25);
		contentPane.add(txtMargin);
		txtMargin.setColumns(10);
		
		lblMessage = new JLabel("Poruka:");
		lblMessage.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMessage.setBounds(10, 364, 46, 14);
		contentPane.add(lblMessage);
		
		txtAreaMessage = new JTextArea();
		txtAreaMessage.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtAreaMessage.setBounds(66, 364, 544, 65);
		contentPane.add(txtAreaMessage);
		
		btnSave = new JButton("SPREMI");
		btnSave.setIcon(new ImageIcon(ItemsDetails.class.getResource("/images/Save16.gif")));
		btnSave.setBounds(454, 440, 156, 34);
		contentPane.add(btnSave);
		
		btnRefresh = new JButton("REFRESH");
		btnRefresh.setIcon(new ImageIcon(ItemsDetails.class.getResource("/images/Refresh16.gif")));
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRefresh.setBounds(454, 42, 156, 34);
		contentPane.add(btnRefresh);
		
		lblItemState = new JLabel("Stanje:");
		lblItemState.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblItemState.setBounds(10, 445, 46, 14);
		contentPane.add(lblItemState);
		
		txtItemState = new JTextField();
		txtItemState.setEditable(false);
		txtItemState.setBounds(66, 440, 86, 25);
		contentPane.add(txtItemState);
		txtItemState.setColumns(10);
	}
		
	private void loadAndRefresh(int itemCode) {
		
		item = null;
		
		try {
			controller.connect();
			item = controller.loadItemDetails(itemCode);
			
			listSuppliera = new LinkedList<String>();
			
			listSuppliera = controller.loadSuppliers();
			
			for (int i = 0; i < listSuppliera.size(); i++) {
				if (!(listSuppliera.get(i).equals(comboBoxSuplier.getItemAt(i))))
					comboBoxSuplier.addItem(listSuppliera.get(i));
			}
			
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
		
		txtAreaName.setText(item.getName());
		
		txtItemCode.setText(String.valueOf(item.getItemCode()));
		
		comboBoxSuplier.getModel().setSelectedItem(item.getSupplier());
		
		txtBarcode1.setText(item.getBarcode1());
		txtBarcode2.setText(item.getBarcode2());
		
		if (item.getDiscount().equals("NE"))
			listDiscount.setSelectedIndex(1);
		else
			listDiscount.setSelectedIndex(0);
		
		if (item.getTax().equals("P25"))
			listTax.setSelectedIndex(2);
		else if (item.getTax().equals("P13"))
			listTax.setSelectedIndex(1);
		else
			listTax.setSelectedIndex(0);
		
		if (item.getUnit().equals("KOM"))
			listUnit.setSelectedIndex(0);
		else
			listUnit.setSelectedIndex(1);
		
		txtPurchaseWP.setText(item.getPurchaseWP());
		txtPurchaseRP.setText(item.getPurchaseRP());
		txtSellingWP.setText(item.getSellingWP());
		txtSellingRP.setText(item.getSellingRP());
		txtMargin.setText(item.getMargin());
		txtAreaMessage.setText(item.getMessage());
		txtItemState.setText(String.valueOf(item.getItemState()));
	}

	private void calculateMargin() {
		try {
			if ((!txtSellingRP.getText().trim().isEmpty()) && (!txtPurchaseRP.getText().trim().isEmpty())) {
				double sellingPrice = Double.parseDouble(txtSellingRP.getText().replace(",", "."));
				double purchasePrice = Double.parseDouble(txtPurchaseRP.getText().replace(",", "."));
				double margin = (((sellingPrice - purchasePrice) / purchasePrice) * 100);
				txtMargin.setText(String.valueOf(df.format(margin)));
			} else
				txtMargin.setText(null);
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
	}
	
	private void calculateWithTaxPurchaseaPrice() {
		try {
			if (!txtPurchaseWP.getText().trim().isEmpty()) {
				double tax = listTax.getSelectedValue().getVat();
				double priceWithTax = Double.parseDouble(txtPurchaseWP.getText().replace(",", ".")) * tax;
				txtPurchaseRP.setText(String.valueOf(df.format(priceWithTax)));
			} else
				txtPurchaseRP.setText(null);
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
	}
	
	private void calculateWithTaxSellingPrice() {
		try {
			if (!txtSellingWP.getText().trim().isEmpty()) {
				double tax = listTax.getSelectedValue().getVat();
				double priceWithTax = Double.parseDouble(txtSellingWP.getText().replace(",", ".")) * tax;
				txtSellingRP.setText(String.valueOf(df.format(priceWithTax)));
			} else
				txtSellingRP.setText(null);
		} catch (Exception e1) {
			e1.printStackTrace(new PrintWriter(errors));
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
			
			Utility.saveException(e1.getMessage(), errors.toString());
		}
	}
}
