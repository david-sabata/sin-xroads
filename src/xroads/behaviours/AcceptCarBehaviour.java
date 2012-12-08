package xroads.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import xroads.agents.CrossroadAgent;

@SuppressWarnings("serial")
public class AcceptCarBehaviour extends CyclicBehaviour {

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
		ACLMessage msg = myAgent.receive(mt);

		if (msg != null) {
			CrossroadAgent xroad = (CrossroadAgent) myAgent;

			// process message 
			int direction = Integer.parseInt(msg.getContent());
			if (direction < 0 || direction >= 4) {
				System.err.println("Error: Unknown incoming direction [" + direction + "]");
				return;
			}

			ACLMessage reply = msg.createReply();

			// enqueue if possible
			if (xroad.enqueueCar(direction, msg.getSender().getLocalName())) {
				reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
			} else {
				reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
			}

			myAgent.send(reply);
		}

		else {
			block();
		}
	}

}
