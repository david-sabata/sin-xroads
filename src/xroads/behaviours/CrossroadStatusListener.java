package xroads.behaviours;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.util.UUID;

import xroads.CrossroadStatus;
import xroads.agents.SpawnerAgent;

@SuppressWarnings("serial")
public class CrossroadStatusListener extends OneShotBehaviour {

	private String crossroadName = null;
	private String conversationId = null;


	public CrossroadStatusListener(String crossroadName) {
		super();

		this.crossroadName = crossroadName;
	}



	@Override
	public void action() {
		// poslat dotaz
		if (conversationId == null) {
			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.setConversationId(UUID.randomUUID().toString());
			request.addReceiver(new AID(crossroadName, AID.ISLOCALNAME));
			myAgent.send(request);

			conversationId = request.getConversationId();
		}

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