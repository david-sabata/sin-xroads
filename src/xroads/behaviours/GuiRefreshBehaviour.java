package xroads.behaviours;

import java.util.ArrayList;

import xroads.agents.CrossroadAgent;
import xroads.agents.SpawnerAgent;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class GuiRefreshBehaviour extends TickerBehaviour{
	private SpawnerAgent spawner;
	
	private ArrayList<String> crossroadsName = new ArrayList<String>();
	
	public GuiRefreshBehaviour(Agent spawnerAgent, long period, int gridWidth, int gridHeight) {
		super(spawnerAgent, period);
		spawner = (SpawnerAgent) spawnerAgent;
	
		for (int i = 0; i < gridWidth * gridHeight; i++) {
			crossroadsName.add("xroad-" + i);
		}
	}

	@Override
	protected void onTick() {
		// TODO Auto-generated method stub
		for(String x : crossroadsName) {
			spawner.requestCrossroadQueueLength(x);
		}
	}

}