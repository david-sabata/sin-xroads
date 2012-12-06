package xroads.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import xroads.Constants;
import xroads.agents.CrossroadAgent.QueueStatus;
import xroads.agents.SpawnerAgent;

/**
 * Naslouchani na odezvy krizovatek s oznamenim o delkach jejich front.
 * Pripojovat POUZE k SpawnerAgentovi, auta budou naslouchat jinak, synchronne.
 */
@SuppressWarnings("serial")
public class QueueLengthListener extends CyclicBehaviour {

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(mt);

		if (msg != null) {
			SpawnerAgent agent = (SpawnerAgent) myAgent;

			String subject = msg.getContent();
			String dirs[] = subject.split("|");

			if (dirs.length != 4) {
				System.err.println("QueueLengthListener recieved INFORM in bad format [" + subject + "]");
				return;
			}

			QueueStatus s = new QueueStatus();

			// pro kazdy smer
			for (int dir : Constants.DIRECTIONS) {
				String parts[] = dirs[dir].split("/");

				if (parts.length != 2) {
					System.err.println("QueueLengthListener recieved INFORM in bad format [" + subject + "]");
					return;
				}

				s.actualLength[dir] = Integer.parseInt(parts[0]);
				s.maximumLength[dir] = Integer.parseInt(parts[1]);
			}

			// predat info zpet agentovi
			agent.onQueueLengthUpdate(s);
		}

		else {
			block();
		}
	}


}
