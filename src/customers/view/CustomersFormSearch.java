package customers.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import customers.controller.CustomersController;

public class CustomersFormSearch extends JPanel {

	private JLabel lblName;
	private JTextField txtName;
	private JLabel lblAddress;
	private JTextField txtAddress;
	private JLabel lblCity;
	private JTextField txtCity;
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
	private JButton btnSearch;

	private CustomersFormSearchListener customersFormSearchListener;
	
	private CustomersController controller;

	public CustomersFormSearch() {
		
		controller = new CustomersController();
		
		Dimension dim = getPreferredSize();
		dim.width = 360;
		setPreferredSize(dim);

		loadComponents();
		layoutComponents();
		
		// ActionListener za 'TRAŽI' button
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				CustomersTemp customer = new CustomersTemp(txtName.getText(), txtAddress.getText(), txtCity.getText(), comboBoxCountry.getSelectedItem().toString(),
						txtPhone.getText(), txtFax.getText(), txtMail.getText(), txtMobilePhone.getText(), txtOib.getText(), txtContract.getText(), txtPerson.getText());

				if (customersFormSearchListener != null)
					customersFormSearchListener.searchCustomer(customer);

			}
		});

	}
	
	public void setCustomersFormSearchListener(CustomersFormSearchListener customersFormSearchListener) {
		this.customersFormSearchListener = customersFormSearchListener;
	}
	
	@Override
    public void addNotify() {
        super.addNotify();
        SwingUtilities.getRootPane(btnSearch).setDefaultButton(btnSearch);
    }
	
	private void loadComponents() {
		
		lblName = new JLabel("Naziv: ");
		txtName = new JTextField(20);
		lblAddress = new JLabel("Adresa: ");
		txtAddress = new JTextField(20);
		lblCity = new JLabel("Grad: ");
		txtCity = new JTextField(20);
		
		lblCountry = new JLabel("Država: ");
		comboBoxCountry = new JComboBox<String>(controller.getAllCountriesForSearch());
		comboBoxCountry.setPrototypeDisplayValue("width drzavabox label wwwewwwww");
		comboBoxCountry.setSelectedItem("");
		
		lblPhone = new JLabel("Telefon: ");
		txtPhone = new JTextField(20);
		lblFax = new JLabel("Fax: ");
		txtFax = new JTextField(20);
		lblMail = new JLabel("Mail: ");
		txtMail = new JTextField(20);
		lblMobilePhone = new JLabel("Mobitel: ");
		txtMobilePhone = new JTextField(20);
		lblOib = new JLabel("OIB: ");
		txtOib = new JTextField(20);
		lblContract = new JLabel("Ugovor: ");
		txtContract = new JTextField(20);
		lblPerson = new JLabel("Osoba: ");
		txtPerson = new JTextField(20);
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
		add(lblName, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtName, gc);

		/////////////////////// 2.RED ///////////////////////

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

		/////////////////////// 3.RED ///////////////////////

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

		/////////////////////// 4.RED ///////////////////////

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

		/////////////////////// 5.RED ///////////////////////

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

		/////////////////////// 6.RED ///////////////////////

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

		/////////////////////// 7.RED ///////////////////////

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

		/////////////////////// 8.RED ///////////////////////

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

		/////////////////////// 9.RED ///////////////////////

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

		/////////////////////// 10.RED ///////////////////////

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

		/////////////////////// 11.RED ///////////////////////

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

		/////////////////////// zadnji.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 2;

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(btnSearch, gc);

	}

}