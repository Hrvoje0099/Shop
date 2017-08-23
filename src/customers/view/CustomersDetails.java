package customers.view;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Pattern;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import common.Utility;
import customers.controller.CustomersController;

public class CustomersDetails extends JFrame {

	private JLabel lblId;
	private JTextField txtID;
	private JLabel lblName;
	private JTextField txtName;
	private JLabel lblAdrress;
	private JTextField txtAddress;
	private JLabel lblCity;
	private JTextField txtCity;
	private JLabel lblZipCode;
	private JTextField txtZipCode;
	private JLabel lblCountry;
	private JComboBox<String> comboBoxCountry;
	private JLabel lblOib;
	private JTextField txtOib;
	private JLabel lblPhone;
	private JTextField txtPhone;
	private JLabel lblFax;
	private JTextField txtFax;
	private JLabel lblMobilePhone;
	private JTextField txtMobilePhone;
	private JLabel lblMail;
	private JTextField txtMail;
	private JLabel lblPerson;
	private JTextField txtPerson;
	private JLabel lblContract;
	private JTextField txtContract;
	private JLabel lblMessage;
	private JTextArea txtAreaMessage;
	private JButton btnSave;

	private JPanel contentPane;
	protected CustomersController controller;
	
	
	private StringWriter errors;

	public CustomersDetails(int customerId, String name) {
		
		controller = new CustomersController();
		errors = new StringWriter();
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 500);

		loadComponents();

		// load klijenta u masku
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				setTitle("" + name);

				CustomersTemp customersTemp = null;

				try {
					customersTemp = controller.loadCustomerDetails(customerId);
				} catch (Exception e1) {
					e1.printStackTrace(new PrintWriter(errors));
					JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
					
					Utility.saveException(e1.getMessage(), errors.toString());
				}

