package xroads.agents;

import jade.core.Agent;
import xroads.behaviours.SpawnWorldBehaviour;
import xroads.gui.XroadsGui;

@SuppressWarnings("serial")
public class SpawnerAgent extends Agent {

	private int gridWidth = 3;
	private int gridHeight = 3;
	private XroadsGui gui;



	@Override
	protected void setup() {
		System.out.println("Spawner " + getAID().getName() + " is ready");
		
		gui = new XroadsGui(this);
		
	}

	/**
	 * Nastavuje velikost mesta
	 * 
	 * Tato metoda je volana z GUI 
	 * @param pGridWidth
	 * @param pGridHeight
	 */
	public void spawnCrossroads(int pGridWidth, int pGridHeight) {
		gridWidth = pGridWidth;
		gridHeight = pGridHeight;
		
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
