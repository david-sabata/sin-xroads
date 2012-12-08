package xroads.agents;

import xroads.Constants;
import xroads.DirectionStatus;
import xroads.World;
import xroads.behaviours.AcceptCarBehaviour;
import xroads.behaviours.CrossroadStatusInformant;
import xroads.behaviours.ReleaseCarBehaviour;


/**
 * Agent "koncovka", vytvoreny Spawn agentem po uvodnim nadefinovani velikosti
 * mrizky krizovatek. Dedi z krizovatky, ale neobsahuje vsechno jeji chovani.
 * Narozdil od ni ma taky neomezene velikosti front (resp. hodne velke cislo).
 * 
 * Jmeno je vzdy ve tvaru endpoint-[n|s|e|w]-[0-9], kde ciselna cast je zero-based 
 * index sloupce (pro N|S agenty; indexovano zleva), pripadne radku (pro E|W agenty, 
 * indexovano shora).
 */
@SuppressWarnings("serial")
public class EndpointAgent extends CrossroadAgent {

	@Override
	protected void setup() {
		gridPosition = World.getAgentCoords(getLocalName());

		// inicializace krizovatky
		crossroadStatus.name = getAID().getLocalName();
		crossroadStatus.position = gridPosition;
		for (int dir : Constants.DIRECTIONS) {
			crossroadStatus.directions[dir] = new DirectionStatus();
			crossroadStatus.directions[dir].maximumLength = Integer.MAX_VALUE;
		}


		// informuje o delce fronty
		addBehaviour(new CrossroadStatusInformant());

		// radi prijizdejici auta do front
		addBehaviour(new AcceptCarBehaviour());

		// auta opousteji krizovatku
		addBehaviour(new ReleaseCarBehaviour());
	}

}
