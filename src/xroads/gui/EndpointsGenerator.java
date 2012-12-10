package xroads.gui;


import java.util.ArrayList;
import java.util.Random;

public class EndpointsGenerator {
	int gridWidth;
	int gridHeight;
	
	Random generator;
	
	ArrayList<String> endpointNames = new ArrayList<String>();
	
	public EndpointsGenerator(long time) {
		generator = new Random(time);
	}
	
	public void regenerateEndpoints(int pGridWidth, int pGridHeight) {
		this.gridWidth = pGridWidth;
		this.gridHeight = pGridHeight;
				
		// north, south
		for (int i = 0; i < gridWidth; i++) {
			endpointNames.add("endpoint-n-" + i);
			endpointNames.add("endpoint-s-" + i);	
		}
		
		// east, west
		for (int i = 0; i < gridHeight; i++) {
			endpointNames.add("endpoint-e-" + i);
			endpointNames.add("endpoint-w-" + i);	
		}
	}
	
	/**
	 * Vrati nahodne zvoleny endpoint
	 * @return null pokud je prazdny
	 * @return
	 */
	public String getRandomEndPoint() {
		if(endpointNames.size() == 0) {
				return null;
		} 
		return endpointNames.get(generator.nextInt((gridWidth + gridHeight) * 2));
	}
}
