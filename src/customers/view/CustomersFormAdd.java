package customers.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import customers.controller.CustomersController;

public class CustomersFormAdd extends JPanel {

	private JLabel lblId;
	private JTextField txtId;
	private JLabel lblName;
	private JTextField txtName;
	private JLabel lblAddress;
	private JTextField txtAddress;
	private JLabel lblCity;
	private JTextField txtCity;
	private JLabel lblZipCode;
	private JTextField txtZipCode;
	private JLabel lblCountry;
	private JComboBox<String> comboBoxCountry;
	private JLabel lblPhone;
	private JTextField txtPhone;
	private JLabel lblFax;
	private JTextField txtFax;
	private JLabel lblMail;
	private JTextField txtMail;
	private JLabel lblMobilePhone;
	private JTextField txtMobilePhone;
	private JLabel lblOib;
	private JTextField txtOib;
	private JLabel lblContract;
	private JTextField txtContract;
	private JLabel lblPerson;
	private JTextField txtPerson;
	private JButton btnSave;
	private JLabel lblNecessarily;

	private CustomersFormAddListener customersFormAddListener;
	
	private CustomersController controller;

	public CustomersFormAdd() {
		
		controller = new CustomersController();
		
		Dimension dim = getPreferredSize();
		dim.width = 360;
		setPreferredSize(dim);

		loadComponents();
		layoutComponents();
		
		// ActionListener za 'SPREMI' button
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (txtName.getText().equals("") || txtAddress.getText().equals("") || txtOib.getText().equals("") || txtId.getText().equals("") || txtCity.getText().equals("")
						|| comboBoxCountry.getSelectedItem().toString().equals("") || txtPerson.getText().equals("") ) {
					JOptionPane.showMessageDialog(null, "NISTE UNJELI SVE PODATKE!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if (!(Pattern.matches("^[0-9]+$", txtId.getText()))) {
					JOptionPane.showMessageDialog(null, "ID MOŽE SADRŽAVATI SAMO BROJEVE", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if (!(Pattern.matches("^[0-9]+$", txtZipCode.getText())) && !(txtZipCode.getText().equals("")) ) {
						JOptionPane.showMessageDialog(null, "POŠTANSKI BROJ MOŽE SADRŽAVATI SAMO BROJEVE", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if (!(Pattern.matches("^[0-9]+$", txtOib.getText()))) {
					JOptionPane.showMessageDialog(null, "OIB MOŽE SADRŽAVATI SAMO BROJEVE", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if ((txtOib.getDocument().getLength() < 11) || (txtOib.getDocument().getLength() > 11)) {
					JOptionPane.showMessageDialog(null, "OIB MORA IMATE 11 BROJEVA!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else {

					CustomersTemp customer = new CustomersTemp(Integer.parseInt(txtId.getText()), txtName.getText(), txtAddress.getText(), txtCity.getText(), txtZipCode.getText(),
							comboBoxCountry.getSelectedItem().toString(), txtPhone.getText(), txtFax.getText(), txtMail.getText(), txtMobilePhone.getText(), txtOib.getText(), txtContract.getText(), txtPerson.getText());

					if (customersFormAddListener != null)
						customersFormAddListener.addCustomer(customer);
				}
			}
		});

	}
	
	public void setCustomersFormAddListener(CustomersFormAddListener customersFormAddListener) {
		this.customersFormAddListener = customersFormAddListener;
	}
	
	private void loadComponents() {
		
		lblId = new JLabel("*Klijent ID: ");
		txtId = new JTextField(20);
		lblName = new JLabel("*Naziv: ");
		txtName = new JTextField(20);
		lblAddress = new JLabel("*Adresa: ");
		txtAddress = new JTextField(20);
		lblCity = new JLabel("*Grad: ");
		txtCity = new JTextField(20);
		lblZipCode = new JLabel("Poštanski broj: ");
		txtZipCode = new JTextField(20);
		
		lblCountry = new JLabel("*Država: ");
		comboBoxCountry = new JComboBox<String>(controller.getAllCountries());
		comboBoxCountry.setPrototypeDisplayValue("width drzavabox label wwwewwwww");
		comboBoxCountry.setSelectedItem("Croatia");
		
		lblPhone = new JLabel("Telefon: ");
		txtPhone = new JTextField(20);
		lblFax = new JLabel("Fax: ");
		txtFax = new JTextField(20);
		lblMail = new JLabel("Mail: ");
		txtMail = new JTextField(20);
		lblMobilePhone = new JLabel("Mobitel: ");
		txtMobilePhone = new JTextField(20);
		lblOib = new JLabel("*OIB: ");
		txtOib = new JTextField(20);
		lblContract = new JLabel("Ugovor: ");
		txtContract = new JTextField(20);
		lblPerson = new JLabel("*Osoba: ");
		txtPerson = new JTextField(20);
		btnSave = new JButton("SPREMI");
		lblNecessarily = new JLabel("(*) obavezna polja");
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
		add(lblId, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtId, gc);

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
		add(txtName, gc);

		/////////////////////// 3.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblAddress, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtAddress, gc);

		/////////////////////// 4.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblCity, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtCity, gc);

		/////////////////////// 5.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblZipCode, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtZipCode, gc);

		/////////////////////// 6.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblCountry, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(comboBoxCountry, gc);

		/////////////////////// 7.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblPhone, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtPhone, gc);

		/////////////////////// 8.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblFax, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtFax, gc);

		/////////////////////// 9.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblMail, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtMail, gc);

		/////////////////////// 10.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblMobilePhone, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtMobilePhone, gc);

		/////////////////////// 11.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblOib, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtOib, gc);

		/////////////////////// 12.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblContract, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtContract, gc);

		/////////////////////// 13.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblPerson, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtPerson, gc);
		
		/////////////////////// 14.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(lblNecessarily, gc);

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
