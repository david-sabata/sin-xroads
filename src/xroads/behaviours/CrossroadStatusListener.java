package xroads.behaviours;


import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

import xroads.CrossroadStatus;
import xroads.agents.SpawnerAgent;

/**
 * Naslouchani na odezvy krizovatek s oznamenim o jejich stavu.
 * Pripojovat POUZE k SpawnerAgentovi, auta budou naslouchat jinak, synchronne.
 */
@SuppressWarnings("serial")
public class CrossroadStatusListener extends CyclicBehaviour {

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(mt);

		if (msg != null) {
			SpawnerAgent agent = (SpawnerAgent) myAgent;

			String serialized = msg.getContent();
			CrossroadStatus s = null;

			try {
				s = CrossroadStatus.deserialize(serialized);
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Error: CrossroadStatusListener recieved malformed message");
				e.printStackTrace();

				myAgent.doDelete();
				return;
			}

			// predat info zpet agentovi
			agent.onCrossroadStatusUpdate(s);
		}

		else {
			block();
		}
	}
}
