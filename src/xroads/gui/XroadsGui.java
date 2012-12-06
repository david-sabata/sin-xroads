package xroads.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import xroads.agents.CrossroadAgent;
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
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class XroadsGui extends JFrame {

	private JPanel contentPane;
	private JTextField widthDimens;
	private JTextField heightDimens;
	private CityGenerator city;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private JTable table_3;
	private JTable table_4;
	private JTable table_5;
	private JTable table_6;
	private JTable table_7;

	/**
	 * Create the frame.
	 */
	public XroadsGui(final SpawnerAgent mainAgent) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 886, 655);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 298, 93);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblHeight.setBounds(28, 63, 35, 14);
		panel.add(lblHeight);
		
		JButton btnGenerate = new JButton("Generate");
		btnGenerate.setFont(new Font("SansSerif", Font.PLAIN, 11));
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
		lblWidth.setFont(new Font("SansSerif", Font.PLAIN, 11));
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
		lblCityDimensions.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblCityDimensions.setBounds(28, 9, 106, 14);
		panel.add(lblCityDimensions);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("SansSerif", Font.PLAIN, 11));
		btnClear.setBounds(211, 30, 77, 23);
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		panel.add(btnClear);
		
		city = new CityGenerator();
		city.setBorder(new LineBorder(new Color(0, 0, 0)));
		city.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		city.setBounds(5, 109, 855, 496);
		contentPane.add(city);
		
		table = new JTable();
		table.setBackground(Color.GRAY);
		table.setBounds(712, 35, 22, 22);
		contentPane.add(table);
		
		table_1 = new JTable();
		table_1.setBackground(Color.GRAY);
		table_1.setBounds(734, 35, 22, 22);
		contentPane.add(table_1);
		
		table_2 = new JTable();
		table_2.setBackground(Color.GRAY);
		table_2.setBounds(734, 56, 22, 22);
		contentPane.add(table_2);
		
		table_3 = new JTable();
		table_3.setBackground(Color.GRAY);
		table_3.setBounds(712, 56, 22, 22);
		contentPane.add(table_3);
		
		table_4 = new JTable();
		table_4.setBackground(Color.RED);
		table_4.setBounds(758, 35, 22, 22);
		contentPane.add(table_4);
		
		table_5 = new JTable();
		table_5.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		table_5.setBackground(Color.ORANGE);
		table_5.setBounds(712, 11, 22, 22);
		contentPane.add(table_5);
		
		table_6 = new JTable();
		table_6.setBackground(Color.ORANGE);
		table_6.setBounds(688, 56, 22, 22);
		contentPane.add(table_6);
		
		table_7 = new JTable();
		table_7.setBackground(Color.GREEN);
		table_7.setBounds(734, 80, 22, 22);
		contentPane.add(table_7);
		
		JLabel lblLegend = new JLabel("Legend of crossroad :");
		lblLegend.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblLegend.setBounds(585, 5, 117, 14);
		contentPane.add(lblLegend);
		
		JLabel lblStuck = new JLabel("Stuck road");
		lblStuck.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblStuck.setBounds(790, 35, 70, 14);
		contentPane.add(lblStuck);
		
		JLabel lblFree = new JLabel("Free road");
		lblFree.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblFree.setBounds(766, 80, 70, 14);
		contentPane.add(lblFree);
		
		JButton btnNewCar = new JButton("New Car");
		btnNewCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainAgent.spawnCarsFromTo("endpoint-n-0", "endpoint-w-1", 1);
			}
		});
		btnNewCar.setBounds(353, 7, 89, 23);
		contentPane.add(btnNewCar);
		
		
		this.setVisible(true);
	}
	
	/**
	 * Update crossroad in the city
	 * @param x
	 * @param y
	 * @param value
	 */
	public void updateCrossRoadAt(CrossroadAgent.QueueStatus s) {
		city.updateCrossRoadAt(s);
	}
}
