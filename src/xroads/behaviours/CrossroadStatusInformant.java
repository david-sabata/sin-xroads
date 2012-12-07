package xroads.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

import xroads.CrossroadStatus;
import xroads.agents.CrossroadAgent;

/**
 * Chovani krizovatky, ktere informuje tazatele o poctu aut ve 
 * sve fronte a o jeji maximalni delce.
 * Dotaz je typu AclMessage.REQUEST s obsahem QueueLengthInformBehaviour.REQUEST_QUEUE_LENGTH
 * Odpoved je typu serializovany objekt se stavem krizovatky.
 */
@SuppressWarnings("serial")
public class CrossroadStatusInformant extends CyclicBehaviour {

	public static final String REQUEST_QUEUE_LENGTH = "request-queue-length";


	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		ACLMessage msg = myAgent.receive(mt);

		if (msg != null) {
			CrossroadAgent xroad = (CrossroadAgent) myAgent;

			// process message 
			String subject = msg.getContent();
			ACLMessage reply = msg.createReply();

			// respond with queues length
			if (subject.equals(REQUEST_QUEUE_LENGTH)) {
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
			} else {
				System.err.println("Error: Msg with unknown content [" + subject + "] came to CrossroadStatusInformant");
			}

			myAgent.send(reply);
		}

		else {
			block();
		}

	}
}
