package xroads.behaviours;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import xroads.Constants;
import xroads.CrossroadStatus;
import xroads.agents.CrossroadAgent;

/**
 * Prepinani svetel krizovatky
 */
@SuppressWarnings("serial")
public class CrossroadLightsBehaviour extends WakerBehaviour {

	private long switchTimeout = 3 * Constants.TIMESTEP;


	public CrossroadLightsBehaviour(Agent agent, long initialSwitchTimeout) {
		super(agent, initialSwitchTimeout);

		switchTimeout = initialSwitchTimeout;
	}



	@Override
	public void onWake() {
		CrossroadAgent xroad = (CrossroadAgent) myAgent;
		CrossroadStatus status = xroad.getStatus();
		int semaphores[] = status.semaphore;

		if (semaphores[Constants.NORTH] == Constants.GREEN && semaphores[Constants.SOUTH] == Constants.GREEN) {
			semaphores[Constants.NORTH] = Constants.RED;
			semaphores[Constants.SOUTH] = Constants.RED;
			semaphores[Constants.EAST] = Constants.GREEN;
			semaphores[Constants.WEST] = Constants.GREEN;
		} else {
			semaphores[Constants.NORTH] = Constants.GREEN;
			semaphores[Constants.SOUTH] = Constants.GREEN;
			semaphores[Constants.EAST] = Constants.RED;
			semaphores[Constants.WEST] = Constants.RED;
		}

		// znovu spustit za dany cas
		reset(switchTimeout);
	}

}
