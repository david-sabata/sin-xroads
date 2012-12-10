package xroads.behaviours.xroadfsm;

import jade.core.behaviours.FSMBehaviour;

/**
 * Prepinani svetel krizovatky pomoci nekonecneho mini-fsm. 
 * Krizovatka v jednom stavu spi a ceka na timeout, ve druhem 
 * se rozhoduje jak svetla prepne.
 *  
 * Dulezite je ze stav ktery prepina svetla soucasne vraci 
 * hodnotu ktera udava jak dlouho se bude v nasledujicim stavu 
 * spat. Tim je mozne odlozit pristi prepnuti svetel na 
 * ruznou dobu podle toho, jak moc je krizovatka zaplnena.  
 */
@SuppressWarnings("serial")
public class CrossroadLightsBehaviour extends FSMBehaviour {

	private static final String STATE_NULL = "null state";
	private static final String STATE_SLEEP = "sleeping";
	private static final String STATE_SWITCH = "switching";


	public CrossroadLightsBehaviour() {
		super();

		//registerFirstState(new NullBehaviour(), STATE_NULL);
		registerFirstState(new TransitionBehaviour(), STATE_SWITCH);
		registerState(new SleeperBehaviour(myAgent), STATE_SLEEP);

		//registerDefaultTransition(STATE_NULL, STATE_SWITCH);
		registerDefaultTransition(STATE_SWITCH, STATE_SLEEP);
		registerDefaultTransition(STATE_SLEEP, STATE_SWITCH, new String[] { STATE_SLEEP });
	}


}
