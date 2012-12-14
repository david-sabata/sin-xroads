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

import java.util.UUID;

import xroads.CarStatus;
import xroads.CrossroadStatus;
import xroads.World;
import xroads.behaviours.CarStatusListener;
import xroads.behaviours.CrossroadStatusListener;
import xroads.behaviours.GuiRefreshBehaviour;
import xroads.behaviours.SpawnWorldBehaviour;
import xroads.behaviours.UniformAddingCarsBehaviour;
import xroads.gui.XroadsGui;

@SuppressWarnings("serial")
public class SpawnerAgent extends Agent {

	private XroadsGui gui;
	private AgentContainer carAgentContainer;

	/**
	 * ID konverzace ktere pouzijeme pro vsechny zpravy 
	 * tykajici se zjistovani/hlaseni stavu krizovatek.
	 * Je nutne pro jemnejsi filtrovani zprav nez na urovni performative
	 */
	private final String statusInformConvId = UUID.randomUUID().toString();

	private final String carStatusInformConvId = UUID.randomUUID().toString();



	/**
	 * Kontejner na agenty nema size()
	 */
	private int carAgents = 0;

	private long startTimestampOfSimulation;

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
		addBehaviour(new GuiRefreshBehaviour(this, World.TIMESTEP / 3, pGridWidth, pGridHeight));

		// naslouchani na infa o stavu
		addBehaviour(new CrossroadStatusListener(statusInformConvId));

		// naslouchani na infa o autech
		addBehaviour(new CarStatusListener(carStatusInformConvId));

		// testovaci provoz ve meste pro 3x3

