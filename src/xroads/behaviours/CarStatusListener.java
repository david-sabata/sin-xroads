package xroads.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

import xroads.CarStatus;
import xroads.agents.SpawnerAgent;

@SuppressWarnings("serial")
public class CarStatusListener extends CyclicBehaviour {
	private String conversationId = null;


	public CarStatusListener(String conversationId) {
		super();

		// pro vsechny konverzace mezi spawnerem a autama
		// tykajici se jejich stavu budeme pouzivat stejne ID
		this.conversationId = conversationId;
	}



	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchConversationId(conversationId);
		ACLMessage msg = myAgent.receive(mt);
		if (msg != null) {

			String serialized = msg.getContent();
			CarStatus s = null;

			try {
				s = CarStatus.deserialize(serialized);
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Error: SpawnerAgent.requestCrossroadStatus recieved malformed message");
				e.printStackTrace();
				return;
			}

			// predat info zpet agentovi
			SpawnerAgent spawner = (SpawnerAgent) myAgent;
			spawner.onCarStatusUpdate(s);
		}

		else {
			block();
		}
	}
}
