package xroads.behaviours.carfsm;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.MessageTemplate;
import xroads.World;

/**
 * Toto chovani ma simulovat zpozdeni auta pri projizdeni krizovatkou.
 * Pri prechodu do tohoto stavu je jiz auto formalne zarazene do 
 * fronty krizovatky a pokud je ve fronte jedine, pravdepodobne uz dostalo 
 * i oznameni o tom ze je na rade.
 * 
 * Zde ale cilene nastavujeme blokujici cekani na zpravu ktera nikdy 
 * nemuze prijit (cekani na jakoukoliv zpravu by agenta vzbudila brzy).
 * Take pouzivame uspani primo v agentovi a ne v behaviour, coz ma za nasledek 
 * uspani celeho agentskeho vlakna a vsech jeho chovani. Diky tomu ho neprobudi 
 * pripadny prubeh vedlejsich chovani.
 */
public class QueueDelayBehaviour extends OneShotBehaviour {

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchConversationId("dummy message from hell");
		long delay = 5 * World.TIMESTEP;
		myAgent.blockingReceive(mt, delay);
	}

}
