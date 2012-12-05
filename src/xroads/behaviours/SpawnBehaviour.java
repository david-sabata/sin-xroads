package xroads.behaviours;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import xroads.agents.CrossroadAgent;
import xroads.agents.EndpointAgent;

@SuppressWarnings("serial")
public class SpawnBehaviour extends OneShotBehaviour {

	private int gridWidth, gridHeight;



	public SpawnBehaviour(int gridWidth, int gridHeight) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
	}




	@Override
	public void action() {
		try {
			spawnCrossroads();
		} catch (StaleProxyException e) {
			System.err.println("Error spawning crossroads agents");
			e.printStackTrace();
		}

		try {
			spawnEndpoints();
		} catch (StaleProxyException e) {
			System.err.println("Error spawning endpoints agents");
			e.printStackTrace();
		}
	}





	private void spawnCrossroads() throws StaleProxyException {
		Profile p = new ProfileImpl();
		AgentContainer container = Runtime.instance().createAgentContainer(p);

		for (int i = 0; i < gridWidth * gridHeight; i++) {
			Object args[] = { gridWidth, gridHeight, i };
			AgentController agent = container.createNewAgent("xroad-" + i, CrossroadAgent.class.getCanonicalName(), args);
			agent.start();
		}
	}




	private void spawnEndpoints() throws StaleProxyException {
		Profile p = new ProfileImpl();
		AgentContainer container = Runtime.instance().createAgentContainer(p);

		// north, south
		for (int i = 0; i < gridWidth; i++) {
			AgentController agent0 = container.createNewAgent("endpoint-n-" + i, EndpointAgent.class.getCanonicalName(), null);
			agent0.start();

			AgentController agent1 = container.createNewAgent("endpoint-s-" + i, EndpointAgent.class.getCanonicalName(), null);
			agent1.start();
		}

		// east, west
		for (int i = 0; i < gridHeight; i++) {
			AgentController agent0 = container.createNewAgent("endpoint-e-" + i, EndpointAgent.class.getCanonicalName(), null);
			agent0.start();

			AgentController agent1 = container.createNewAgent("endpoint-w-" + i, EndpointAgent.class.getCanonicalName(), null);
			agent1.start();
		}
	}





}
