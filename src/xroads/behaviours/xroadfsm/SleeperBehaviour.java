package xroads.behaviours.xroadfsm;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;

/**
 * Nutna implementace abstraktniho CustomWakerBehaviour, ktery 
 * predstavuje upraveny WakerBehaviour nastavujici svuj timeout 
 * vzdy podle navratove hodnoty predchoziho stavu FSM ve kterem 
 * je zapojeny.
 */
@SuppressWarnings("serial")
public class SleeperBehaviour extends CustomWakerBehaviour {

	/**
	 * Pamatujeme si navratovou hodnotu posledniho stavu, 
	 * protoze do onStart prichazi spravna, ale do CustomWakerBehaviour 
	 * se nepropaguje. Tam se naopak z podivneho duvodu 
	 * dostava ta, kterou vrati zdejsi onEnd
	 */
	private int lastExitValue = -1;



	public SleeperBehaviour(Agent a) {
		super(a, true);
	}




	@Override
	public void onStart() {
		FSMBehaviour fsm = (FSMBehaviour) parent;
		lastExitValue = fsm.getLastExitValue();
	}



	@Override
	public int onEnd() {
		return lastExitValue;
	}

}