				txtID.setText(String.valueOf(customersTemp.getId()));
				txtName.setText(customersTemp.getName());
				txtAddress.setText(customersTemp.getAddress());
				txtCity.setText(customersTemp.getCity());
				txtZipCode.setText(String.valueOf(customersTemp.getZipCode()));
				comboBoxCountry.getModel().setSelectedItem(customersTemp.getCountry());
				txtPhone.setText(customersTemp.getPhone());
				txtFax.setText(customersTemp.getFax());
				txtMail.setText(customersTemp.getMail());
				txtMobilePhone.setText(customersTemp.getMobilePhone());
				txtOib.setText(customersTemp.getOib());
				txtContract.setText(customersTemp.getContract());
				txtPerson.setText(customersTemp.getPerson());
				txtAreaMessage.setText(customersTemp.getMessage());
			}
		});

		// update klijenta na klik gumba SPREMI
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (txtName.getText().equals("") || txtAddress.getText().equals("") || txtOib.getText().equals("") || txtID.getText().equals("") || txtCity.getText().equals("")
						|| comboBoxCountry.getSelectedItem().toString().equals("") || txtPerson.getText().equals("") ) {
					JOptionPane.showMessageDialog(null, "NISTE UNJELI SVE PODATKE!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if ( !(Pattern.matches("^[0-9]+$", txtZipCode.getText())) && !(txtZipCode.getText().equals("")) ) {
					JOptionPane.showMessageDialog(null, "POŠTANSKI BROJ MOŽE SADRŽAVATI SAMO BROJEVE", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else {
			
					CustomersTemp customer = new CustomersTemp(txtName.getText(), txtAddress.getText(), txtCity.getText(), txtZipCode.getText(), comboBoxCountry.getSelectedItem().toString(), txtPhone.getText(), txtFax.getText(), txtMail.getText(), txtMobilePhone.getText(), txtContract.getText(), txtPerson.getText(), txtAreaMessage.getText());

					try {
						controller.updateCustomer(customer, customerId);
					} catch (Exception e1) {
						e1.printStackTrace(new PrintWriter(errors));
						JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
						
						Utility.saveException(e1.getMessage(), errors.toString());					
					}
				}
			}
		});

		// disconnect
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				controller.disconnect();
				dispose();
			}
		});

	}
	
	private void loadComponents() {
		
		controller.connect();
		
		lblId = new JLabel("ID:");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 11));

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setColumns(10);

		lblName = new JLabel("Naziv:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 11));

		txtName = new JTextField();
		txtName.setColumns(10);

		lblAdrress = new JLabel("Adresa:");
		lblAdrress.setFont(new Font("Tahoma", Font.BOLD, 11));

		txtAddress = new JTextField();
		txtAddress.setColumns(10);

		lblCity = new JLabel("Grad:");
		lblCity.setFont(new Font("Tahoma", Font.BOLD, 11));

		txtCity = new JTextField();
		txtCity.setColumns(10);

		lblZipCode = new JLabel("Poštanski br:");
		lblZipCode.setFont(new Font("Tahoma", Font.BOLD, 11));

		txtZipCode = new JTextField();
		txtZipCode.setColumns(10);

		lblCountry = new JLabel("Država:");
		lblCountry.setFont(new Font("Tahoma", Font.BOLD, 11));
		comboBoxCountry = new JComboBox<String>(controller.getAllCountries());

		lblOib = new JLabel("OIB:");
		lblOib.setFont(new Font("Tahoma", Font.BOLD, 11));

		txtOib = new JTextField();
		txtOib.setEditable(false);
		txtOib.setColumns(10);

		lblPhone = new JLabel("Telefon:");
		lblPhone.setFont(new Font("Tahoma", Font.BOLD, 11));

		txtPhone = new JTextField();
		txtPhone.setColumns(10);

		lblFax = new JLabel("Fax:");
		lblFax.setFont(new Font("Tahoma", Font.BOLD, 11));

		txtFax = new JTextField();
		txtFax.setAlignmentY(Component.TOP_ALIGNMENT);
		txtFax.setColumns(10);

		lblMobilePhone = new JLabel("Mobitel:");
		lblMobilePhone.setFont(new Font("Tahoma", Font.BOLD, 11));

		txtMobilePhone = new JTextField();
		txtMobilePhone.setColumns(10);

		lblMail = new JLabel("Mail:");
		lblMail.setFont(new Font("Tahoma", Font.BOLD, 11));

		txtMail = new JTextField();
		txtMail.setColumns(10);

		lblPerson = new JLabel("Osoba:");
		lblPerson.setFont(new Font("Tahoma", Font.BOLD, 11));

		txtPerson = new JTextField();
		txtPerson.setColumns(10);

		lblContract = new JLabel("Ugovor:");
		lblContract.setFont(new Font("Tahoma", Font.BOLD, 11));

		txtContract = new JTextField();
		txtContract.setColumns(10);

		txtAreaMessage = new JTextArea();
		txtAreaMessage.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		lblMessage = new JLabel("Poruka:");
		lblMessage.setFont(new Font("Tahoma", Font.BOLD, 11));

		btnSave = new JButton("SPREMI");
		btnSave.setAlignmentX(0.5f);
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPhone)
						.addComponent(lblAdrress)
						.addComponent(lblCity)
						.addComponent(lblId)
						.addComponent(lblPerson))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtID, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblName)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtName, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtCity, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblCountry)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBoxCountry, 0, 320, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(txtPerson, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
									.addGap(10))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(txtAddress, GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
									.addGap(18)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblOib)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtOib, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblZipCode)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtZipCode, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
							.addGap(51)
							.addComponent(lblFax)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtFax, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
							.addComponent(lblMobilePhone)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtMobilePhone, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)))
					.addGap(10))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblContract)
						.addComponent(lblMessage))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtContract, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblMail)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtMail, GroupLayout.PREFERRED_SIZE, 305, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtAreaMessage))
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(649, Short.MAX_VALUE)
					.addComponent(btnSave)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblId)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblName))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAdrress)
						.addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtZipCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblZipCode))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCity)
						.addComponent(txtCity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCountry)
						.addComponent(comboBoxCountry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtOib, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblOib))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(6)
								.addComponent(lblPerson)))
						.addComponent(txtPerson, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblPhone)
							.addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtFax, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblFax))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(txtMobilePhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblMobilePhone)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblContract)
						.addComponent(txtContract, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtMail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMail))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtAreaMessage, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMessage))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnSave)
					.addGap(54))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
}
