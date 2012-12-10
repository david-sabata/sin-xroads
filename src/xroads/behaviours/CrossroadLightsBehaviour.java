package xroads.behaviours;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import xroads.Constants;
import xroads.CrossroadStatus;
import xroads.DirectionStatus;
import xroads.World;
import xroads.agents.CrossroadAgent;

/**
 * Prepinani svetel krizovatky
 */
@SuppressWarnings("serial")
public class CrossroadLightsBehaviour extends WakerBehaviour {

	// private long switchTimeout = 3 * World.TIMESTEP;


	public CrossroadLightsBehaviour(Agent agent, long initialSwitchTimeout) {
		super(agent, initialSwitchTimeout);

	// switchTimeout = initialSwitchTimeout;
	}



	@Override
	public void onWake() {
		CrossroadAgent xroad = (CrossroadAgent) myAgent;
		CrossroadStatus status = xroad.getStatus();

		DirectionStatus north = status.directions[Constants.NORTH];
		DirectionStatus south = status.directions[Constants.SOUTH];
		DirectionStatus east = status.directions[Constants.EAST];
		DirectionStatus west = status.directions[Constants.WEST];

		if (north.semaphore == Constants.GREEN && south.semaphore == Constants.GREEN) {
			north.semaphore = Constants.RED;
			south.semaphore = Constants.RED;
			east.semaphore = Constants.GREEN;
			west.semaphore = Constants.GREEN;
		} else {
			north.semaphore = Constants.GREEN;
			south.semaphore = Constants.GREEN;
			east.semaphore = Constants.RED;
			west.semaphore = Constants.RED;
		}

		// znovu spustit za dany cas
		// reset(switchTimeout);
		reset(3 * World.TIMESTEP);
	}

}
