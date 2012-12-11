package xroads.behaviours.carfsm;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.util.UUID;

import xroads.Constants;
import xroads.CrossroadStatus;
import xroads.agents.CarAgent;

/**
 * Ceka na zelenou na aktualni krizovatce
 */
@SuppressWarnings("serial")
public class WaitForGreenBehaviour extends Behaviour {

	private String conversationId = null;
	private boolean isGreenLight = false;

	/**
	 * Pri kazdem prechodu do tohoto stavu je poterba resetovat vnitrni promenne
	 */
	@Override
	public void onStart() {
		isGreenLight = false;
		conversationId = null;

		System.out.println("Car [" + myAgent.getLocalName() + "] waits for green light at [" + ((CarAgent) myAgent).getCurrentCrossroad() + "]");
	}




	@Override
	public void action() {
		CarAgent agent = (CarAgent) myAgent;

		// poslat dotaz na stav krizovatky na ktere auto stoji
		if (conversationId == null) {
			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.addReceiver(new AID(agent.getCurrentCrossroad(), AID.ISLOCALNAME));
			request.setConversationId(UUID.randomUUID().toString());
			myAgent.send(request);

			conversationId = request.getConversationId();
		}

		// cekat na odpoved
		MessageTemplate mt = MessageTemplate.MatchConversationId(conversationId);
		ACLMessage msg = myAgent.receive(mt);

		if (msg != null) {
			String serialized = msg.getContent();
			CrossroadStatus s = null;

			try {
				s = CrossroadStatus.deserialize(serialized);
			} catch (ClassNotFoundException e) {
				System.err.println("Error: CarOverallBehaviour.waitForGreen recieved malformed message");
				e.printStackTrace();

				myAgent.doDelete();
				return;
			} catch (IOException e) {
				System.err.println("Error: CarOverallBehaviour.waitForGreen recieved malformed message");
				e.printStackTrace();

				myAgent.doDelete();
				return;
			}

			isGreenLight = (s.directions[agent.getCurrentDirection()].semaphore == Constants.GREEN);

			// pokud je cervena, chceme zacit novou konverzaci (novy pozadavek)
			if (!isGreenLight)
				conversationId = null;
		} else {
			block();
		}
	}



	@Override
	public boolean done() {
		return isGreenLight;
	}

}
