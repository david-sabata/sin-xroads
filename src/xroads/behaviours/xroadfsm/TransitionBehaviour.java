package xroads.behaviours.xroadfsm;

import jade.core.behaviours.OneShotBehaviour;

import java.util.Random;

import xroads.Constants;
import xroads.CrossroadStatus;
import xroads.DirectionStatus;
import xroads.World;
import xroads.agents.CrossroadAgent;

/**
 * Prepina svetla na krizovatce a svoji navratovou hodnotou
 * udava za jak dlouho se budou svetla prepinat priste
 */
@SuppressWarnings("serial")
public class TransitionBehaviour extends OneShotBehaviour {

	@Override
	public void action() {
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

	}

	@Override
	public int onEnd() {
		Random r = new Random();

		return World.TIMESTEP * (1 + r.nextInt(5));
	}




}
