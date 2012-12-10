package xroads.behaviours.xroadfsm;

import jade.core.behaviours.OneShotBehaviour;

/**
 * Toto chovani nic nedela
 * 
 * Pouziva se jako udalost ktera se ma spustit pote co 
 * se krizovatka probere ze spanku. Nasim cilem je aby presla 
 * ve svem FSM do dalsiho stavu; teprve tam se prepinaji svetla.
 */
@SuppressWarnings("serial")
public class NullBehaviour extends OneShotBehaviour {

	@Override
	public void action() {
		// do nothing
	}

}
