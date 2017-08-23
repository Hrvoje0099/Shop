package items.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import common.Utility;
import items.model.TaxCategory;

public class ItemsFormAdd extends JPanel {
	
	private JLabel lblItemCode;
	private JTextField txtItemCode;
	private JLabel lblName;
	private JTextArea txtAreaName;
	private JLabel lblBarcode1;
	private JTextField txtBarcode1;
	private JLabel lblBarcode2;
	private JTextField txtBarcode2;
	private JLabel lblSupplier;
	private JComboBox<String> comboBoxSupplier;
	
	private JLabel lblDiscount;
	private JRadioButton radioYes;
	private JRadioButton radioNo;
	private ButtonGroup groupDiscount;
	
	private JLabel lblTax;
	private JList<TaxCategory> listTax;
	
	private JLabel lblUnit;
	private JRadioButton radioPcs;
	private JRadioButton radioKg;
	private ButtonGroup groupUnit;
	
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
	private JButton btnSave;
	
	private DecimalFormat df;
	private ItemsFormAddListener itemsFormAddListener;
	
	private List<String> listSupplier;
	
	private StringWriter errors;

	public ItemsFormAdd() {
		
		errors = new StringWriter();
		df = new DecimalFormat("#.00");
		
		Dimension dim = getPreferredSize();
		dim.width = 400;
		setPreferredSize(dim);
		
		loadComponents();
		layoutComponents();
		
		// ActionListener za 'SPREMI' button
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (txtItemCode.getText().equals("") || txtAreaName.getText().equals("") || txtBarcode1.getText().equals("") || txtPurchaseWP.getText().equals("") || txtSellingWP.getText().equals("") ) {
					JOptionPane.showMessageDialog(null, "NISTE UNJELI SVE PODATKE!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if (txtItemCode.getText().equals("0")) {
					JOptionPane.showMessageDialog(null, "ŠIFRA NE MOŽE BITI BROJ 0!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if (!(Pattern.matches("^[0-9]+$", txtItemCode.getText()))) {
					JOptionPane.showMessageDialog(null, "ŠIFRA MOŽE SADRŽAVATI SAMO BROJEVE", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if (!(Pattern.matches("^[0-9]+$", txtBarcode1.getText()))) {
					JOptionPane.showMessageDialog(null, "BARKOD 1 MOŽE SADRŽAVATI SAMO BROJEVE", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if ( !(Pattern.matches("^[0-9]+$", txtBarcode2.getText())) && !(txtBarcode2.getText().equals("")) ) {
					JOptionPane.showMessageDialog(null, "BARKOD 2 MOŽE SADRŽAVATI SAMO BROJEVE", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if ((txtItemCode.getDocument().getLength() < 6) || (txtItemCode.getDocument().getLength() > 6)) {
					JOptionPane.showMessageDialog(null, "ŠIFRA MORA IMATE 6 BROJEVA!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else {
					
					ItemsTemp item = new ItemsTemp(Integer.parseInt(txtItemCode.getText()), txtAreaName.getText(), txtBarcode1.getText(), txtBarcode2.getText(),
							comboBoxSupplier.getSelectedItem().toString(), groupDiscount.getSelection().getActionCommand(), listTax.getSelectedValue().toString(),
							groupUnit.getSelection().getActionCommand(), txtPurchaseWP.getText(), txtPurchaseRP.getText(), txtSellingWP.getText(), txtSellingRP.getText(), txtMargin.getText());
				
					if (itemsFormAddListener != null)
						itemsFormAddListener.addItem(item);
				}
				
			}
		});
		
	}
	
	public void setItemsFormAddListener(ItemsFormAddListener itemsFormAddListener) {
		this.itemsFormAddListener = itemsFormAddListener;
	}
	
	private void loadComponents() {
		
		lblItemCode = new JLabel("Šifra artikla: ");
		txtItemCode = new JTextField(20);
		
		lblName = new JLabel("Naziv: ");
		txtAreaName = new JTextArea(3, 20);
		txtAreaName.setPreferredSize(new Dimension(0, 0));
		
		lblBarcode1 = new JLabel("Barkod 1: ");
		txtBarcode1 = new JTextField(20);
		
		lblBarcode2 = new JLabel("Barkod 2: ");
		txtBarcode2 = new JTextField(20);

		lblSupplier = new JLabel("Dobavljač: ");
		comboBoxSupplier = new JComboBox<String>();
		comboBoxSupplier.setPrototypeDisplayValue("qwqwqwqwqwqwqwqwqwqwqwqwq");
		comboBoxSupplier.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				
				if (itemsFormAddListener != null) {
					
					listSupplier = new LinkedList<String>();
					listSupplier = itemsFormAddListener.loadSuppliers();
					
					for (int i = 0; i < listSupplier.size(); i++) {
						comboBoxSupplier.addItem(listSupplier.get(i));
					}
				}
			}
		});
		
		lblDiscount = new JLabel("Rabat: ");
		radioNo = new JRadioButton("NE");
		radioYes = new JRadioButton("DA");
		groupDiscount = new ButtonGroup();
		groupDiscount.add(radioNo);
		groupDiscount.add(radioYes);
		radioNo.setSelected(true);
		radioNo.setActionCommand("ne");
		radioYes.setActionCommand("da");
		
		lblTax = new JLabel("Porez: ");
		listTax = new JList<TaxCategory>();
		DefaultListModel<TaxCategory> porezModel = new DefaultListModel<TaxCategory>();
		porezModel.addElement(new TaxCategory(0, 1.05, "P5"));
		porezModel.addElement(new TaxCategory(1, 1.13, "P13"));
		porezModel.addElement(new TaxCategory(2, 1.25, "P25"));
		listTax.setModel(porezModel);
		listTax.setPreferredSize(new Dimension(60, 65));
		listTax.setBorder(BorderFactory.createEtchedBorder());
		listTax.setSelectedIndex(2);
		listTax.setSelectionBackground(Color.cyan);

		lblUnit = new JLabel("Mjera: ");
		radioPcs = new JRadioButton("KOM");
		radioKg = new JRadioButton("KG");
		groupUnit = new ButtonGroup();
		groupUnit.add(radioPcs);
		groupUnit.add(radioKg);
		radioPcs.setSelected(true);
		radioPcs.setActionCommand("kom");
		radioKg.setActionCommand("kg");
		
		lblPurchaseWP = new JLabel("Nabavna VP cijena: ");
		txtPurchaseWP = new JTextField(20);	
		lblPurchaseRP = new JLabel("Nabavna MP cijena: ");
		txtPurchaseRP = new JTextField(20);
		txtPurchaseRP.setEditable(false);

		txtPurchaseWP.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				calculateWithTaxPurchasePrice();
				calculateMargin();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				calculateWithTaxPurchasePrice();
				calculateMargin();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}

			private void calculateWithTaxPurchasePrice() {
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
		});

		lblSellingWP = new JLabel("Prodajna VP cijena: ");
		txtSellingWP = new JTextField(20);
		lblSellingRP = new JLabel("Prodajna MP cijena: ");
		txtSellingRP = new JTextField(20);
		txtSellingRP.setEditable(false);
		
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
			public void changedUpdate(DocumentEvent e) {
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
		});
		
		lblMargin = new JLabel("Marža(%): ");
		txtMargin = new JTextField(20);
		txtMargin.setEditable(false);
		
		btnSave = new JButton("SPREMI");
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
		add(lblBarcode1, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtBarcode1, gc);
		
		/////////////////////// 4.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblBarcode2, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtBarcode2, gc);

		/////////////////////// 5.RED ///////////////////////

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

		/////////////////////// 6.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblDiscount, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(radioNo, gc);
		
		/////////////////////// 7.RED ///////////////////////
		
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(radioYes, gc);

		/////////////////////// 8.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblTax, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(listTax, gc);

		/////////////////////// 9.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblUnit, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(radioPcs, gc);
		
		/////////////////////// 10.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(radioKg, gc);

		/////////////////////// 11.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblPurchaseWP, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtPurchaseWP, gc);

		/////////////////////// 12.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblPurchaseRP, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtPurchaseRP, gc);
		
		/////////////////////// 13.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblSellingWP, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtSellingWP, gc);
		
		/////////////////////// 14.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblSellingRP, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtSellingRP, gc);
		
		/////////////////////// 15.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblMargin, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtMargin, gc);

		/////////////////////// zadnji.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 2;

		gc.gridx = 1;
		gc.insets = new Insets(10, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(btnSave, gc);

	}

}
