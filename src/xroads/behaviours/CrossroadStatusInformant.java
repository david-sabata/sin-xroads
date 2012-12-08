package xroads.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

import xroads.CrossroadStatus;
import xroads.agents.CrossroadAgent;

/**
 * Chovani krizovatky, ktere informuje tazatele o svem stavu.
 * Odpoved je typu serializovany objekt se stavem krizovatky.
 */
@SuppressWarnings("serial")
public class CrossroadStatusInformant extends CyclicBehaviour {

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		ACLMessage msg = myAgent.receive(mt);

		if (msg != null) {
			CrossroadAgent xroad = (CrossroadAgent) myAgent;

			// process message 
			ACLMessage reply = msg.createReply();

			// respond with queues length
			reply.setPerformative(ACLMessage.INFORM);

			CrossroadStatus status = xroad.getStatus();
			String serialized = null;

			try {
				serialized = status.serialize();
			} catch (IOException e) {
				System.err.println("Error: Unable to compose message with crossroad status");
				e.printStackTrace();

				myAgent.doDelete();
				return;
			}

			reply.setContent(serialized);

			myAgent.send(reply);
		}

		else {
			block();
		}

	}
}
