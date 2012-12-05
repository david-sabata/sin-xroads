package xroads.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import xroads.agents.SpawnerAgent;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class XroadsGui extends JFrame {

	private JPanel contentPane;
	private JTextField widthDimens;
	private JTextField heightDimens;
	private CityGenerator city;

	/**
	 * Create the frame.
	 */
	public XroadsGui(final SpawnerAgent mainAgent) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 298, 93);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setBounds(28, 63, 35, 14);
		panel.add(lblHeight);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// regenerate table city
					city.generateCity(Integer.parseInt(widthDimens.getText()), Integer.parseInt(heightDimens.getText()));
					// Contact gui agent, that city is generated
					mainAgent.spawnCrossroads(Integer.parseInt(widthDimens.getText()), Integer.parseInt(heightDimens.getText()));
				} catch (NumberFormatException error) {
					System.out.println(error.getLocalizedMessage());
				}
			}
		});
		btnGenerate.setBounds(211, 59, 77, 23);
		panel.add(btnGenerate);
		
		JLabel lblWidth = new JLabel("Width:");
		lblWidth.setBounds(28, 34, 32, 14);
		panel.add(lblWidth);
		
		widthDimens = new JTextField();
		widthDimens.setBounds(85, 31, 86, 20);
		panel.add(widthDimens);
		widthDimens.setColumns(10);
		
		heightDimens = new JTextField();
		heightDimens.setBounds(85, 60, 86, 20);
		panel.add(heightDimens);
		heightDimens.setColumns(10);
		
		JLabel lblCityDimensions = new JLabel("Dimensions of city :");
		lblCityDimensions.setBounds(28, 9, 106, 14);
		panel.add(lblCityDimensions);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(211, 30, 77, 23);
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel.add(btnClear);
		
		city = new CityGenerator();
		city.setBorder(new LineBorder(new Color(0, 0, 0)));
		city.setBackground(UIManager.getColor("Panel.background"));
		city.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		city.setRowSelectionAllowed(false);
		city.setColumnSelectionAllowed(true);
		city.setCellSelectionEnabled(true);
		city.setBounds(5, 109, 751, 426);
		contentPane.add(city);
		
		this.setVisible(true);
	}
}
