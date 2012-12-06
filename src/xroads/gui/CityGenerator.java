package xroads.gui;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import xroads.Constants;
import xroads.agents.CrossroadAgent;

@SuppressWarnings("serial")
public class CityGenerator extends JTable {
	private int width;
	private int height;
	private int gridWidth; //original 
	private int gridHeight; //original
	
	private DefaultTableModel tableModel;
	private CellRenderer renderer;
	
	/**
	 * Delete everything from table and generate new table with new params (width, height)
	 * @param width
	 * @param height
	 */
	public void generateCity(int width, int height) {
		if(width > 0 && height > 0) {
			this.gridWidth = width;
			this.gridHeight = height;
			
			this.width = calculateWithRoads(width);
			this.height = calculateWithRoads(height);
	
			tableModel = new DefaultTableModel(this.width, this.height);
						
			this.setModel(tableModel);
			
			// Add cross roads, roads, in out to grid
			renderer = new CellRenderer(tableModel);
			this.setDefaultRenderer(Object.class , renderer);
			
			
			
			// Resize table layout to size of city
			resizeTable();
		}
	}

	/**
	 * Return number of culomus (or row) with our graphics
	 * @param n
	 * @return
	 */
	private int calculateWithRoads(int n) {
		return (n * 2) + n + 3;
	}
	
	

	private void resizeTable() {
		int widthTable = 0 ;
		int heightTable = 0;
		
		// Get Width of table
		for(int i = 0; i < this.getRowCount(); i++) {
			heightTable += this.getRowHeight(i);
		}
		
		// Get Height of table
		widthTable += this.getPreferredSize().width; 
		
		//this.setAlignmentX(widthTable);
		//this.setAlignmentY(heightTable);
		// Resize table
		this.setPreferredSize(new Dimension(widthTable, heightTable));
		
	}
	
	/**
	 * Update value at all fronts
	 * @param x
	 * @param y
	 */
	public void updateCrossRoadAt(CrossroadAgent.QueueStatus s) {	
		String name = s.crossroadName;
		int pos = Integer.parseInt((name.split("-"))[1]);
		
		int x = pos / gridWidth;
		int y = pos % gridWidth;
		x *= 3;
		y *= 3;
		
		//WEST
		CoordRoad west = coordCrossRoadWest(x, y);
		String westValue = Integer.toString(s.actualLength[Constants.WEST]) + "/" + Integer.toString(s.maximumLength[Constants.WEST]);
		
		// EAST
		CoordRoad east = coordCrossRoadEast(x, y);
		String eastValue = Integer.toString(s.actualLength[Constants.EAST]) + "/" + Integer.toString(s.maximumLength[Constants.EAST]);
		
		// NORTH
		CoordRoad north = coordCrossRoadNorth(x, y);
		String northValue = Integer.toString(s.actualLength[Constants.NORTH]) + "/" + Integer.toString(s.maximumLength[Constants.NORTH]);
		
		// SOUTH
		CoordRoad south = coordCrossRoadSouth(x, y);
		String southValue = Integer.toString(s.actualLength[Constants.SOUTH]) + "/" + Integer.toString(s.maximumLength[Constants.SOUTH]);
		
		
		tableModel.setValueAt(westValue, west.getY(), west.getX());
		tableModel.setValueAt(eastValue, east.getY(), east.getX());
		tableModel.setValueAt(northValue, north.getY(), north.getX());
		tableModel.setValueAt(southValue, south.getY(), south.getX());
	
	}
	
	/**
	 *  Transfer coordinate for West road in crossroad
	 * @return
	 */
	private CoordRoad coordCrossRoadWest(int x, int y) {
		
		return  new CoordRoad(x + 1, y + 3);
	}
	
	/**
	 *  Transfer coordinate for West road in crossroad
	 * @return
	 */
	private CoordRoad coordCrossRoadEast(int x, int y) {
		return  new CoordRoad(x + 4, y + 2);
	}
	
	/**
	 *  Transfer coordinate for West road in crossroad
	 * @return
	 */
	private CoordRoad coordCrossRoadNorth(int x, int y) {
		return  new CoordRoad(x + 2, y + 1);
	}
	
	/**
	 *  Transfer coordinate for West road in crossroad
	 * @return
	 */
	private CoordRoad coordCrossRoadSouth(int x, int y) {
		return  new CoordRoad(x + 3, y + 4);
	}
	
	
	
	/**
	 * To store X, Y coord of roads of crossRoad
	 * @author xsych_000
	 *
	 */
	public class CoordRoad {
		private int x;
		private int y;
		
		public CoordRoad(int x , int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}		
	}
	
}



