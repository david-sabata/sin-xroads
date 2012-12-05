package xroads.agents;

import jade.core.Agent;
import xroads.behaviours.SpawnWorldBehaviour;

@SuppressWarnings("serial")
public class SpawnerAgent extends Agent {

	private int gridWidth = 3;
	private int gridHeight = 3;




	protected void setup() {
		System.out.println("Spawner " + getAID().getName() + " is ready");

		addBehaviour(new SpawnWorldBehaviour(gridWidth, gridHeight));
	}




	/**
	 * Vpousti do systemu auta smerujici z jedne koncovky do jine. Obe koncovky
	 * jsou definovane svym agentskym jmenem (popsano primo v tride EndpointAgent)
	 * 
	 * Tato metoda je volana z GUI
	 * 
	 * @param endpointFromName
	 * @param endpointToName
	 * @param count
	 */
	public void spawnCarsFromTo(String endpointFromName, String endpointToName, int count) {

	}




}
