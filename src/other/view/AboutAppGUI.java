package other.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

public class AboutAppGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtShop;
	private JTextField txtMyName;
	private JTextField txtVerzion;
	private JTextField txtFinalWork;


	public AboutAppGUI() {
		setResizable(false);
		setTitle("O PROGRAMU");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		txtShop = new JTextField();
		txtShop.setBackground(SystemColor.controlShadow);
		txtShop.setHorizontalAlignment(SwingConstants.CENTER);
		txtShop.setActionCommand("");
		txtShop.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtShop.setBorder(new MatteBorder(1, 1, 2, 1, (Color) new Color(0, 0, 0)));
		txtShop.setEditable(false);
		txtShop.setText("TRGOVINA");
		txtShop.setBounds(142, 13, 160, 40);
		
		contentPane.add(txtShop);
		txtShop.setColumns(10);
		
		txtMyName = new JTextField();
		txtMyName.setHorizontalAlignment(SwingConstants.CENTER);
		txtMyName.setBackground(SystemColor.controlShadow);
		txtMyName.setBorder(null);
		txtMyName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtMyName.setEditable(false);
		txtMyName.setText("HRVOJE VUKOVI\u0106");
		txtMyName.setBounds(147, 101, 150, 24);
		contentPane.add(txtMyName);
		txtMyName.setColumns(10);
		
		txtVerzion = new JTextField();
		txtVerzion.setEditable(false);
		txtVerzion.setBorder(null);
		txtVerzion.setBackground(SystemColor.controlShadow);
		txtVerzion.setHorizontalAlignment(SwingConstants.CENTER);
		txtVerzion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtVerzion.setText("VERZIJA: 1.0");
		txtVerzion.setBounds(147, 136, 150, 24);
		contentPane.add(txtVerzion);
		txtVerzion.setColumns(10);
		
		txtFinalWork = new JTextField();
		txtFinalWork.setText("ZAVR\u0160NI RAD 2017.");
		txtFinalWork.setHorizontalAlignment(SwingConstants.CENTER);
		txtFinalWork.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtFinalWork.setEditable(false);
		txtFinalWork.setBorder(null);
		txtFinalWork.setBackground(SystemColor.controlShadow);
		txtFinalWork.setBounds(147, 171, 150, 24);
		contentPane.add(txtFinalWork);
		txtFinalWork.setColumns(10);
	}

}
