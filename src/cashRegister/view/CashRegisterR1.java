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
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import cashRegister.controller.CashRegisterController;
import common.Utility;

public class CashRegisterR1 extends JFrame {

	private JLabel lblCustomers;
	private JComboBox<String> comboBoxCustomers;
	private JButton btnOk;
	
	private CashRegisterController controller;
	private List<String> listCustomer;
	private CashRegisterListeners cashRegisterListeners;
	
	private StringWriter errors;
	
	public CashRegisterR1() {
		
		controller = new CashRegisterController();
		errors = new StringWriter();
		
		setTitle("POPIS KLIJENATA");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 130);
		setResizable(false);
		setLocationRelativeTo(null);
		
		lblCustomers = new JLabel("Klijenti: ");
		comboBoxCustomers = new JComboBox<String>();
		btnOk = new JButton("OK");
		
		// ActionListener za OK button kod postavljanja R1 kupca
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cashRegisterListeners != null)
					cashRegisterListeners.setR1Customera(comboBoxCustomers.getSelectedItem().toString());
				dispose();
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
		
		loadCustomers();		
		layoutComponents();
	}
	
	public void setCashRegisterListeners(CashRegisterListeners cashRegisterListener) {
		this.cashRegisterListeners = cashRegisterListener;
	}
	
	//kad stisnemo ENTER da reagira na button PRIJAVA
	@Override
    public void addNotify() {
        super.addNotify();
        SwingUtilities.getRootPane(btnOk).setDefaultButton(btnOk);
    }
	
	private void loadCustomers() {
		
		try {
			controller.connect();
			
			listCustomer = new LinkedList<String>();
			
			listCustomer = controller.loadSuppliers();
			
			for (int i = 0; i < listCustomer.size(); i++) {
				if (!(listCustomer.get(i).equals(comboBoxCustomers.getItemAt(i))))
					comboBoxCustomers.addItem(listCustomer.get(i));
			}
			comboBoxCustomers.addItem("");
			
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
		gc.insets = new Insets(20, 0, 0, 5);
		add(lblCustomers, gc);

		gc.gridx = 1;
		gc.insets = new Insets(20, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(comboBoxCustomers, gc);

		/////////////////////// 2.RED ///////////////////////

		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 2;

		gc.gridx = 1;
		gc.insets = new Insets(10, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(btnOk, gc);

	}
	
}
