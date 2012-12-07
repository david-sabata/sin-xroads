package xroads.behaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.util.Random;

import xroads.Constants;
import xroads.CrossroadStatus;
import xroads.Position;
import xroads.World;
import xroads.agents.CarAgent;

@SuppressWarnings("serial")
public class CarOverallBehaviour extends FSMBehaviour {

	private static final String STATE_WAIT_QUEUE = "wait in queue";
	private static final String STATE_CHOOSE_DIR = "choosing direction";
	private static final String STATE_WAIT_GREEN = "wait for green light";
	private static final String STATE_ENQUEUE = "enqueue";
	private static final String STATE_AT_ENDPOINT = "endpoint";

	private static final int ENQUEUE_FAILED = 0;
	private static final int ENQUEUE_OK = 1;
	private static final int ENQUEUE_ENDPOINT = 2;


	private CarAgent agent = (CarAgent) myAgent;
	private Random random = new Random();


	/**
	 * Jmeno first-hop krizovatky nebo koncovky
	 */
	private String targetCrossroad = null;

	/**
	 * Smer kterym bude auto do nasledujici krizovatky prijizdet
	 */
	private int targetCrossroadDir = -1;



	public CarOverallBehaviour() {
		super();

		registerFirstState(waitInQueue, STATE_WAIT_QUEUE);
		registerState(chooseDirection, STATE_CHOOSE_DIR);
		registerState(waitForGreen, STATE_WAIT_GREEN);
		registerState(enqueue, STATE_ENQUEUE);
		registerLastState(leaveToEndpoint, STATE_AT_ENDPOINT);

		registerDefaultTransition(STATE_WAIT_QUEUE, STATE_CHOOSE_DIR);
		registerDefaultTransition(STATE_CHOOSE_DIR, STATE_WAIT_GREEN);
		registerDefaultTransition(STATE_WAIT_GREEN, STATE_ENQUEUE);

		// zarazeni do fronty se bude opakovat dokud se nepovede
		// FIXME: pozor, auto v tomto stavu jiz nekontroluje zelenou na semaforu!
		registerTransition(STATE_ENQUEUE, STATE_ENQUEUE, ENQUEUE_FAILED);

		// presun na uvodni cekaci stav na dalsi krizovatku
		registerTransition(STATE_ENQUEUE, STATE_WAIT_QUEUE, ENQUEUE_OK);

		// presun do ciloveho stavu v pripade ze se auto zaradi do fronty koncovky
		registerTransition(STATE_ENQUEUE, STATE_AT_ENDPOINT, ENQUEUE_ENDPOINT);
	}


