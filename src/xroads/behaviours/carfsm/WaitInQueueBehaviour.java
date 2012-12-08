package xroads.behaviours.carfsm;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Ceka ve fronte dokud nebude prvni. To je symbolizovano 
 * prichozi zpravou INFORM
 */
public class WaitInQueueBehaviour extends Behaviour {

	private boolean isFirstInQueue = false;

	/**
	 * Pri kazdem prechodu do tohoto stavu je potreba resetovat vnitrni promenne
	 */
	@Override
	public void onStart() {
		isFirstInQueue = false;
	}


	@Override
	public void action() {
		System.out.println("Car waits in queue");

		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(mt);

		if (msg != null) {
			isFirstInQueue = true;
		} else {
			block();
		}
	}

	@Override
	public boolean done() {
		return isFirstInQueue;
	}
}
