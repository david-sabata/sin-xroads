package xroads.agents;

import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;

import xroads.behaviours.QueueLengthInformBehaviour;

@SuppressWarnings("serial")
public class CrossroadAgent extends Agent {


	private int gridPosition = -1;
	private int gridWidth = -1;
	private int gridHeight = -1;


	/**
	 * Pamatujeme si radeji jmena agentu nez jejich objekty
	 */
	public List<ArrayList<String>> carQueue = new ArrayList<ArrayList<String>>();

	/**
	 * Maximalni pocet aut stojici u teto krizovatky
	 */
	public int maxQueueLength = 10;




	@Override
	protected void setup() {
		Object args[] = getArguments();
		if (args.length != 3) {
			System.err.println("Unexpected arguments for CrossroadAgent. Call with <gridWidth> <gridHeight> <position>");
			doDelete();
		}

		// pripravit objekty pro fronty
		for (int i = 0; i < 4; i++) {
			ArrayList<String> l = new ArrayList<String>();
			carQueue.add(l);
		}

		gridWidth = Integer.parseInt(args[0].toString());
		gridHeight = Integer.parseInt(args[1].toString());
		gridPosition = Integer.parseInt(args[2].toString());

		//		System.out.println("CrossroadAgent " + getAID().getName() + " is ready on grid " + gridWidth + "x" + gridHeight + " @ " + gridPosition);

		// informuje o delce fronty
		addBehaviour(new QueueLengthInformBehaviour());
	}





	/**
	 * Prenoska pro informaci o stavu fronty na krizovatce.
	 * Pole jsou indexovana konstantami NORTH, EAST, SOUTH, WEST
	 */
	public static class QueueStatus {
		public int actualLength[] = new int[4];
		public int maximumLength[] = new int[4];
	}





}