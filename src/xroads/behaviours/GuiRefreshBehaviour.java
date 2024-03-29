package xroads.behaviours;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

import java.util.ArrayList;

import xroads.agents.SpawnerAgent;

@SuppressWarnings("serial")
public class GuiRefreshBehaviour extends TickerBehaviour {
	private SpawnerAgent spawner;

	private ArrayList<String> crossroadsName = new ArrayList<String>();
	
	public GuiRefreshBehaviour(Agent spawnerAgent, long period, int gridWidth, int gridHeight) {
		super(spawnerAgent, period);
		spawner = (SpawnerAgent) spawnerAgent;

		// it generates names for crossroad
		for (int i = 0; i < gridWidth * gridHeight; i++) {
			crossroadsName.add("xroad-" + i);
		}
	}

	@Override
	protected void onTick() {
		// refresh crossroads
		for (String x : crossroadsName) {
			spawner.requestCrossroadStatus(x);
		}
		
		//refresh cars
		for (int i = 0; i < spawner.getCarAgents(); i ++) {
			spawner.requestCarStatus("car-" + i);
		}
		
		spawner.updateSimulationTime();
	}

}
