package other.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import common.Password;
import common.Utility;
import other.controller.OtherController;

public class ChangePasswordGUI extends JFrame {

	private JLabel lblOldPassword;
	private JTextField txtOldPassword;
	private JLabel lblNewPassword;
	private JTextField txtNewPassword;
	private Password password;
	private JButton btnSave;
	
	private File file;
	private static OtherController controller;
	
	private StringWriter errors;

	public ChangePasswordGUI() throws IOException {
		
		controller = new OtherController();
		errors = new StringWriter();

		setTitle("PROMJENA LOZINKE");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 200);
		setLocationRelativeTo(null);
		setResizable(false);

		password = new Password();
		btnSave = new JButton("SPREMI");
		lblOldPassword = new JLabel("STARA LOZINKA: ");
		txtOldPassword = new JTextField(15);
		lblNewPassword = new JLabel("NOVA LOZINKA: ");
		txtNewPassword = new JTextField(15);
		file = new File("password.ser");

		password = Utility.checkPasswordFile();

		// ActionListener za promijenu lozinke
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (txtOldPassword.getText().equals(password.myPassword)) {

					String newPassword = txtNewPassword.getText();
					password.myPassword = newPassword;
					password.setPassword(newPassword);
					try {
						FileOutputStream fos = new FileOutputStream(file);
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(password);
						oos.close();
						fos.close();
					} catch (IOException e1) {
						e1.printStackTrace(new PrintWriter(errors));
						JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
						
						Utility.saveException(e1.getMessage(), errors.toString());
					}
					JOptionPane.showMessageDialog(null, "NOVA LOZINKA: " + password.myPassword, "INFO", JOptionPane.INFORMATION_MESSAGE);

					dispose();

				} else {
					JOptionPane.showMessageDialog(null, "STARA LOZINKA JE KRIVA", "GREŠKA", JOptionPane.ERROR_MESSAGE);
				}
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

		// kreiramo 'MenuBar' - IZBORNIK
		setJMenuBar(createMenuBar());

		layoutComponents();
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu("IZBORNIK");
		JMenuItem oldPasswordItem = new JMenuItem("Prikaži staru lozinku");
		menu.add(oldPasswordItem);
		menuBar.add(menu);

		// ActionListener za 'Postavke'
		oldPasswordItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			    JPanel panel = new JPanel();
			    JPasswordField passField = new JPasswordField(10);
			    panel.add(new JLabel("Upišite glavnu lozinku:"));
			    panel.add(passField);
			    JOptionPane pane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION) {
			        @Override
			        public void selectInitialValue() {
			        	passField.requestFocusInWindow();
			        }
			    };
			    pane.createDialog(null, "UNOS LOZINKE").setVisible(true);
				
				if (passField.getText().equals("hrvoje"))
					JOptionPane.showMessageDialog(null, "STARA LOZINKA JE: " + password.myPassword, "INFO", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "KRIVA LOZINKA", "GREŠKA", JOptionPane.ERROR_MESSAGE);
			}
		});

		return menuBar;
	}

	private void layoutComponents() {
		
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		
		gc.weighty = 1;

		/////////////////////// 1.RED ///////////////////////

		gc.gridy = 0;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 10);
		add(lblOldPassword, gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtOldPassword, gc);

		/////////////////////// 2.RED ///////////////////////

		gc.gridy = 1;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 10);
		add(lblNewPassword, gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		add(txtNewPassword, gc);

		/////////////////////// 3.RED ///////////////////////

		gc.gridy = 2;

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(btnSave, gc);

	}

	//kad stisnemo ENTER da reagira na button PRIJAVA
	@Override
    public void addNotify() {
        super.addNotify();
        SwingUtilities.getRootPane(btnSave).setDefaultButton(btnSave);
    }
}
