package xroads.agents;


import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.regex.Pattern;

import xroads.Constants;
import xroads.CrossroadStatus;
import xroads.World;
import xroads.behaviours.CrossroadStatusListener;
import xroads.behaviours.GuiRefreshBehaviour;
import xroads.behaviours.SpawnWorldBehaviour;
import xroads.gui.XroadsGui;

@SuppressWarnings("serial")
public class SpawnerAgent extends Agent {

	private XroadsGui gui;


	private AgentContainer carAgentContainer;

	/**
	 * Kontejner na agenty nema size()
	 */
	private int carAgents = 0;


	@Override
	protected void setup() {
		System.out.println("Spawner " + getAID().getName() + " is ready");

		gui = new XroadsGui(this);

		// vytvoreni kontejneru na auta, ktery si budeme pamatovat
		Profile p = new ProfileImpl();
		carAgentContainer = Runtime.instance().createAgentContainer(p);
	}


	/**
	 * Nastavuje velikost mesta
	 * 
	 * Tato metoda je volana z GUI 
	 * @param pGridWidth
	 * @param pGridHeight
	 */
	public void spawnCrossroads(int pGridWidth, int pGridHeight) {
		World.setGridSize(pGridWidth, pGridHeight);

		addBehaviour(new SpawnWorldBehaviour(pGridWidth, pGridHeight));

		// Cyklicka kontrola krizovatek
		addBehaviour(new GuiRefreshBehaviour(this, 1000, pGridWidth, pGridHeight));
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
	public void spawnCarsFromTo(final String endpointFromName, final String endpointToName, int count) {
		addBehaviour(new OneShotBehaviour() {

			@Override
			public void action() {
				String args[] = { endpointFromName, endpointToName };

				// zjistit odkud auto do krizovatky (resp. koncovky) prijizdi
				int dir = -1;
				String parts[] = endpointFromName.split(Pattern.quote("-"));
				switch (parts[1]) {
					case "n":
						dir = Constants.NORTH;
						break;
					case "s":
						dir = Constants.SOUTH;
						break;
					case "e":
						dir = Constants.EAST;
						break;
					case "w":
						dir = Constants.WEST;
						break;
				}
				final int incomingDir = dir;

				// spawn
				try {
					AgentController agent = carAgentContainer.createNewAgent("car-" + carAgents, CarAgent.class.getCanonicalName(), args);
					agent.start();
					carAgents++;

					// odeslat vstupni koncovce info o spawnu auta
					addBehaviour(new OneShotBehaviour() {
						@Override
						public void action() {
							ACLMessage request = new ACLMessage(ACLMessage.PROPOSE);
							request.addReceiver(new AID(endpointFromName, AID.ISLOCALNAME));
							request.setContent(String.valueOf(incomingDir));
							myAgent.send(request);
						}
					});

				} catch (StaleProxyException e) {
					System.err.println("Error creating car agents");
					e.printStackTrace();
				}
			}

		});
	}

	/**
	 * Vyzada si od krizovatky informace o jejim stavu
	 * 
	 * @param agentName jmeno krizovatkoveho agenta
	 */
	public void requestCrossroadStatus(final String agentName) {
		addBehaviour(new CrossroadStatusListener(agentName));
	}


	/**
	 * Dostali jsme informaci o stavu krizovatky, predame do GUI
	 */
	public void onCrossroadStatusUpdate(CrossroadStatus s) {
		System.out.println("Got xroad status");
		gui.updateCrossRoadAt(s);
	}
}