		spawnCarsFromTo("xroad-8", "endpoint-e-2", 0);
		spawnCarsFromTo("xroad-3", "endpoint-w-2", 3);
		spawnCarsFromTo("xroad-4", "endpoint-w-0", 2);
		spawnCarsFromTo("xroad-1", "endpoint-n-0", 1);
		spawnCarsFromTo("xroad-3", "endpoint-s-1", 1);
		spawnCarsFromTo("xroad-1", "endpoint-n-1", 2);
		spawnCarsFromTo("xroad-2", "endpoint-w-1", 2);
		spawnCarsFromTo("xroad-8", "endpoint-s-1", 0);
		spawnCarsFromTo("xroad-0", "endpoint-w-2", 3);
		spawnCarsFromTo("xroad-4", "endpoint-e-2", 2);
		spawnCarsFromTo("xroad-2", "endpoint-e-1", 1);
		spawnCarsFromTo("xroad-4", "endpoint-e-1", 3);
		spawnCarsFromTo("xroad-8", "endpoint-s-0", 0);
		spawnCarsFromTo("xroad-5", "endpoint-s-2", 2);
		spawnCarsFromTo("xroad-4", "endpoint-n-2", 2);
		spawnCarsFromTo("xroad-6", "endpoint-s-2", 2);
		spawnCarsFromTo("xroad-8", "endpoint-e-1", 3);
		spawnCarsFromTo("xroad-4", "endpoint-e-0", 3);
		spawnCarsFromTo("xroad-8", "endpoint-e-2", 1);
		spawnCarsFromTo("xroad-6", "endpoint-s-1", 2);
		spawnCarsFromTo("xroad-2", "endpoint-e-2", 1);
		spawnCarsFromTo("xroad-0", "endpoint-n-2", 3);
		spawnCarsFromTo("xroad-6", "endpoint-w-2", 3);
		spawnCarsFromTo("xroad-3", "endpoint-w-2", 2);
		spawnCarsFromTo("xroad-7", "endpoint-e-0", 0);
		spawnCarsFromTo("xroad-3", "endpoint-w-0", 1);
		spawnCarsFromTo("xroad-5", "endpoint-w-2", 3);
		spawnCarsFromTo("xroad-6", "endpoint-e-1", 3);
		spawnCarsFromTo("xroad-5", "endpoint-s-1", 3);
		spawnCarsFromTo("xroad-3", "endpoint-n-2", 2);
		spawnCarsFromTo("xroad-6", "endpoint-w-2", 2);
		spawnCarsFromTo("xroad-7", "endpoint-w-2", 3);
		spawnCarsFromTo("xroad-5", "endpoint-n-2", 3);
		spawnCarsFromTo("xroad-0", "endpoint-e-2", 0);
		spawnCarsFromTo("xroad-0", "endpoint-e-2", 3);
		spawnCarsFromTo("xroad-6", "endpoint-s-1", 1);
		spawnCarsFromTo("xroad-6", "endpoint-e-2", 2);
		spawnCarsFromTo("xroad-4", "endpoint-e-1", 3);
		spawnCarsFromTo("xroad-5", "endpoint-s-2", 2);
		spawnCarsFromTo("xroad-2", "endpoint-n-1", 2);
		spawnCarsFromTo("xroad-1", "endpoint-w-0", 0);
		spawnCarsFromTo("xroad-5", "endpoint-e-2", 2);
		spawnCarsFromTo("xroad-3", "endpoint-w-2", 0);
		spawnCarsFromTo("xroad-4", "endpoint-n-0", 0);
		spawnCarsFromTo("xroad-7", "endpoint-n-0", 0);
		spawnCarsFromTo("xroad-4", "endpoint-s-0", 1);
		spawnCarsFromTo("xroad-3", "endpoint-n-1", 3);
		spawnCarsFromTo("xroad-4", "endpoint-e-0", 2);
		spawnCarsFromTo("xroad-6", "endpoint-n-1", 2);
		spawnCarsFromTo("xroad-1", "endpoint-n-0", 0);
		/*spawnCarsFromTo("xroad-5", "endpoint-s-1", 2);
		spawnCarsFromTo("xroad-1", "endpoint-e-1", 1);
		spawnCarsFromTo("xroad-6", "endpoint-w-1", 2);
		spawnCarsFromTo("xroad-3", "endpoint-s-2", 0);
		spawnCarsFromTo("xroad-8", "endpoint-s-0", 3);
		spawnCarsFromTo("xroad-5", "endpoint-w-1", 0);
		spawnCarsFromTo("xroad-0", "endpoint-e-1", 1);
		spawnCarsFromTo("xroad-4", "endpoint-n-2", 0);
		spawnCarsFromTo("xroad-5", "endpoint-s-0", 2);
		spawnCarsFromTo("xroad-6", "endpoint-s-0", 0);
		spawnCarsFromTo("xroad-4", "endpoint-n-1", 3);
		spawnCarsFromTo("xroad-1", "endpoint-n-0", 1);
		spawnCarsFromTo("xroad-4", "endpoint-n-2", 3);
		spawnCarsFromTo("xroad-5", "endpoint-n-0", 0);
		spawnCarsFromTo("xroad-2", "endpoint-e-1", 2);
		spawnCarsFromTo("xroad-6", "endpoint-s-0", 2);
		spawnCarsFromTo("xroad-5", "endpoint-e-1", 3);
		spawnCarsFromTo("xroad-1", "endpoint-s-1", 0);
		spawnCarsFromTo("xroad-1", "endpoint-w-1", 0);
		spawnCarsFromTo("xroad-7", "endpoint-w-1", 3);
		spawnCarsFromTo("xroad-8", "endpoint-n-0", 1);
		spawnCarsFromTo("xroad-4", "endpoint-w-0", 0);
		spawnCarsFromTo("xroad-3", "endpoint-w-1", 1);
		spawnCarsFromTo("xroad-6", "endpoint-w-1", 1);
		spawnCarsFromTo("xroad-3", "endpoint-s-2", 2);
		spawnCarsFromTo("xroad-4", "endpoint-w-1", 1);
		spawnCarsFromTo("xroad-6", "endpoint-w-0", 0);
		spawnCarsFromTo("xroad-3", "endpoint-s-2", 3);
		spawnCarsFromTo("xroad-1", "endpoint-s-1", 1);
		spawnCarsFromTo("xroad-2", "endpoint-n-1", 3);
		spawnCarsFromTo("xroad-3", "endpoint-e-0", 1);
		spawnCarsFromTo("xroad-6", "endpoint-w-1", 2);
		spawnCarsFromTo("xroad-1", "endpoint-s-0", 2);
		spawnCarsFromTo("xroad-6", "endpoint-e-2", 0);
		spawnCarsFromTo("xroad-1", "endpoint-n-2", 0);
		spawnCarsFromTo("xroad-7", "endpoint-w-2", 1);
		spawnCarsFromTo("xroad-2", "endpoint-n-1", 2);
		spawnCarsFromTo("xroad-7", "endpoint-e-1", 2);
		spawnCarsFromTo("xroad-1", "endpoint-e-2", 2);
		spawnCarsFromTo("xroad-5", "endpoint-s-1", 0);
		spawnCarsFromTo("xroad-6", "endpoint-e-0", 0);
		spawnCarsFromTo("xroad-6", "endpoint-w-2", 1);
		spawnCarsFromTo("xroad-4", "endpoint-w-1", 3);
		spawnCarsFromTo("xroad-0", "endpoint-w-2", 0);
		spawnCarsFromTo("xroad-2", "endpoint-n-2", 1);
		spawnCarsFromTo("xroad-3", "endpoint-s-1", 0);
		spawnCarsFromTo("xroad-8", "endpoint-n-0", 1);
		spawnCarsFromTo("xroad-2", "endpoint-n-1", 0);
		spawnCarsFromTo("xroad-1", "endpoint-e-0", 3);
		spawnCarsFromTo("xroad-6", "endpoint-s-1", 3);
		*/
		/*spawnCarsFromTo("xroad-5", "endpoint-n-2", 2);
		spawnCarsFromTo("xroad-2", "endpoint-s-1", 0);
		spawnCarsFromTo("xroad-1", "endpoint-e-1", 1);
		spawnCarsFromTo("xroad-6", "endpoint-e-0", 2);
		spawnCarsFromTo("xroad-5", "endpoint-w-2", 2);
		spawnCarsFromTo("xroad-8", "endpoint-n-0", 1);
		spawnCarsFromTo("xroad-4", "endpoint-e-2", 0);
		spawnCarsFromTo("xroad-4", "endpoint-s-0", 2);
		spawnCarsFromTo("xroad-6", "endpoint-e-0", 0);
		spawnCarsFromTo("xroad-2", "endpoint-n-0", 2);
		spawnCarsFromTo("xroad-8", "endpoint-e-1", 2);
		spawnCarsFromTo("xroad-8", "endpoint-s-2", 3);
		spawnCarsFromTo("xroad-0", "endpoint-n-2", 0);
		spawnCarsFromTo("xroad-3", "endpoint-e-2", 0);
		spawnCarsFromTo("xroad-6", "endpoint-n-2", 3);
		spawnCarsFromTo("xroad-1", "endpoint-e-2", 3);
		spawnCarsFromTo("xroad-5", "endpoint-s-1", 2);
		spawnCarsFromTo("xroad-1", "endpoint-s-2", 1);
		spawnCarsFromTo("xroad-5", "endpoint-e-0", 1);
		spawnCarsFromTo("xroad-0", "endpoint-s-0", 1);
		spawnCarsFromTo("xroad-3", "endpoint-e-0", 3);
		spawnCarsFromTo("xroad-6", "endpoint-s-0", 0);
		spawnCarsFromTo("xroad-6", "endpoint-n-0", 3);
		spawnCarsFromTo("xroad-6", "endpoint-s-0", 2);
		spawnCarsFromTo("xroad-3", "endpoint-w-1", 3);
		spawnCarsFromTo("xroad-8", "endpoint-w-0", 2);
		spawnCarsFromTo("xroad-1", "endpoint-s-1", 2);
		spawnCarsFromTo("xroad-0", "endpoint-w-2", 0);
		spawnCarsFromTo("xroad-5", "endpoint-e-0", 0);
		spawnCarsFromTo("xroad-8", "endpoint-e-1", 1);
		spawnCarsFromTo("xroad-6", "endpoint-s-2", 0);
		spawnCarsFromTo("xroad-1", "endpoint-e-1", 2);
		spawnCarsFromTo("xroad-0", "endpoint-s-1", 0);
		spawnCarsFromTo("xroad-5", "endpoint-w-1", 1);
		spawnCarsFromTo("xroad-4", "endpoint-s-2", 0);
		spawnCarsFromTo("xroad-8", "endpoint-e-2", 1);
		spawnCarsFromTo("xroad-6", "endpoint-w-2", 3);
		spawnCarsFromTo("xroad-0", "endpoint-s-2", 1);
		spawnCarsFromTo("xroad-4", "endpoint-n-0", 3);
		spawnCarsFromTo("xroad-4", "endpoint-w-2", 0);
		spawnCarsFromTo("xroad-5", "endpoint-s-1", 3);
		spawnCarsFromTo("xroad-1", "endpoint-n-2", 1);
		spawnCarsFromTo("xroad-6", "endpoint-e-0", 0);
		spawnCarsFromTo("xroad-6", "endpoint-e-0", 3);
		spawnCarsFromTo("xroad-6", "endpoint-e-2", 3);
		spawnCarsFromTo("xroad-8", "endpoint-s-2", 1);
		spawnCarsFromTo("xroad-2", "endpoint-n-1", 3);
		spawnCarsFromTo("xroad-6", "endpoint-w-2", 3);
		spawnCarsFromTo("xroad-0", "endpoint-w-0", 0);
		spawnCarsFromTo("xroad-4", "endpoint-w-2", 2);
		spawnCarsFromTo("xroad-8", "endpoint-s-1", 1);
		spawnCarsFromTo("xroad-3", "endpoint-n-0", 3);
		spawnCarsFromTo("xroad-5", "endpoint-e-1", 0);
		spawnCarsFromTo("xroad-7", "endpoint-e-2", 3);
		spawnCarsFromTo("xroad-7", "endpoint-e-1", 1);
		spawnCarsFromTo("xroad-6", "endpoint-w-2", 3);
		spawnCarsFromTo("xroad-3", "endpoint-e-1", 2);
		spawnCarsFromTo("xroad-7", "endpoint-w-1", 1);
		spawnCarsFromTo("xroad-8", "endpoint-s-1", 3);
		spawnCarsFromTo("xroad-7", "endpoint-n-0", 0);
		spawnCarsFromTo("xroad-2", "endpoint-n-2", 3);
		spawnCarsFromTo("xroad-4", "endpoint-s-2", 3);
		spawnCarsFromTo("xroad-2", "endpoint-s-1", 0);
		spawnCarsFromTo("xroad-6", "endpoint-e-2", 1);
		spawnCarsFromTo("xroad-4", "endpoint-e-0", 2);
		spawnCarsFromTo("xroad-8", "endpoint-s-1", 1);
		spawnCarsFromTo("xroad-7", "endpoint-w-1", 0);
		spawnCarsFromTo("xroad-2", "endpoint-s-2", 3);
		spawnCarsFromTo("xroad-8", "endpoint-w-0", 0);
		spawnCarsFromTo("xroad-1", "endpoint-e-1", 0);
		spawnCarsFromTo("xroad-5", "endpoint-s-0", 1);
		spawnCarsFromTo("xroad-4", "endpoint-e-1", 1);
		spawnCarsFromTo("xroad-5", "endpoint-n-1", 3);
		spawnCarsFromTo("xroad-0", "endpoint-n-0", 3);
		spawnCarsFromTo("xroad-5", "endpoint-w-0", 1);
		spawnCarsFromTo("xroad-4", "endpoint-n-1", 3);
		spawnCarsFromTo("xroad-3", "endpoint-n-1", 2);
		spawnCarsFromTo("xroad-5", "endpoint-n-0", 3);
		spawnCarsFromTo("xroad-7", "endpoint-w-0", 0);
		spawnCarsFromTo("xroad-1", "endpoint-w-2", 2);
		spawnCarsFromTo("xroad-4", "endpoint-n-1", 2);
		spawnCarsFromTo("xroad-8", "endpoint-s-2", 3);
		spawnCarsFromTo("xroad-1", "endpoint-n-2", 0);
		spawnCarsFromTo("xroad-5", "endpoint-n-2", 1);
		spawnCarsFromTo("xroad-6", "endpoint-e-1", 1);
		spawnCarsFromTo("xroad-8", "endpoint-w-2", 3);
		spawnCarsFromTo("xroad-7", "endpoint-w-1", 3);
		spawnCarsFromTo("xroad-6", "endpoint-e-1", 0);
		spawnCarsFromTo("xroad-2", "endpoint-s-1", 2);
		spawnCarsFromTo("xroad-6", "endpoint-w-0", 0);
		spawnCarsFromTo("xroad-6", "endpoint-e-1", 1);
		spawnCarsFromTo("xroad-5", "endpoint-s-1", 0);
		spawnCarsFromTo("xroad-0", "endpoint-e-1", 2);
		spawnCarsFromTo("xroad-1", "endpoint-n-0", 1);
		spawnCarsFromTo("xroad-7", "endpoint-n-0", 1);
		spawnCarsFromTo("xroad-1", "endpoint-e-2", 0);
		spawnCarsFromTo("xroad-8", "endpoint-w-1", 0);
		spawnCarsFromTo("xroad-3", "endpoint-w-1", 2);
		spawnCarsFromTo("xroad-1", "endpoint-e-2", 3);
		spawnCarsFromTo("xroad-4", "endpoint-n-1", 1);
		*/
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
	public void spawnCarsFromTo(final String endpointFromName, final String endpointToName) {
		addBehaviour(new OneShotBehaviour() {

			@Override
			public void action() {
				String args[] = { endpointFromName, endpointToName };

				// spawn
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



	public void spawnCarsFromTo(final String endpointFromName, final String endpointToName, final int direction) {
		addBehaviour(new OneShotBehaviour() {

			@Override
			public void action() {
				String args[] = { endpointFromName, endpointToName, String.valueOf(direction) };

				// spawn
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
	 * Vyzada si od krizovatky informace o jejim stavu
	 * 
	 * @param agentName jmeno krizovatkoveho agenta
	 */
	public void requestCrossroadStatus(final String agentName) {
		addBehaviour(new OneShotBehaviour() {
			@Override
			public void action() {
				ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
				request.setConversationId(statusInformConvId);
				request.addReceiver(new AID(agentName, AID.ISLOCALNAME));
				myAgent.send(request);
			}
		});
	}

	/**
	 * Dostali jsme informaci o stavu krizovatky, predame do GUI
	 */
	public void onCrossroadStatusUpdate(CrossroadStatus s) {
		gui.updateCrossRoadAt(s);
	}





	/**
	 * Vyzada si od auta informace o jeho stavu
	 */
	public void requestCarStatus(final String agentName) {
		addBehaviour(new OneShotBehaviour() {
			@Override
			public void action() {
				ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
				request.setConversationId(carStatusInformConvId);
				request.addReceiver(new AID(agentName, AID.ISLOCALNAME));
				myAgent.send(request);
			}
		});
	}

	/**
	 * Dostali jsme informaci o stavu auta, predame do GUI
	 */
	public void onCarStatusUpdate(CarStatus s) {
		gui.updateCarStatus(s, carAgents);
	}





	/**
	 * Tato metoda nastavuje promennou, ktera urcuje jak rychle bude ubihat simulacni cas
	 * @param parseInt
	 */
	public void setSimulationSpeed(int parseInt) {
		World.TIMESTEP = parseInt;
	}


	public void setStartOfSimulation() {
		startTimestampOfSimulation = System.currentTimeMillis();
	}

	/**
	 *  Tato metoda prida hlavnimu spawnerovi takove chovani, ktere definuje kolik aute se v urcitem casovem intervalu vygeneruje 
	 * @param time 
	 * @param startEndPoint
	 */
	public void addNewCarsToCityUniformly(String startPoint, String endPoint, int count, int time) {
		addBehaviour(new UniformAddingCarsBehaviour(this, time / count / (1000 / World.TIMESTEP), count, startPoint, endPoint));
	}

	/**
	 * Vraci pocet agentu
	 * @return
	 */
	public int getCarAgents() {
		return carAgents;
	}


	public long getStartTimeOfSimulation() {
		// TODO Auto-generated method stub
		return startTimestampOfSimulation;
	}


	public void updateSimulationTime() {
		// TODO Auto-generated method stub
		gui.updateSimulationTime();
	}

}
