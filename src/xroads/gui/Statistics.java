package xroads.gui;

import java.util.ArrayList;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.JLabel;

import xroads.CarStatus;
import xroads.World;
import xroads.agents.SpawnerAgent;

public class Statistics {
	
	ArrayList<String> agentsName = new ArrayList<String>();
	private JLabel generatedCars;
	private JLabel finishedCars;
	private JLabel simulationTime;
	private JLabel averageTime;
	private long allTime = 0;
	private double averageTimeCalc = 0;
	
	/**
	 * Assign text field to variables
	 * @param generatedCars
	 * @param finishedCars
	 */
	public Statistics(JLabel generatedCars, JLabel finishedCars, JLabel simulationTime, JLabel averageTime) {
		super();
		this.generatedCars = generatedCars;
		this.finishedCars = finishedCars;
		this.simulationTime = simulationTime;
		this.averageTime = averageTime;
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
				
				long tradeTime = getTimeofCar(s);
				averageTime.setText(getAverageTime(tradeTime));
			}
		}
	}

	private String getAverageTime(long tradeTime) {
		allTime += tradeTime;	
		double temp =  (((double) allTime) / ((double)Integer.parseInt((finishedCars.getText())) / (((double)1000.) / ((double)World.TIMESTEP)))) / 1000.;
		DecimalFormat threeDots = new DecimalFormat("0.000"); // we want dot
		return threeDots.format(temp);
		
	}


	private long getTimeofCar(CarStatus s) {
		return s.endTime - s.startTime;	
	}


	public void updateSimulationTime(SpawnerAgent mainAgent) {
		// TODO Auto-generated method stub
		simulationTime.setText(Integer.toString((int) (System.currentTimeMillis() - mainAgent.getStartTimeOfSimulation()) / 1000));
	}
	
	
}
