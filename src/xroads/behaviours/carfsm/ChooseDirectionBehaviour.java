package xroads.behaviours.carfsm;

import jade.core.behaviours.OneShotBehaviour;

import java.util.ArrayList;
import java.util.List;
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



	private static class Target {
		public String crossroad; // cilova kizovatka
		public int dir; // smer ZE KTEREHO ke krizovatce pojede
	}



	@Override
	public void action() {
		System.out.println("Car [" + myAgent.getLocalName() + "] is first in queue");

		CarAgent agent = (CarAgent) myAgent;

		Position myPos = World.getAgentCoords(agent.getCurrentCrossroad());
		Position dstPos = World.getAgentCoords(agent.getDestinationCrossroad());

		int w = World.getGridWidth();
		int h = World.getGridHeight();

		List<Target> availableTargets = new ArrayList<Target>();


		// N
		if (myPos.y > dstPos.y && myPos.x != -1 && myPos.x != w && (myPos.y > 0 || myPos.x == dstPos.x)) {
			Target t = new Target();
			t.crossroad = World.getAgentAt(myPos.x, myPos.y - 1);
			t.dir = Constants.SOUTH;
			availableTargets.add(t);
		}
		// z koncovky muze/musi vzdycky
		else if (myPos.y == h) {
			Target t = new Target();
			t.crossroad = World.getAgentAt(myPos.x, myPos.y - 1);
			t.dir = Constants.SOUTH;
			availableTargets.add(t);
		}

		// S
		if (myPos.y < dstPos.y && myPos.x != -1 && myPos.x != w && (myPos.y < World.getGridHeight() - 1 || myPos.x == dstPos.x)) {
			Target t = new Target();
			t.crossroad = World.getAgentAt(myPos.x, myPos.y + 1);
			t.dir = Constants.NORTH;
			availableTargets.add(t);
		}
		// z koncovky muze/musi vzdycky
		else if (myPos.y == -1) {
			Target t = new Target();
			t.crossroad = World.getAgentAt(myPos.x, myPos.y + 1);
			t.dir = Constants.NORTH;
			availableTargets.add(t);
		}

		// E
		if (myPos.x < dstPos.x && myPos.y != -1 && myPos.y != h && (myPos.x < World.getGridWidth() - 1 || myPos.y == dstPos.y)) {
			Target t = new Target();
			t.crossroad = World.getAgentAt(myPos.x + 1, myPos.y);
			t.dir = Constants.WEST;
			availableTargets.add(t);
		}
		// z koncovky muze/musi vzdycky
		else if (myPos.x == -1) {
			Target t = new Target();
			t.crossroad = World.getAgentAt(myPos.x + 1, myPos.y);
			t.dir = Constants.WEST;
			availableTargets.add(t);
		}

		// W
		if (myPos.x > dstPos.x && myPos.y != -1 && myPos.y != h && (myPos.x > 0 || myPos.y == dstPos.y)) {
			Target t = new Target();
			t.crossroad = World.getAgentAt(myPos.x - 1, myPos.y);
			t.dir = Constants.EAST;
			availableTargets.add(t);
		}
		// z koncovky muze/musi vzdycky
		else if (myPos.x == w) {
			Target t = new Target();
			t.crossroad = World.getAgentAt(myPos.x - 1, myPos.y);
			t.dir = Constants.EAST;
			availableTargets.add(t);
		}

		// kontrola
		if (availableTargets.size() == 0) {
			System.err.println("Car [" + myAgent.getLocalName() + "] is lost at " + myPos.toString());
		}

		// vybrat nahodny z dostupnych smeru
		int index = random.nextInt(availableTargets.size());
		Target t = availableTargets.get(index);

		System.out.println("Car [" + myAgent.getLocalName() + "] decides to go from [" + myPos.x + " | " + myPos.y + "] to [" + t.crossroad + " | " + t.dir
				+ "], " + availableTargets.size() + " choices");

		// zapamatovat si v agentovi (mezi stavy FSM jde predavat pouze int)
		agent.setNextHopCrossroad(t.crossroad, t.dir);


		//		// muzeme jet obema smery
		//		if (myPos.x != dstPos.x && myPos.y != dstPos.y) {
		//
		//			// minimalizace x
		//			if (random.nextBoolean()) {
		//				int diff = (myPos.x < dstPos.x) ? 1 : -1;
		//				targetCrossroad = World.getAgentAt(myPos.x + diff, myPos.y);
		//				targetCrossroadDir = (diff == 1) ? Constants.WEST : Constants.EAST;
		//			}
		//			// minimalizace y
		//			else {
		//				int diff = (myPos.y < dstPos.y) ? 1 : -1;
		//				targetCrossroad = World.getAgentAt(myPos.x, myPos.y + diff);
		//				targetCrossroadDir = (diff == 1) ? Constants.NORTH : Constants.SOUTH;
		//			}
		//		}
		//
		//		// muzeme jet jen po X
		//		else if (myPos.y == dstPos.y) {
		//			int diff = (myPos.x < dstPos.x) ? 1 : -1;
		//			targetCrossroad = World.getAgentAt(myPos.x + diff, myPos.y);
		//			targetCrossroadDir = (diff == 1) ? Constants.WEST : Constants.EAST;
		//		}
		//
		//		// muzeme jet jen po Y
		//		else if (myPos.x == dstPos.x) {
		//			int diff = (myPos.y < dstPos.y) ? 1 : -1;
		//			targetCrossroad = World.getAgentAt(myPos.x, myPos.y + diff);
		//			targetCrossroadDir = (diff == 1) ? Constants.NORTH : Constants.SOUTH;
		//		}
		//
		//		// agent v cili - sem by se to nemelo dostat, toto chceme odchytit jinde
		//		else {
		//			System.out.println("Car [" + myAgent.getLocalName() + "] has reached its destination");
		//			return;
		//		}
		//
		//		// kontrola
		//		if (targetCrossroad == null || targetCrossroadDir == -1) {
		//			System.err.println("MyPos " + myPos.toString());
		//			System.err.println("DstPos" + dstPos.toString());
		//			System.err.println("Target crossroad or direction is null");
		//		}

		// zapamatovat si v agentovi (mezi stavy FSM jde predavat pouze int)
		//agent.setNextHopCrossroad(targetCrossroad, targetCrossroadDir);
	}
}
