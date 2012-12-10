package xroads.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import xroads.Constants;
import xroads.CrossroadStatus;
import xroads.DirectionStatus;
import xroads.Position;
import xroads.World;
import xroads.behaviours.AcceptCarBehaviour;
import xroads.behaviours.CrossroadStatusInformant;
import xroads.behaviours.ReleaseCarBehaviour;
import xroads.behaviours.xroadfsm.CrossroadLightsBehaviour;

/**
 * Krizovatka
 * 
 * Samostatne prepina svetla, nejdrive ve vychozim intervalu, 
 * pozdeji si casy prepnuti reguluje sama (TODO).
 * 
 * Reaguje na dotazy (zprava typu REQUEST s contentem REQUEST_QUEUE_LENGTH) 
 * na stav - vraci zaplnenost svych prijezdovych cest a stavy semaforu.
 * 
 * Reaguje na snahy aut zaradit se (zprava typu PROPOSE s contentem oznacujicim 
 * smer ze ktereho auto prijizdi), auta zarazuje do front a o zarazeni je 
 * informuje zpet (ACCEPT_PROPOSAL | REJECT_PROPOSAL).
 */
@SuppressWarnings("serial")
public class CrossroadAgent extends Agent {

	protected Position gridPosition;


	/**
	 * Pamatujeme si radeji jmena agentu nez jejich objekty
	 */
	protected CrossroadStatus crossroadStatus = new CrossroadStatus();



	@Override
	protected void setup() {
		gridPosition = World.getAgentCoords(getLocalName());

		// inicializace krizovatky
		crossroadStatus.name = getAID().getLocalName();
		crossroadStatus.position = gridPosition;
		for (int dir : Constants.DIRECTIONS) {
			crossroadStatus.directions[dir] = new DirectionStatus();
		}


		// informuje o delce fronty
		addBehaviour(new CrossroadStatusInformant());

		// prepina semafory
		addBehaviour(new CrossroadLightsBehaviour());

		// radi prijizdejici auta do front
		addBehaviour(new AcceptCarBehaviour());

		// auta opousteji krizovatku
		addBehaviour(new ReleaseCarBehaviour());
	}

	/**
	 * Vraci objekt reprezentujici stav krizovatky
	 */
	public CrossroadStatus getStatus() {
		return crossroadStatus;
	}


	/**
	 * Zaradi do fronty z daneho smeru auto.
	 * Pokud se auto do fronty nevleze vraci false
	 */
	public boolean enqueueCar(int direction, String agentName) {
		DirectionStatus dirStatus = crossroadStatus.directions[direction];

		if (dirStatus.carQueue.size() < dirStatus.maximumLength) {
			dirStatus.carQueue.add(agentName);

			// pokud je auto ve fronte jedine, rovnou mu zasleme info ze je prvni
			final String carAgent = dirStatus.carQueue.get(0);

			addBehaviour(new OneShotBehaviour() {
				@Override
				public void action() {
					ACLMessage request = new ACLMessage(ACLMessage.INFORM);
					request.addReceiver(new AID(carAgent, AID.ISLOCALNAME));
					myAgent.send(request);
				}
			});

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Odebere z fronty daneho smeru auto a pokud jsou ve fronte dalsi 
	 * auta, posle prvnimu z nich oznameni ze je na rade
	 */
	public void unqueueCar(int direction, String agentName) {
		DirectionStatus dirStatus = crossroadStatus.directions[direction];
		dirStatus.carQueue.remove(agentName);

		// pokud je ve fronte dalsi auto, informujeme ho ze je prvni
		if (dirStatus.carQueue.size() > 0) {
			final String carAgent = dirStatus.carQueue.get(0);

			addBehaviour(new OneShotBehaviour() {
				@Override
				public void action() {
					ACLMessage request = new ACLMessage(ACLMessage.INFORM);
					request.addReceiver(new AID(carAgent, AID.ISLOCALNAME));
					myAgent.send(request);
				}
			});

		}
	}

}