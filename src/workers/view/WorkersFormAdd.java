package workers.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class WorkersFormAdd extends JPanel {

	private JLabel lblId;
	private JTextField txtId;
	private JLabel lblName;
	private JTextField txtName;
	private JLabel lblSurname;
	private JTextField txtPrezime;
	private JLabel lblOib;
	private JTextField txtOib;
	private JComboBox<Object> comboBoxBirthYear;
	private JLabel lblPassword;
	private JPasswordField txtPassword;
	private JLabel lblNecessarily;
	private JButton btnSave;

	private JRadioButton radioMale;
	private JRadioButton radioFemale;
	private ButtonGroup groupSex;

	private WorkersFormAddListener workersFormAddListener;

	public WorkersFormAdd() {
		
		Dimension dim = getPreferredSize();
		dim.width = 380;
		setPreferredSize(dim);

		loadComponents();
		layoutComponents();

		// Set up 'Border'
		Border innerBorder = BorderFactory.createTitledBorder("Dodaj Radnika");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		// ActionListener za 'SPREMI' button
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (txtName.getText().equals("") || txtPrezime.getText().equals("") || txtOib.getText().equals("") || txtId.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "NISTE UNJELI SVE PODATKE!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if (!(Pattern.matches("^[0-9]+$", txtId.getText()))) {
					JOptionPane.showMessageDialog(null, "ID MOŽE SADRŽAVATI SAMO BROJEVE", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if (!(Pattern.matches("^[0-9]+$", txtOib.getText()))) {
					JOptionPane.showMessageDialog(null, "OIB MOŽE SADRŽAVATI SAMO BROJEVE", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if ((txtOib.getDocument().getLength() < 11) || (txtOib.getDocument().getLength() > 11)) {
					JOptionPane.showMessageDialog(null, "OIB MORA IMATE 11 BROJEVA!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if (!(Pattern.matches("^[a-zA-Z]+$", txtName.getText())) && (!(Pattern.matches("\\p{IsLatin}+", txtName.getText())))) {
					JOptionPane.showMessageDialog(null, "IME MOŽE SADRŽAVATI SAMO SLOVA", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if (!(Pattern.matches("^[a-zA-Z]+$", txtPrezime.getText())) && (!(Pattern.matches("\\p{IsLatin}+", txtPrezime.getText())))) {
					JOptionPane.showMessageDialog(null, "PREZIME MOŽE SADRŽAVATI SAMO SLOVA", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else {

					WorkersTemp worker = new WorkersTemp(Integer.parseInt(txtId.getText()), txtName.getText(), txtPrezime.getText(), txtOib.getText(), Integer.parseInt(comboBoxBirthYear.getSelectedItem().toString()), groupSex.getSelection().getActionCommand(), txtPassword.getText());

					if (workersFormAddListener != null)
						workersFormAddListener.addWorker(worker);
				}
			}
		});

	}
	
	public void setWorkersFormAddListener(WorkersFormAddListener workersFormAddListener) {
		this.workersFormAddListener = workersFormAddListener;
	}
	
	private void loadComponents() {
		
		lblId = new JLabel("*Radnik ID: ");
		txtId = new JTextField(20);
		lblName = new JLabel("*Ime: ");
		txtName = new JTextField(20);
		lblSurname = new JLabel("*Prezime: ");
		txtPrezime = new JTextField(20);
		lblOib = new JLabel("*OIB: ");
		txtOib = new JTextField(20);
		lblPassword = new JLabel("Lozinka: ");
		txtPassword = new JPasswordField(20);
		lblNecessarily = new JLabel("(*) obavezna polja");
		btnSave = new JButton("SPREMI");

		radioMale = new JRadioButton("muško");
		radioFemale = new JRadioButton("žensko");
		radioMale.setActionCommand("muško");
		radioFemale.setActionCommand("žensko");
		groupSex = new ButtonGroup();
		groupSex.add(radioMale);
		groupSex.add(radioFemale);
		radioMale.setSelected(true);

		ArrayList<String> years_tmp = new ArrayList<String>();
		for (int years = 1940; years <= Calendar.getInstance().get(Calendar.YEAR); years++) {
			years_tmp.add(years + "");
		}
		comboBoxBirthYear = new JComboBox<Object>(years_tmp.toArray());
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
		add(lblSurname, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtPrezime, gc);

		/////////////////////// 4.RED ///////////////////////

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

		/////////////////////// 5.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("*Godina rođenja: "), gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(comboBoxBirthYear, gc);

		/////////////////////// 6.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("*Spol: "), gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(radioMale, gc);

		/////////////////////// 7.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(radioFemale, gc);

		/////////////////////// 8.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(lblPassword, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtPassword, gc);
		
		/////////////////////// 9.RED ///////////////////////

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
