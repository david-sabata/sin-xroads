package xroads.behaviours.carfsm;

import jade.core.behaviours.OneShotBehaviour;

import java.util.Random;

import xroads.Constants;
import xroads.Position;
import xroads.World;
import xroads.agents.CarAgent;

/**
 * Auto je na prvnim miste a rozhoduje se kam dal pojede. Aktualne se 
 * bude snazit o priblizeni cilovemu bodu minimalizaci rozdilu souradnic 
 * X a Y s tim, ze minimalizovanou souradnici vybira nahodne.
 */
@SuppressWarnings("serial")
public class ChooseDirectionBehaviour extends OneShotBehaviour {

	private Random random = new Random();



	@Override
	public void action() {
		System.out.println("Car is first in queue");

		String targetCrossroad = null;
		int targetCrossroadDir = -1;

		CarAgent agent = (CarAgent) myAgent;

		Position myPos = World.getAgentCoords(agent.getCurrentCrossroad());
		Position dstPos = World.getAgentCoords(agent.getDestinationCrossroad());

		// muzeme jet obema smery
		if (myPos.x != dstPos.x && myPos.y != dstPos.y) {
			// minimalizace x
			if (random.nextBoolean()) {
				int diff = (myPos.x < dstPos.x) ? 1 : -1;
				targetCrossroad = World.getAgentAt(myPos.x + diff, myPos.y);
				targetCrossroadDir = (diff == 1) ? Constants.WEST : Constants.EAST;
			}
			// minimalizace y
			else {
				int diff = (myPos.y < dstPos.y) ? 1 : -1;
				targetCrossroad = World.getAgentAt(myPos.x, myPos.y + diff);
				targetCrossroadDir = (diff == 1) ? Constants.NORTH : Constants.SOUTH;
			}
		}

		// muzeme jet jen po X
		else if (myPos.y == dstPos.y) {
			int diff = (myPos.x < dstPos.x) ? 1 : -1;
			targetCrossroad = World.getAgentAt(myPos.x + diff, myPos.y);
			targetCrossroadDir = (diff == 1) ? Constants.WEST : Constants.EAST;
		}

		// muzeme jet jen po Y
		else if (myPos.x == dstPos.x) {
			int diff = (myPos.y < dstPos.y) ? 1 : -1;
			targetCrossroad = World.getAgentAt(myPos.x, myPos.y + diff);
			targetCrossroadDir = (diff == 1) ? Constants.NORTH : Constants.SOUTH;
		}

		// agent v cili - sem by se to nemelo dostat, toto chceme odchytit jinde
		else {
			System.out.println("Agent " + agent.getLocalName() + " has reached its destination");
			return;
		}

		// zapamatovat si v agentovi (mezi stavy FSM jde predavat pouze int)
		agent.setNextHopCrossroad(targetCrossroad, targetCrossroadDir);
	}

}
