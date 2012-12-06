package xroads.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;

import xroads.Constants;
import xroads.agents.CrossroadAgent;

/**
 * Chovani krizovatky, ktere informuje tazatele o poctu aut ve 
 * sve fronte a o jeji maximalni delce.
 * Dotaz je typu AclMessage.REQUEST s obsahem QueueLengthInformBehaviour.REQUEST_QUEUE_LENGTH
 * Odpoved je typu AclMessage.INFORM s obsahem [aktualni delka N]/[maximalni delka N]|[aktualni delka S]/[maximalni delka S]|...
 */
@SuppressWarnings("serial")
public class QueueLengthInformBehaviour extends CyclicBehaviour {

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

				String content = "";
				for (int dir : Constants.DIRECTIONS) {
					ArrayList<String> queue = xroad.carQueue.get(dir);
					// TODO content += queue.size() + "/" + xroad.maxQueueLength;
					content += 7 + "/" + xroad.maxQueueLength;
					if (dir != Constants.WEST)
						content += "|";
				}

				reply.setContent(content);
			} else {
				System.err.println("Error: Msg with unknown content [" + subject + "] came to QueueLengthInformBehaviour");
			}

			myAgent.send(reply);
		}

		else {
			block();
		}

	}
}
