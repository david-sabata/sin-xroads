package xroads.behaviours;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import xroads.agents.CarAgent;

/**
 * Jednorazova udalost spoustena pri vytvoreni auta, slouzici k 
 * informovani koncovky ze je do ni auto zarazeno. Tuto zpravu 
 * musi zasilat samo auto a nemuze to provest spawner.
 */
public class CarNewbornBehaviour extends OneShotBehaviour {

	@Override
	public void action() {
		CarAgent car = (CarAgent) myAgent;

		ACLMessage request = new ACLMessage(ACLMessage.PROPOSE);
		request.addReceiver(new AID(car.getCurrentCrossroad(), AID.ISLOCALNAME));
		request.setContent(String.valueOf(car.getCurrentDirection()));
		myAgent.send(request);

	}
}
