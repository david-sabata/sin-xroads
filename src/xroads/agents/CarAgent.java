package xroads.agents;

import jade.core.Agent;

import java.sql.Date;
import java.util.regex.Pattern;

import xroads.CarStatus;
import xroads.World;
import xroads.behaviours.CarNewbornBehaviour;
import xroads.behaviours.CarStatusInformant;
import xroads.behaviours.carfsm.CarOverallBehaviour;

@SuppressWarnings("serial")
public class CarAgent extends Agent {

	private String sourceCrossroad;
	private String currentCrossroad;
	private String destinationCrossroad;
	private long timestampStart = 0;
	private long timestampEnd = 0;
	
	/**
	 * Smer ze ktere auto stoji na aktualni krizovatce
	 */
	private int currentDirection;


	private String nextHopCrossroad;
	private int nextHopDirection;


	@Override
	protected void setup() {
		Object args[] = getArguments();
		if (args.length != 2) {
			System.err.println("Unexpected arguments for CarAgent. Call with <src> <dst>");
			doDelete();
		}

		sourceCrossroad = args[0].toString();
		currentCrossroad = sourceCrossroad;
		destinationCrossroad = args[1].toString();

		// auto stoji na zacatku vzdy v koncovce ze ktere muzeme vyparsovat smer
		String parts[] = currentCrossroad.split(Pattern.quote("-"));
		if (!parts[0].equals("endpoint")) {
			throw new RuntimeException("Error: Car needs to be spawned at endpoint");
		}
		currentDirection = World.parseDirection(parts[1]);

		timestampStart = System.currentTimeMillis();
				
		// informovat koncovku ze v ni je auto
		addBehaviour(new CarNewbornBehaviour());

		addBehaviour(new CarStatusInformant());

		// komplexni chovani auta definovane pomoci FSM
		addBehaviour(new CarOverallBehaviour());
	}


	/**
	 * Oznamuje autu, ze se presunulo do nove krizovatky
	 * 
	 * Volano pouze z CarOVerallBehaviour !!!
	 */
	public void setCurrentCrossroad(String newCurrent, int newCurrentDir) {
		currentCrossroad = newCurrent;
		currentDirection = newCurrentDir;

		//		System.out.println("Car is at [" + newCurrent + "] in direction [" + newCurrentDir + "]");
	}




	public String getDestinationCrossroad() {
		return destinationCrossroad;
	}

	public String getCurrentCrossroad() {
		return currentCrossroad;
	}

	public int getCurrentDirection() {
		return currentDirection;
	}

	public long getStartTimestamp() {
		return timestampStart;
	}
	
	public long getEndTimestamp() {
		return timestampEnd;
	}

	public void setNextHopCrossroad(String crossroad, int direction) {
		nextHopCrossroad = crossroad;
		nextHopDirection = direction;
	}

	public String getNextCrossroad() {
		return nextHopCrossroad;
	}

	public int getNextCrossroadDir() {
		return nextHopDirection;
	}


	/**
	 * Vraci objekt se stavem auta
	 */
	public CarStatus getStatus() {
		CarStatus s = new CarStatus();
		s.name = getLocalName();
		s.currentCrossroad = getCurrentCrossroad();
		s.destinationCrossroad = getDestinationCrossroad();
		s.startTime = getStartTimestamp();
		s.endTime = getEndTimestamp();
		return s;
	}


	public void setTimestampEnd(long currentTimeMillis) {
	   timestampEnd = currentTimeMillis;	
	}
}
