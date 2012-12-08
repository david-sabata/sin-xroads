package xroads.behaviours;

import java.util.ArrayList;
import xroads.agents.SpawnerAgent;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

@SuppressWarnings("serial")
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
		for(String x : crossroadsName) {
			spawner.requestCrossroadQueueLength(x);
		}
	}

}
