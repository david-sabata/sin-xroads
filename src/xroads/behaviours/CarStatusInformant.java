package xroads.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

import xroads.CarStatus;
import xroads.agents.CarAgent;

@SuppressWarnings("serial")
public class CarStatusInformant extends CyclicBehaviour {

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		ACLMessage msg = myAgent.receive(mt);

		if (msg != null) {
			CarAgent car = (CarAgent) myAgent;

			// process message 
			ACLMessage reply = msg.createReply();

			// respond with queues length
			reply.setPerformative(ACLMessage.INFORM);

			CarStatus status = car.getStatus();
			String serialized = null;

			try {
				serialized = status.serialize();
			} catch (IOException e) {
				System.err.println("Error: Unable to compose message with car status");
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
