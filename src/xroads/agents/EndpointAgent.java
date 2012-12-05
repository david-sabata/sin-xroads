package xroads.agents;

import jade.core.Agent;

/**
 * Agent "koncovka", vytvoreny Spawn agentem po uvodnim nadefinovani velikosti
 * mrizky krizovatek.
 * 
 * Jmeno je vzdy ve tvaru endpoint-[n|s|e|w]-[0-9], kde ciselna cast je zero-based 
 * index sloupce (pro N|S agenty; indexovano zleva), pripadne radku (pro E|W agenty, 
 * indexovano shora).
 */
@SuppressWarnings("serial")
public class EndpointAgent extends Agent {

	protected void setup() {
		System.out.println("EndpointAgent " + getAID().getName() + " is ready");
	}

}
