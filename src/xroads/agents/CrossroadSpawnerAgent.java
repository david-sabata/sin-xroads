package xroads.agents;

import jade.core.Agent;
import xroads.behaviours.SpawnBehaviour;

@SuppressWarnings("serial")
public class CrossroadSpawnerAgent extends Agent {

	private int gridWidth = 3;
	private int gridHeight = 3;




	protected void setup() {
		System.out.println("CrossroadSpawner " + getAID().getName() + " is ready");

		addBehaviour(new SpawnBehaviour(gridWidth, gridHeight));
	}



}
