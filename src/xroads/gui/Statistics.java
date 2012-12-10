package xroads.gui;

import java.util.ArrayList;

import javax.swing.JLabel;

import xroads.CarStatus;

public class Statistics {
	
	ArrayList<String> agentsName = new ArrayList<String>();
	private JLabel generatedCars;
	private JLabel finishedCars;
	
	/**
	 * Assign text field to variables
	 * @param generatedCars
	 * @param finishedCars
	 */
	public Statistics(JLabel generatedCars, JLabel finishedCars) {
		super();
		this.generatedCars = generatedCars;
		this.finishedCars = finishedCars;
	}


	/**
	 * Update statistics
	 * @param s
	 * @param carAgents 
	 */
	public void updateCarStatus(CarStatus s, int carAgents) {
		generatedCars.setText(Integer.toString(carAgents));
		if(!agentsName.contains(s.name)) {
			if(s.currentCrossroad.equals(s.destinationCrossroad)) {
				finishedCars.setText(Integer.toString(Integer.parseInt(finishedCars.getText()) + 1));
				agentsName.add(s.name);
			}
		}
	}
}
