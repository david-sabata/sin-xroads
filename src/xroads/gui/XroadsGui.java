package xroads.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import xroads.CrossroadStatus;
import xroads.agents.SpawnerAgent;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
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
	private JPanel panel_1;
	private JTextField simulationSpeedText;

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
		widthDimens.setText("3");
		widthDimens.setBounds(85, 31, 86, 20);
		panel.add(widthDimens);
		widthDimens.setColumns(10);

		heightDimens = new JTextField();
		heightDimens.setText("3");
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
		city.setBounds(5, 118, 855, 487);
		contentPane.add(city);

		table = new JTable();
		table.setBackground(Color.GRAY);
		table.setBounds(769, 45, 22, 22);
		contentPane.add(table);

		table_1 = new JTable();
		table_1.setBackground(Color.GRAY);
		table_1.setBounds(791, 45, 22, 22);
		contentPane.add(table_1);

		table_2 = new JTable();
		table_2.setBackground(Color.GRAY);
		table_2.setBounds(791, 66, 22, 22);
		contentPane.add(table_2);

		table_3 = new JTable();
		table_3.setBackground(Color.GRAY);
		table_3.setBounds(769, 66, 22, 22);
		contentPane.add(table_3);

		table_4 = new JTable();
		table_4.setBackground(Color.RED);
		table_4.setBounds(815, 45, 22, 22);
		contentPane.add(table_4);

		table_5 = new JTable();
		table_5.setModel(new DefaultTableModel(new Object[][] {}, new String[] {}));
		table_5.setBackground(Color.ORANGE);
		table_5.setBounds(769, 21, 22, 22);
		contentPane.add(table_5);

		table_6 = new JTable();
		table_6.setBackground(Color.ORANGE);
		table_6.setBounds(745, 66, 22, 22);
		contentPane.add(table_6);

		table_7 = new JTable();
		table_7.setBackground(Color.GREEN);
		table_7.setBounds(791, 90, 22, 22);
		contentPane.add(table_7);

		JLabel lblLegend = new JLabel("Legend of crossroad :");
		lblLegend.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblLegend.setBounds(743, 5, 117, 14);
		contentPane.add(lblLegend);

		JLabel lblStuck = new JLabel("Stuck road");
		lblStuck.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblStuck.setBounds(815, 29, 70, 14);
		contentPane.add(lblStuck);

		JLabel lblFree = new JLabel("Free road");
		lblFree.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblFree.setBounds(730, 89, 70, 14);
		contentPane.add(lblFree);
		
		panel_1 = new JPanel();
		panel_1.setBounds(313, 5, 246, 93);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
				JButton btnNewCar = new JButton("New Car");
				btnNewCar.setBounds(137, 7, 86, 23);
				panel_1.add(btnNewCar);
				
				JCheckBox chckbxRandomWay = new JCheckBox("Random way");
				chckbxRandomWay.setSelected(true);
				chckbxRandomWay.setBounds(19, 7, 97, 23);
				panel_1.add(chckbxRandomWay);
				
				simulationSpeedText = new JTextField();
				simulationSpeedText.setText("1000");
				simulationSpeedText.setBounds(19, 62, 86, 20);
				panel_1.add(simulationSpeedText);
				simulationSpeedText.setColumns(10);
				
				JButton btnSetSpeed = new JButton("Set speed");
				btnSetSpeed.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
						mainAgent.setSimulationSpeed(Integer.parseInt(simulationSpeedText.getText()));
						} catch (NumberFormatException error) {
							System.out.println(error.getLocalizedMessage());
						}
					}
				});
				btnSetSpeed.setBounds(137, 61, 86, 23);
				panel_1.add(btnSetSpeed);
				
				JLabel lblRychlostSimulace = new JLabel("Rychlost simulace (ms)");
				lblRychlostSimulace.setBounds(19, 47, 116, 14);
				panel_1.add(lblRychlostSimulace);
				
				JPanel panel_2 = new JPanel();
				panel_2.setBounds(565, 5, 155, 93);
				contentPane.add(panel_2);
				panel_2.setLayout(null);
				
				JLabel lblStatistics = new JLabel("Statistics:");
				lblStatistics.setBounds(10, 11, 80, 14);
				panel_2.add(lblStatistics);
				
				JLabel lblAverageTime = new JLabel("Average time:");
				lblAverageTime.setBounds(10, 31, 80, 14);
				panel_2.add(lblAverageTime);
				
				JLabel label = new JLabel("0000");
				label.setBounds(57, 50, 46, 14);
				panel_2.add(label);
				
				JLabel lblS = new JLabel("s");
				lblS.setBounds(99, 50, 46, 14);
				panel_2.add(lblS);
				btnNewCar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mainAgent.spawnCarsFromTo("endpoint-s-1", "endpoint-n-1", 1);
					}
				});


		this.setVisible(true);
	}

	/**
	 * Update crossroad in the city
	 * @param x
	 * @param y
	 * @param value
	 */
	public void updateCrossRoadAt(CrossroadStatus s) {
		city.updateCrossRoadAt(s);
	}
}
