package xroads.gui;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class CityGenerator extends JTable {
	private int width;
	private int height;
	
	private DefaultTableModel tableModel;
	
	/**
	 * Delete everything from table and generate new table with new params (width, height)
	 * @param width
	 * @param height
	 */
	public void generateCity(int width, int height) {
		this.width = calculateWithRoads(width);
		this.height = calculateWithRoads(height);

		tableModel = new DefaultTableModel(this.width, this.height);
		this.setModel(tableModel);
				
		// Add cross roads, roads, in out to grid
		addGraphics();
		// Resize table layout to size of city
		resizeTable();
		
	}

	/**
	 * Return number of culomus (or row) with our graphics
	 * @param n
	 * @return
	 */
	private int calculateWithRoads(int n) {
		return (n * 2) + n + 1;
	}

	/**
	 * Add graphics (road, cross road, in, out)
	 */
	private void addGraphics() {
		addCrossRoad();
		addInOut();
		addRoads();
	}
	
	
	private void addRoads() {
		// TODO Auto-generated method stub
		
	}

	private void addInOut() {
		// TODO Auto-generated method stub
		
	}

	private void addCrossRoad() {
		// TODO Auto-generated method stub
		
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
		
		this.setAlignmentX(widthTable);
		this.setAlignmentY(heightTable);
		// Resize table
		//this.setPreferredSize(new Dimension(widthTable, heightTable));
		
	}
}
