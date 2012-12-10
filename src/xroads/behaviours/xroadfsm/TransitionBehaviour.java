package xroads.behaviours.xroadfsm;

import jade.core.behaviours.OneShotBehaviour;

import java.util.Random;

import xroads.Constants;
import xroads.CrossroadStatus;
import xroads.DirectionStatus;
import xroads.World;
import xroads.agents.CrossroadAgent;

/**
 * Prepina svetla na krizovatce a svoji navratovou hodnotou
 * udava za jak dlouho se semafor probudi priste.
 * Probuzeni nemusi nutne znamenat prepnuti svetel. Je mozne 
 * ze krizovatka vyhodnoti ze neni potreba prepnout, protoze 
 * v prujezdnem smeru je jeste stale moc aut.
 * 
 * Je zde ale pojistka proti vyhladoveni ve forme nejvyssiho 
 * mozneho casu po ktery bude uzavreny smer cekat.
 */
@SuppressWarnings("serial")
public class TransitionBehaviour extends OneShotBehaviour {


	/**
	 * Timeout do pristiho probuzeni a pripadneho prepnuti
	 */
	private int nextSwitchTimeout = 30 * World.TIMESTEP;


	/**
	 * Maximalni cas po ktery nemusi dojit ke zmene smeru
	 */
	private int maxWaitingTime = 4 * nextSwitchTimeout;


	/**
	 * Doba po kterou uz uzavreny smer stoji. Pri kazdem spusteni 
	 * pricitame dobu timeoutu.
	 */
	private int actualWaitingTime = 0;



	@Override
	public void action() {
		// cekame o [timeout] dele
		actualWaitingTime += nextSwitchTimeout;

		// kontrola limitu na stani - vynutit prepnuti
		if (actualWaitingTime >= maxWaitingTime) {
			switchLights();
			actualWaitingTime = 0;
			return;
		}

		CrossroadAgent xroad = (CrossroadAgent) myAgent;
		CrossroadStatus status = xroad.getStatus();

		int sizeN = status.directions[Constants.NORTH].carQueue.size();
		int sizeS = status.directions[Constants.SOUTH].carQueue.size();
		int sizeE = status.directions[Constants.EAST].carQueue.size();
		int sizeW = status.directions[Constants.WEST].carQueue.size();

		if (sizeN + sizeS > sizeE + sizeW) {
			// v plnejsim smeru je cervena, prepneme
			if (status.directions[Constants.NORTH].semaphore != Constants.GREEN) {
				switchLights();
				actualWaitingTime = 0;
				return;
			}
		} else {
			// v plnejsim smeru je cervena, prepneme
			if (status.directions[Constants.EAST].semaphore != Constants.GREEN) {
				switchLights();
				actualWaitingTime = 0;
				return;
			}
		}

	}


	/**
	 * Prepne semafory ze soucasneho stavu na opacny
	 */
	private void switchLights() {
		CrossroadAgent xroad = (CrossroadAgent) myAgent;
		CrossroadStatus status = xroad.getStatus();

		DirectionStatus north = status.directions[Constants.NORTH];
		DirectionStatus south = status.directions[Constants.SOUTH];
		DirectionStatus east = status.directions[Constants.EAST];
		DirectionStatus west = status.directions[Constants.WEST];

		if (north.semaphore == Constants.GREEN && south.semaphore == Constants.GREEN) {
			north.semaphore = Constants.RED;
			south.semaphore = Constants.RED;
			east.semaphore = Constants.GREEN;
			west.semaphore = Constants.GREEN;
		} else {
			north.semaphore = Constants.GREEN;
			south.semaphore = Constants.GREEN;
			east.semaphore = Constants.RED;
			west.semaphore = Constants.RED;
		}
	}




	@Override
	public int onEnd() {
		Random r = new Random();

		return World.TIMESTEP * (1 + r.nextInt(5));
	}




}
