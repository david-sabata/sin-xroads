package xroads.agents;

import jade.core.Agent;
import xroads.behaviours.CarOverallBehaviour;

@SuppressWarnings("serial")
public class CarAgent extends Agent {

	private String sourceCrossroad;
	private String currentCrossroad;
	private String destinationCrossroad;

	/**
	 * Smer ze ktere auto stoji na aktualni krizovatce
	 */
	private int currentDirection;


	@Override
	protected void setup() {
		Object args[] = getArguments();
		if (args.length != 2) {
			System.err.println("Unexpected arguments for CarAgent. Call with <src> <dst>");
			doDelete();
		}

		sourceCrossroad = args[0].toString();
		currentCrossroad = sourceCrossroad;
		destinationCrossroad = args[1].toString();

		// komplexni chovani auta definovane pomoci FSM
		addBehaviour(new CarOverallBehaviour());
	}


	/**
	 * Oznamuje autu, ze se presunulo do nove krizovatky
	 * 
	 * Volano pouze z CarOVerallBehaviour !!!
	 */
	public void setCurrentCrossroad(String newCurrent, int newCurrentDir) {
		currentCrossroad = newCurrent;
		currentDirection = newCurrentDir;

		System.out.println("Car is at [" + newCurrent + "] in direction [" + newCurrentDir + "]");
	}




	public String getDestinationCrossroad() {
		return destinationCrossroad;
	}

	public String getCurrentCrossroad() {
		return currentCrossroad;
	}

	public int getCurrentDirection() {
		return currentDirection;
	}


}
