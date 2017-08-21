package cashRegister.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import cashRegister.controller.CashRegisterController;
import common.Utility;
import workers.view.WorkersTemp;

public class CashRegisterLogin extends JFrame {

	private JLabel lblName;
	private JTextField txtName;
	private JLabel lblSurname;
	private JTextField txtSurname;
	private JLabel lblPassword;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	
	private CashRegisterController controller;
	
	private StringWriter errors;

	public CashRegisterLogin() {
		
		controller = new CashRegisterController();
		errors = new StringWriter();

		setTitle("PRIJAVA RADNIKA");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		
		lblName = new JLabel("Ime: ");
		txtName = new JTextField(20);
		lblSurname = new JLabel("Prezime: ");
		txtSurname = new JTextField(20);
		lblPassword = new JLabel("Šifra: ");
		txtPassword = new JPasswordField(20);
		btnLogin = new JButton("PRIJAVA");
		
		
		//ActionListener za 'PRIJAVA' button
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (txtName.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "NISTE UNJELI IME RADNIKA!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else if ( txtSurname.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "NISTE UNJELI PREZIME RADNIKA!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				} else {
					String name = txtName.getText();
					String surname = txtSurname.getText();
					String password = txtPassword.getText();
					
					WorkersTemp worker = new WorkersTemp(name, surname, password);
					
					try {
						controller.connect();
						controller.loadWorkers();
					} catch (Exception e1) {
						e1.printStackTrace(new PrintWriter(errors));
						JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
						
						Utility.saveException(e1.getMessage(), errors.toString());
					}
					
					if (controller.checkWorkerLoginInCashRegister(worker) == true) {						
						CashRegisterGUI cashRegisterGUI = new CashRegisterGUI(name, surname);
						cashRegisterGUI.setVisible(true);
						setVisible(false);
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

		layoutComponents();
	}
	
	//kad stisnemo ENTER da reagira na button PRIJAVA
	@Override
    public void addNotify() {
        super.addNotify();
        SwingUtilities.getRootPane(btnLogin).setDefaultButton(btnLogin);
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
		gc.insets = new Insets(20, 0, 0, 5);
		add(lblName, gc);

		gc.gridx = 1;
		gc.insets = new Insets(20, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtName, gc);

		/////////////////////// 2.RED ///////////////////////

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
		add(txtSurname, gc);

		/////////////////////// 3.RED ///////////////////////

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

		/////////////////////// 4.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 2;

		gc.gridx = 1;
		gc.insets = new Insets(10, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(btnLogin, gc);

	}
	
}
