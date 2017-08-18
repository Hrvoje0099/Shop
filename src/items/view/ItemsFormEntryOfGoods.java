package items.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import common.Utility;
import items.controller.ItemsController;

public class ItemsFormEntryOfGoods extends JPanel {
	
	private JLabel lblBarcode;
	private JTextField txtBarcode;
	private JButton btnReload;
	
	private JLabel lblItemCode;
	private JTextField txtItemCode;
	private JLabel lblName;
	private JTextArea txtAreaName;
	private JLabel lblSupplier;
	private JTextField txtSupplier;
	private JLabel lblDiscount;
	private JTextField txtDiscount;
	private JLabel lblTax;
	private JTextField txtTax;
	private JLabel lblUnit;
	private JTextField txtUnit;
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
	private JLabel lblAmountInput;
	private JTextField txtAmountInput;
	private JButton btnAmountInput;
	
	private ItemsController controller;
	private ItemsTemp item;
	private ItemsFormEntryOfGoodsListener itemsFormEntryOfGoodsListener;
	
	private DecimalFormat df;
	
	private StringWriter errors;
	
	public ItemsFormEntryOfGoods() {
		
		controller = new ItemsController();
		errors = new StringWriter();
		df = new DecimalFormat("0.000");
		
		Dimension dim = getPreferredSize();
		dim.width = 400;
		setPreferredSize(dim);
		
		loadComponents();
		layoutComponents();
		
		// ActionListener za UČITAJ button
		btnReload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (txtBarcode.getText().equals("") ) {
					JOptionPane.showMessageDialog(null, "MORATE UNIJETI BARKOD!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if (!(Pattern.matches("^[0-9]+$", txtBarcode.getText()))) {
					JOptionPane.showMessageDialog(null, "BARKOD MOŽE SADRŽAVATI SAMO BROJEVE", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else {
					
					String barcode = txtBarcode.getText();
					
					item = null;
					
					try {
						controller.connect();
						item = controller.loadItemForEntryOfGoods(barcode);
						if (item == null) {
							JOptionPane.showMessageDialog(null, "BARKOD NE POSTOJI!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (Exception e1) {
						e1.printStackTrace(new PrintWriter(errors));
						JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
						
						Utility.saveException(e1.getMessage(), errors.toString());
					}
					
					txtItemCode.setText(String.valueOf(item.getItemCode()));
					txtItemCode.setEnabled(true);
					
					txtAreaName.setText(item.getName());
					txtAreaName.setEnabled(true);
					
					txtSupplier.setText(item.getSupplier());
					txtSupplier.setEnabled(true);
					
					txtDiscount.setText(item.getDiscount());
					txtDiscount.setEnabled(true);
					
					txtTax.setText(item.getTax());
					txtTax.setEnabled(true);
					
					txtUnit.setText(item.getUnit());
					txtUnit.setEnabled(true);
					
					txtPurchaseWP.setText(item.getPurchaseWP());
					txtPurchaseWP.setEnabled(true);
					
					txtPurchaseRP.setText(item.getPurchaseRP());
					txtPurchaseRP.setEnabled(true);
					
					txtSellingWP.setText(item.getSellingWP());
					txtSellingWP.setEnabled(true);
					
					txtSellingRP.setText(item.getSellingRP());
					txtSellingRP.setEnabled(true);
					
					txtMargin.setText(item.getMargin());
					txtMargin.setEnabled(true);
					
					txtAmountInput.setEnabled(true);
					txtAmountInput.setEditable(true);
					
					btnAmountInput.setEnabled(true);	
				}
			}
		});
		
		// ActionListener za ULAZ button
		btnAmountInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (txtAmountInput.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "MORATE UNIJENTI KOLI�INU ULAZA ROBE!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if (!(Pattern.matches("^[0.0-9.9]+$", txtAmountInput.getText().replaceAll(",", ".")))) {
					JOptionPane.showMessageDialog(null, "KOLIČINA ULAZA ROBE MOŽE SADRŽAVATI SAMO BROJEVE", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else {
					
					double amountInput;
					double amountInputFormat;
					try {
						if (item.getUnit().equals("KG")) {
							amountInput = Double.valueOf(txtAmountInput.getText().replaceAll(",", "."));
							amountInputFormat = Double.valueOf(df.format(amountInput).replaceAll(",", "."));
						} else {
							amountInputFormat = Integer.valueOf(txtAmountInput.getText());
						}
					} catch (Exception e1) {
						e1.printStackTrace(new PrintWriter(errors));
						JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
						
						Utility.saveException(e1.getMessage(), errors.toString());
						return;
					}
					
					ItemsTemp itemsTemp = new ItemsTemp(Integer.parseInt(txtItemCode.getText()), txtAreaName.getText(), item.getBarcode1(), item.getBarcode2(), txtSupplier.getText(), txtDiscount.getText(), txtTax.getText(), txtUnit.getText(), txtPurchaseWP.getText(), txtPurchaseRP.getText(), txtSellingWP.getText(), txtSellingRP.getText(), txtMargin.getText(), amountInputFormat);

					if (itemsFormEntryOfGoodsListener != null) 
						itemsFormEntryOfGoodsListener.addItemOnTableForEntryOfGoods(itemsTemp);
				}
			}
		});
		
	}

	public void setItemsFormEntryOfGoodsListener(ItemsFormEntryOfGoodsListener itemsFormEntryOfGoodsListener) {
		this.itemsFormEntryOfGoodsListener = itemsFormEntryOfGoodsListener;
	}
	
	private void loadComponents() {
		
		lblBarcode = new JLabel("Barkod: ");
		txtBarcode = new JTextField(20);
		
		btnReload = new JButton("UČITAJ");
		
		lblItemCode = new JLabel("Šifra artikla: ");
		txtItemCode = new JTextField(20);
		txtItemCode.setEditable(false);
		txtItemCode.setEnabled(false);
		
		lblName = new JLabel("Naziv: ");
		txtAreaName = new JTextArea(3, 20);
		txtAreaName.setPreferredSize(new Dimension(0, 0));
		txtAreaName.setEditable(false);
		txtAreaName.setEnabled(false);
		
		lblSupplier = new JLabel("Dobavljač: ");
		txtSupplier = new JTextField(20);
		txtSupplier.setEditable(false);
		txtSupplier.setEnabled(false);
		
		lblDiscount = new JLabel("Rabat: ");
		txtDiscount = new JTextField(20);
		txtDiscount.setEditable(false);
		txtDiscount.setEnabled(false);
		
		lblTax = new JLabel("Porez: ");
		txtTax = new JTextField(20);
		txtTax.setEditable(false);
		txtTax.setEnabled(false);
		
		lblUnit = new JLabel("Mjera: ");
		txtUnit = new JTextField(20);
		txtUnit.setEditable(false);
		txtUnit.setEnabled(false);
		
		lblPurchaseWP = new JLabel("Nabavna VP cijena: ");
		txtPurchaseWP = new JTextField(20);
		txtPurchaseWP.setEditable(false);
		txtPurchaseWP.setEnabled(false);
		
		lblPurchaseRP = new JLabel("Nabavna MP cijena: ");
		txtPurchaseRP = new JTextField(20);
		txtPurchaseRP.setEditable(false);
		txtPurchaseRP.setEnabled(false);
		
		lblSellingWP = new JLabel("Prodajna VP cijena: ");
		txtSellingWP = new JTextField(20);
		txtSellingWP.setEditable(false);
		txtSellingWP.setEnabled(false);
		
		lblSellingRP = new JLabel("Prodajna MP cijena: ");
		txtSellingRP = new JTextField(20);
		txtSellingRP.setEditable(false);
		txtSellingRP.setEnabled(false);
		
		lblMargin = new JLabel("Marža(%): ");
		txtMargin = new JTextField(20);
		txtMargin.setEditable(false);
		txtMargin.setEnabled(false);
		
		lblAmountInput = new JLabel("Ulaz(kom): ");
		txtAmountInput = new JTextField(20);
		txtAmountInput.setEnabled(false);
		
		btnAmountInput = new JButton("");
		btnAmountInput.setIcon(new ImageIcon(ItemsDetails.class.getResource("/images/right.png")));
		btnAmountInput.setEnabled(false);
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
		add(lblBarcode, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtBarcode, gc);

		/////////////////////// 2.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(btnReload, gc);
		

		/////////////////////// 3.RED ///////////////////////

		gc.gridy++;
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

		/////////////////////// 4.RED ///////////////////////

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
		add(txtSupplier, gc);

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
		add(txtDiscount, gc);

		/////////////////////// 7.RED ///////////////////////

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
		add(txtTax, gc);
		
		/////////////////////// 8.RED ///////////////////////

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
		add(txtUnit, gc);

		/////////////////////// 9.RED ///////////////////////

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

		/////////////////////// 10.RED ///////////////////////

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
		
		/////////////////////// 11.RED ///////////////////////

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
		
		/////////////////////// 12.RED ///////////////////////

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
		
		/////////////////////// 13.RED ///////////////////////

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
		
		/////////////////////// 14.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblAmountInput, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtAmountInput, gc);

		/////////////////////// zadnji.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 2;

		gc.gridx = 1;
		gc.insets = new Insets(10, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(btnAmountInput, gc);

	}
}
