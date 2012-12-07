package xroads.agents;

import jade.core.Agent;
import xroads.Constants;
import xroads.CrossroadStatus;
import xroads.behaviours.CrossroadLightsBehaviour;
import xroads.behaviours.CrossroadStatusInformant;

@SuppressWarnings("serial")
public class CrossroadAgent extends Agent {


	private int gridPosition = -1;
	private int gridWidth = -1;
	private int gridHeight = -1;


	/**
	 * Pamatujeme si radeji jmena agentu nez jejich objekty
	 */
	private CrossroadStatus crossroadStatus = new CrossroadStatus();



	@Override
	protected void setup() {
		Object args[] = getArguments();
		if (args.length != 3) {
			System.err.println("Unexpected arguments for CrossroadAgent. Call with <gridWidth> <gridHeight> <position>");
			doDelete();
		}

		// inicializace krizovatky
		for (int dir : Constants.DIRECTIONS) {
			crossroadStatus.actualLength[dir] = 0;
			crossroadStatus.maximumLength[dir] = 10;
			crossroadStatus.semaphore[dir] = Constants.ORANGE;
		}

		crossroadStatus.name = getAID().getLocalName();

		gridWidth = Integer.parseInt(args[0].toString());
		gridHeight = Integer.parseInt(args[1].toString());
		gridPosition = Integer.parseInt(args[2].toString());


		// informuje o delce fronty
		addBehaviour(new CrossroadStatusInformant());

		// prepina semafory
		long initialSwitchTimeout = 3 * Constants.TIMESTEP;
		addBehaviour(new CrossroadLightsBehaviour(this, initialSwitchTimeout));
	}


	/**
	 * Vraci objekt reprezentujici stav krizovatky
	 */
	public CrossroadStatus getStatus() {
		return crossroadStatus;
	}





}