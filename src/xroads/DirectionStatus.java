package xroads;

import java.io.Serializable;
import java.util.ArrayList;

public class DirectionStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1229926952785698818L;


	/**
	 * Maximalni delka fronty
	 */
	public int maximumLength = 15;

	/**
	 * Samotna fronta aut, obsahujici jejich agentska jmena
	 */
	public ArrayList<String> carQueue = new ArrayList<String>();


	/**
	 * Stav semaforu (konstanty RED, ORANGE, GREEN)
	 */
	public int semaphore = Constants.ORANGE;

}