	/**
	 * Auto je umistene ve fronte a ceka na informaci o tom, ze 
	 * se dostalo na prvni misto. Prichozi zprava musi byt typu INFORM
	 */
	private Behaviour waitInQueue = new Behaviour() {

		private boolean isFirstInQueue = false;

		@Override
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				isFirstInQueue = true;
			} else {
				block();
			}
		}

		@Override
		public boolean done() {
			return isFirstInQueue;
		}
	};


	/**
	 * Auto je na prvnim miste a rozhoduje se kam dal pojede. Aktualne se 
	 * bude snazit o priblizeni cilovemu bodu minimalizaci rozdilu souradnic 
	 * X a Y s tim, ze minimalizovanou souradnici vybira nahodne.
	 */
	private OneShotBehaviour chooseDirection = new OneShotBehaviour() {
		@Override
		public void action() {
			Position myPos = World.getAgentCoords(agent.getCurrentCrossroad());
			Position dstPos = World.getAgentCoords(agent.getDestinationCrossroad());

			// muzeme jet obema smery
			if (myPos.x != dstPos.x && myPos.y != dstPos.y) {
				// minimalizace x
				if (random.nextBoolean()) {
					int diff = (myPos.x < dstPos.x) ? 1 : -1;
					targetCrossroad = World.getAgentAt(myPos.x + diff, myPos.y);
					targetCrossroadDir = (diff == 1) ? Constants.WEST : Constants.EAST;
				}
				// minimalizace y
				else {
					int diff = (myPos.y < dstPos.y) ? 1 : -1;
					targetCrossroad = World.getAgentAt(myPos.x, myPos.y + diff);
					targetCrossroadDir = (diff == 1) ? Constants.NORTH : Constants.SOUTH;
				}
			}

			// muzeme jet jen po X
			else if (myPos.y == dstPos.y) {
				int diff = (myPos.x < dstPos.x) ? 1 : -1;
				targetCrossroad = World.getAgentAt(myPos.x + diff, myPos.y);
				targetCrossroadDir = (diff == 1) ? Constants.WEST : Constants.EAST;
			}

			// muzeme jet jen po Y
			else if (myPos.x == dstPos.x) {
				int diff = (myPos.y < dstPos.y) ? 1 : -1;
				targetCrossroad = World.getAgentAt(myPos.x, myPos.y + diff);
				targetCrossroadDir = (diff == 1) ? Constants.NORTH : Constants.SOUTH;
			}

			// agent v cili - sem by se to nemelo dostat, toto chceme odchytit jinde
			else {
				System.out.println("Agent " + agent.getLocalName() + " has reached its destination");
			}

		}
	};




	/**
	 * Ceka na zelenou na aktualni krizovatce
	 */
	private Behaviour waitForGreen = new Behaviour() {

		private String conversationId = null;

		private boolean isGreenLight = false;

		@Override
		public void action() {
			// poslat dotaz na stav krizovatky na ktere auto stoji
			if (conversationId == null) {
				ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
				request.addReceiver(new AID(agent.getCurrentCrossroad(), AID.ISLOCALNAME));
				myAgent.send(request);

				conversationId = request.getConversationId();
			}

			// cekat na odpoved
			MessageTemplate mt = MessageTemplate.MatchConversationId(conversationId);
			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				String serialized = msg.getContent();
				CrossroadStatus s = null;

				try {
					s = CrossroadStatus.deserialize(serialized);
				} catch (ClassNotFoundException | IOException e) {
					System.err.println("Error: CarOverallBehaviour.waitForGreen recieved malformed message");
					e.printStackTrace();

					myAgent.doDelete();
					return;
				}

				isGreenLight = (s.directions[agent.getCurrentDirection()].semaphore == Constants.GREEN);
			} else {
				block();
			}
		}

		@Override
		public boolean done() {
			return isGreenLight;
		}
	};





	/**
	 * Ceka az bude ve fronte na cilovou krizovatku misto a zaradi se do ni
	 * (vse probiha v ramci jedne konverzace pro zajisteni pseudoatomicity 
	 * a taky proto, ze se mezi FSM stavy nedaji snadno predavat hodnoty).
	 * 
	 * Implementovano jako OneShot, kde jedno spusteni odpovida jednomu pozadavku 
	 * o zarazeni. 
	 * V pripade uspechu vraci 1 a auto je v tuto chvili jiz v cilove fronte, 
	 * jinak vraci 0.
	 */
	private OneShotBehaviour enqueue = new OneShotBehaviour() {

		private String conversationId = null;

		private boolean isEnqueued = false;

		@Override
		public void action() {
			// poslat pozadavek na zarazeni do fronty;
			// obsahem zpravy je oznaceni smeru ze ktereho chce auto prijet
			if (conversationId == null) {
				ACLMessage request = new ACLMessage(ACLMessage.PROPOSE);
				request.addReceiver(new AID(targetCrossroad, AID.ISLOCALNAME));
				request.setContent(String.valueOf(targetCrossroadDir));
				myAgent.send(request);

				conversationId = request.getConversationId();
			}

			// cekat na odpoved - prijde bud PROPOSE_ACCEPT nebo PROPOSE_REJECT
			MessageTemplate mt = MessageTemplate.MatchConversationId(conversationId);
			ACLMessage msg = myAgent.receive(mt);

			if (msg != null) {
				isEnqueued = (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL);

				// pokud se povedlo zaradit, oznamime minule krizovatce svuj odjezd
				if (isEnqueued) {
					ACLMessage request = new ACLMessage(ACLMessage.INFORM);
					request.addReceiver(new AID(agent.getCurrentCrossroad(), AID.ISLOCALNAME));
					request.setContent(String.valueOf(agent.getCurrentDirection()));
					myAgent.send(request);
				}
			} else {
				block();
			}
		}


		@Override
		public int onEnd() {
			if (isEnqueued) {
				agent.setCurrentCrossroad(targetCrossroad, targetCrossroadDir);

				targetCrossroad = null;
				targetCrossroadDir = -1;
			}

			return isEnqueued ? 1 : 0;
		}
	};



	/**
	 * Tento stav nic nedela, jen symbolizuje ze fronta do ktere se zaradilo 
	 * patri koncovce a tudiz je FSM v koncovem stavu
	 */
	private OneShotBehaviour leaveToEndpoint = new OneShotBehaviour() {
		@Override
		public void action() {
			// do nothing
		}
	};



}
