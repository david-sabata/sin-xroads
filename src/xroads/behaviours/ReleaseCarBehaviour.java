package xroads.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import xroads.agents.CrossroadAgent;

/**
 * Chovani pro sledovani odjizdejicich aut z teto krizovatky.
 * Pri prichodu zpravy o odjezdu (INFORM se smerem v content) 
 * se zavola na agentovi krizovatky metoda pro odecteni 
 * jednoho auta z dane fronty.
 */
@SuppressWarnings("serial")
public class ReleaseCarBehaviour extends CyclicBehaviour {

	private final CrossroadAgent xroad = (CrossroadAgent) myAgent;


	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(mt);

		if (msg != null) {
			int direction = Integer.parseInt(msg.getContent());
			if (direction < 0 || direction >= 4) {
				System.err.println("Error: Car is leaving crossroad in unknown direction");
				return;
			}

			// predat info zpet agentovi
			xroad.unqueueCar(direction, msg.getSender().getLocalName());
		}

		else {
			block();
		}
	}

}
