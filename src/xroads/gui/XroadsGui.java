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

import xroads.CarStatus;
import xroads.CrossroadStatus;
import xroads.World;
import xroads.agents.SpawnerAgent;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

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
	private JLabel lblNewLabel;

	private EndpointsGenerator endpointGen;
	private JTextField numOfcars;
	private XroadsGui gui;
	private Statistics statistics;
	private JTextField sendTime;
	private JLabel generatedCars;
	private JLabel finishedCars;
	private SpawnerAgent mainAgent;
	/**
	 * Create the frame.
	 */
	public XroadsGui(final SpawnerAgent mainAgent) {
		gui = this;
		this.mainAgent = mainAgent;
		// initialize endpoint generator
		endpointGen = new EndpointsGenerator(System.currentTimeMillis());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 886, 655);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 298, 102);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblHeight = new JLabel("Width:");
		lblHeight.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblHeight.setBounds(28, 33, 35, 14);
		panel.add(lblHeight);

		final JButton btnGenerate = new JButton("Start");
		btnGenerate.setFont(new Font("SansSerif", Font.PLAIN, 11));
		btnGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// regenerate table city
					city.generateCity(Integer.parseInt(widthDimens.getText()), Integer.parseInt(heightDimens.getText()));
					
					// regenerate endpoint names
					endpointGen.regenerateEndpoints(Integer.parseInt(widthDimens.getText()), Integer.parseInt(heightDimens.getText()));
					
					// Contact gui agent, that city is generated
					mainAgent.spawnCrossroads(Integer.parseInt(widthDimens.getText()), Integer.parseInt(heightDimens.getText()));
					if(Integer.parseInt(simulationSpeedText.getText()) > 0) {
						World.TIMESTEP = Integer.parseInt(simulationSpeedText.getText());
					}
					
					mainAgent.setStartOfSimulation();
					
					btnGenerate.setEnabled(false);
				} catch (NumberFormatException error) {
					System.out.println(error.getLocalizedMessage());
				}
			}
		});
		btnGenerate.setBounds(211, 59, 77, 23);
		panel.add(btnGenerate);

		JLabel lblWidth = new JLabel("Height");
		lblWidth.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblWidth.setBounds(28, 63, 32, 14);
		panel.add(lblWidth);

		widthDimens = new JTextField();
		widthDimens.setText("3");
		widthDimens.setBounds(60, 31, 86, 20);
		panel.add(widthDimens);
		widthDimens.setColumns(10);

		heightDimens = new JTextField();
		heightDimens.setText("3");
		heightDimens.setBounds(60, 61, 86, 20);
		panel.add(heightDimens);
		heightDimens.setColumns(10);

		JLabel lblCityDimensions = new JLabel("Dimensions of city :");
		lblCityDimensions.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblCityDimensions.setBounds(28, 9, 106, 14);
		panel.add(lblCityDimensions);
		
		JLabel lblRychlostSimulace = new JLabel("Rychlost simulace");
		lblRychlostSimulace.setBounds(153, 10, 135, 14);
		panel.add(lblRychlostSimulace);
		
		simulationSpeedText = new JTextField();
		simulationSpeedText.setBounds(185, 31, 86, 20);
		panel.add(simulationSpeedText);
		simulationSpeedText.setHorizontalAlignment(SwingConstants.RIGHT);
		simulationSpeedText.setText("1000");
		simulationSpeedText.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("1s = ");
		lblNewLabel_1.setBounds(156, 34, 25, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblMs = new JLabel("ms");
		lblMs.setBounds(274, 34, 24, 14);
		panel.add(lblMs);

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
		lblFree.setBounds(730, 93, 70, 14);
		contentPane.add(lblFree);
		
		panel_1 = new JPanel();
		panel_1.setBounds(313, 5, 246, 102);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
				JButton btnNewCar = new JButton("New Cars");
				btnNewCar.setBounds(137, 56, 86, 23);
				panel_1.add(btnNewCar);
				
				final JCheckBox randomWayCheckbox = new JCheckBox("Random way");
				randomWayCheckbox.setSelected(true);
				randomWayCheckbox.setBounds(19, 56, 97, 23);
				panel_1.add(randomWayCheckbox);
				
				numOfcars = new JTextField();
				numOfcars.setHorizontalAlignment(SwingConstants.RIGHT);
				numOfcars.setText("1");
				numOfcars.setBounds(46, 32, 59, 20);
				panel_1.add(numOfcars);
				numOfcars.setColumns(10);
				
				JLabel lblNumboerOfCars = new JLabel("Send");
				lblNumboerOfCars.setBounds(10, 35, 29, 14);
				panel_1.add(lblNumboerOfCars);
				
				JLabel lblIn = new JLabel("cars in");
				lblIn.setBounds(111, 35, 39, 14);
				panel_1.add(lblIn);
				
				sendTime = new JTextField();
				sendTime.setHorizontalAlignment(SwingConstants.RIGHT);
				sendTime.setText("30");
				sendTime.setBounds(151, 32, 72, 20);
				panel_1.add(sendTime);
				sendTime.setColumns(10);
				
				JLabel lblS_1 = new JLabel("s");
				lblS_1.setBounds(226, 35, 20, 14);
				panel_1.add(lblS_1);
				
				JPanel panel_2 = new JPanel();
				panel_2.setBounds(565, 5, 155, 102);
				contentPane.add(panel_2);
				panel_2.setLayout(null);
				
				JLabel lblStatistics = new JLabel("Statistics:");
				lblStatistics.setBounds(10, 11, 80, 14);
				panel_2.add(lblStatistics);
				
				JLabel lblAverageTime = new JLabel("Average time:");
				lblAverageTime.setBounds(10, 25, 80, 14);
				panel_2.add(lblAverageTime);
				
				JLabel simulationAverageTime = new JLabel("0");
				simulationAverageTime.setHorizontalAlignment(SwingConstants.RIGHT);
				simulationAverageTime.setBounds(92, 25, 46, 14);
				panel_2.add(simulationAverageTime);
				
				JLabel lblS = new JLabel("s");
				lblS.setBounds(138, 25, 17, 14);
				panel_2.add(lblS);
				
				lblNewLabel = new JLabel("Simulation time:");
				lblNewLabel.setBounds(10, 39, 80, 14);
				panel_2.add(lblNewLabel);
				
				JLabel simulationTime = new JLabel("0");
				simulationTime.setHorizontalAlignment(SwingConstants.RIGHT);
				simulationTime.setBounds(92, 39, 46, 14);
				panel_2.add(simulationTime);
				
				JLabel label = new JLabel("s");
				label.setBounds(138, 39, 17, 14);
				panel_2.add(label);
				
				JLabel lblGeneratedCars = new JLabel("Generated cars:");
				lblGeneratedCars.setBounds(10, 52, 80, 14);
				panel_2.add(lblGeneratedCars);
				
				generatedCars = new JLabel("0");
				generatedCars.setHorizontalAlignment(SwingConstants.RIGHT);
				generatedCars.setBounds(102, 52, 36, 14);
				panel_2.add(generatedCars);
				
				JLabel lblCarsInEndpoint = new JLabel("Cars in endpoint:");
				lblCarsInEndpoint.setBounds(10, 64, 90, 14);
				panel_2.add(lblCarsInEndpoint);
				
				finishedCars = new JLabel("0");
				finishedCars.setHorizontalAlignment(SwingConstants.RIGHT);
				finishedCars.setBounds(92, 64, 46, 14);
				panel_2.add(finishedCars);
				btnNewCar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String startPoint = null;
						String endPoint = null;
						
						// if random way is selected we will choose random endpoint
						if(randomWayCheckbox.isSelected()) {
							startPoint = endpointGen.getRandomEndPoint();
							endPoint = endpointGen.getRandomEndPoint();
							
							if(startPoint != null && endPoint != null) {
								// endPoint and startPoint cant be the same
								while(startPoint.equals(endPoint)) {
									endPoint = endpointGen.getRandomEndPoint();
								}
							}
						} 
						// set by user
						else {
							
						}
						
						int cars = Integer.parseInt(numOfcars.getText());
						int time = Integer.parseInt(sendTime.getText()) * 1000;
						if(startPoint != null && endPoint != null && cars > 0 && time > 0) {
							mainAgent.addNewCarsToCityUniformly(startPoint, endPoint, cars, time);			
						} else {
							// TODO zobrazit dialog, ze pocatecni a koncovy bod, popripade pocet aut jsou spatne definovany
							// JOptionPane.showMessageDialog(null, gui, "Eggs are not supposed to be green.", JOptionPane.WARNING_MESSAGE);
						}
						
						
					}
				});

		statistics = new Statistics(generatedCars, finishedCars, simulationTime, simulationAverageTime );
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

	/**
	 * Update statistics
	 * @param s
	 * @param carAgents 
	 */
	public void updateCarStatus(CarStatus s, int carAgents) {
		statistics.updateCarStatus(s, carAgents);
	}
	
	public void updateSimulationTime() {
		statistics.updateSimulationTime(mainAgent);
	}
}
