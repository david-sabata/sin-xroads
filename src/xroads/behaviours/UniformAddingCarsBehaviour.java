package xroads.behaviours;

import xroads.agents.SpawnerAgent;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;


@SuppressWarnings("serial")
public class UniformAddingCarsBehaviour extends TickerBehaviour {

	private  final int allCars;
	private SpawnerAgent spawnerAgent;
	
	private String startPoint;
	private String endPoint;
	
	private int addedCars = 0;
	
	public UniformAddingCarsBehaviour(Agent pSpawnerAgent, long pPeriod, int pNumberOfCars, String pStartPoint, String pEndPoint) {
		
		super(pSpawnerAgent, pPeriod);
		
		// number of cars to generate
		allCars = pNumberOfCars;
		spawnerAgent = (SpawnerAgent) pSpawnerAgent;
		startPoint = pStartPoint;
		endPoint = pEndPoint;
		
		System.out.println(pPeriod);
	}

	@Override
	protected void onTick() {
		if(addedCars < allCars) {
			// generate new car
			spawnerAgent.spawnCarsFromTo(startPoint, endPoint);		
			addedCars += 1;		
		} else {
			System.out.println("All cars in selected interval was generated!");
			stop();
		}		
	}
}
