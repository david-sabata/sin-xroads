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
import xroads.CrossroadStatus;
import xroads.behaviours.GuiRefreshBehaviour;
import xroads.behaviours.CrossroadStatusInformant;
import xroads.behaviours.CrossroadStatusListener;
import xroads.behaviours.SpawnWorldBehaviour;
import xroads.gui.XroadsGui;

@SuppressWarnings("serial")
public class SpawnerAgent extends Agent {

	private int gridWidth = 3;
	private int gridHeight = 3;
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

		// naslouchani na oznameni o delkach front
		addBehaviour(new CrossroadStatusListener());

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

		// Cyklicka kontrola krizovatek
		addBehaviour(new GuiRefreshBehaviour(this, 1000, gridWidth, gridHeight));

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

				try {
					AgentController agent = carAgentContainer.createNewAgent("car-" + carAgents, CarAgent.class.getCanonicalName(), args);
					agent.start();
					carAgents++;
				} catch (StaleProxyException e) {
					System.err.println("Error creating car agents");
					e.printStackTrace();
				}
			}

		});
	}


	/**
	 * Vyzada si od krizovatky informace o stavu jeji fronty
	 * 
	 * @param agentName jmeno krizovatkoveho agenta
	 */
	public void requestCrossroadQueueLength(final String agentName) {
		addBehaviour(new OneShotBehaviour() {
			@Override
			public void action() {
				// poslat dotaz
				ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
				request.addReceiver(new AID(agentName, AID.ISLOCALNAME));
				request.setContent(CrossroadStatusInformant.REQUEST_QUEUE_LENGTH);
				myAgent.send(request);
			}
		});
	}


	/**
	 * Metoda volana z behaviouru v pripade ze nektera z krizovatek
	 * odpovedela na dotaz na delku fronty. Zde je prostor pro GUI reagovat.
	 */
	public void onCrossroadStatusUpdate(CrossroadStatus s) {
		gui.updateCrossRoadAt(s);
	}


}
