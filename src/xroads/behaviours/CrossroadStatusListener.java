package xroads.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

import xroads.CrossroadStatus;
import xroads.agents.SpawnerAgent;

@SuppressWarnings("serial")
public class CrossroadStatusListener extends CyclicBehaviour {

	private String conversationId = null;


	public CrossroadStatusListener(String conversationId) {
		super();

		// pro vsechny konverzace mezi spawnerem a krizovatkama
		// tykajici se jejich stavu budeme pouzivat stejne ID
		this.conversationId = conversationId;
	}



	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchConversationId(conversationId);
		ACLMessage msg = myAgent.receive(mt);
		if (msg != null) {

			String serialized = msg.getContent();
			CrossroadStatus s = null;

			try {
				s = CrossroadStatus.deserialize(serialized);
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Error: SpawnerAgent.requestCrossroadStatus recieved malformed message");
				e.printStackTrace();
				return;
			}

			// predat info zpet agentovi
			SpawnerAgent spawner = (SpawnerAgent) myAgent;
			spawner.onCrossroadStatusUpdate(s);
		}

		else {
			block();
		}
	}
}