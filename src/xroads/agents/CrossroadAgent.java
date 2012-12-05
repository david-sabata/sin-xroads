package xroads.agents;

import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CrossroadAgent extends Agent {

	private int gridPosition = -1;
	private int gridWidth = -1;
	private int gridHeight = -1;


	/**
	 * Pamatujeme si radeji jmena agentu nez jejich objekty
	 */
	public List<String> carQueue = new ArrayList<String>();



	protected void setup() {
		Object args[] = getArguments();
		if (args.length != 3) {
			System.err.println("Unexpected arguments for CrossroadAgent. Call with <gridWidth> <gridHeight> <position>");
			doDelete();
		}

		gridWidth = Integer.parseInt(args[0].toString());
		gridHeight = Integer.parseInt(args[1].toString());
		gridPosition = Integer.parseInt(args[2].toString());

		System.out.println("CrossroadAgent " + getAID().getName() + " is ready on grid " + gridWidth + "x" + gridHeight + " @ " + gridPosition);
	}

}