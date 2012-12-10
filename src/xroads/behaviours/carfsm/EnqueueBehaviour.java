package xroads.behaviours.carfsm;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.UUID;

import xroads.agents.CarAgent;

/**
 * Ceka az bude ve fronte na cilovou krizovatku misto a zaradi se do ni
 * (vse probiha v ramci jedne konverzace pro zajisteni pseudoatomicity 
 * a taky proto, ze se mezi FSM stavy nedaji snadno predavat hodnoty).
 * 
 * Implementovano jako OneShot, kde jedno spusteni odpovida jednomu pozadavku 
 * o zarazeni. Cykleni zajistuje FSM.
 * V pripade uspechu vraci 1 a auto je v tuto chvili jiz v cilove fronte, 
 * jinak vraci 0.
 */
@SuppressWarnings("serial")
public class EnqueueBehaviour extends OneShotBehaviour {

	public static final int ENQUEUE_FAILED = 0;
	public static final int ENQUEUE_OK = 1;
	public static final int ENQUEUE_ENDPOINT = 2;


	private String conversationId = null;
	private boolean isEnqueued = false;

	/**
	 * Pri kazdem prechodu do tohoto stavu je poterba resetovat vnitrni promenne
	 */
	@Override
	public void onStart() {
		isEnqueued = false;
		conversationId = null;
	}




	@Override
	public void action() {
		CarAgent agent = (CarAgent) myAgent;

		// poslat pozadavek na zarazeni do fronty;
		// obsahem zpravy je oznaceni smeru ze ktereho chce auto prijet
		if (conversationId == null) {
			ACLMessage request = new ACLMessage(ACLMessage.PROPOSE);
			request.addReceiver(new AID(agent.getNextCrossroad(), AID.ISLOCALNAME));
			request.setContent(String.valueOf(agent.getNextCrossroadDir()));
			request.setConversationId(UUID.randomUUID().toString());
			myAgent.send(request);

			conversationId = request.getConversationId();
			System.out.println("Car [" + myAgent.getLocalName() + "] requests enqueuing");
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
			CarAgent agent = (CarAgent) myAgent;

			agent.setCurrentCrossroad(agent.getNextCrossroad(), agent.getNextCrossroadDir());
			agent.setNextHopCrossroad(null, -1);

			System.out.println("Car [" + myAgent.getLocalName() + "] is now enqueued at [" + agent.getCurrentCrossroad() + "] [dir:"
					+ agent.getCurrentDirection() + "]");

			if (agent.getCurrentCrossroad().equals(agent.getDestinationCrossroad())) {
				return ENQUEUE_ENDPOINT;
			} else {
				return ENQUEUE_OK;
			}
		}

		return ENQUEUE_FAILED;
	}

}
