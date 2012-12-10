package xroads.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import xroads.Constants;
import xroads.CrossroadStatus;

@SuppressWarnings("serial")
public class CityGenerator extends JTable {
	private static final int endpointSize = 24;
	private static final int crossSize = 18;
	private static final int roadSize = 75;
	
	private int width;
	private int height;
	private int gridWidth; //original 
	@SuppressWarnings("unused")
	// It will be maybe important in the future
	private int gridHeight; //original

	private DefaultTableModel tableModel;
	private CellRenderer renderer;

	/**
	 * Delete everything from table and generate new table with new params (width, height)
	 * @param width
	 * @param height
	 */
	public void generateCity(int width, int height) {
		if (width > 0 && height > 0) {
			this.gridWidth = width;
			this.gridHeight = height;

			this.width = calculateWithRoads(width);
			this.height = calculateWithRoads(height);

			tableModel = new DefaultTableModel(this.height, this.width);
			this.setModel(tableModel);

			// Add cross roads, roads, in out to grid
			renderer = new CellRenderer(tableModel);
			this.setDefaultRenderer(Object.class, renderer);

			// Resize table layout to size of city
			resizeTable();
		}
	}

	/**
	 * Return number of columns (or row) with our graphics
	 * @param n
	 * @return
	 */
	private int calculateWithRoads(int n) {
		return (n * 2) + n + 3;
	}


	/**
	 * It should resible table to constant values but DOESNT WORK
	 */
	private void resizeTable() {
		int rows = this.getRowCount();
		int cols = this.getColumnCount();
		int sizeHeight;
		
		this.setRowHeight(0, endpointSize);
		this.setRowHeight(rows - 1, endpointSize);
		sizeHeight = 2*endpointSize;
		for (int i = 1; i < rows - 1; i++) {
			if (i % 3 == 1) {
				this.setRowHeight(i, roadSize);
				sizeHeight += roadSize;
			} else {
				this.setRowHeight(i, crossSize);
				sizeHeight += crossSize;
			}
		}
		
		this.getColumnModel().getColumn(0).setMinWidth(1);
		this.getColumnModel().getColumn(0).setMaxWidth(endpointSize);
		this.getColumnModel().getColumn(0).setPreferredWidth(endpointSize);
		this.getColumnModel().getColumn(cols - 1).setMinWidth(1);
		this.getColumnModel().getColumn(cols - 1).setMaxWidth(endpointSize);
		this.getColumnModel().getColumn(cols - 1).setPreferredWidth(endpointSize);
		for (int i = 1; i < cols - 1; i++) {
			this.getColumnModel().getColumn(i).setMinWidth(1);
			if (i % 3 == 1) {	
				this.getColumnModel().getColumn(i).setPreferredWidth(roadSize);
				this.getColumnModel().getColumn(i).setMaxWidth(roadSize);
			} else {
				this.getColumnModel().getColumn(i).setPreferredWidth(crossSize);
				this.getColumnModel().getColumn(i).setMaxWidth(crossSize);
			}
		}
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setBounds(5, 109, this.getColumnModel().getTotalColumnWidth(), sizeHeight);
		this.setPreferredSize(new Dimension(this.getColumnModel().getTotalColumnWidth(), sizeHeight));
	}

	/**
	 * Update value at all fronts
	 * @param x
	 * @param y
	 */
	public void updateCrossRoadAt(CrossroadStatus s) {
		String name = s.name;

		int x = s.position.x;
		int y = s.position.y;

		x *= 3;
		y *= 3;

		for (int dir : Constants.DIRECTIONS) {
			CoordRoad coordRoad = null;
			CoordRoad coordSem = null;
			switch (dir) {
				case Constants.WEST:
					coordRoad = coordCrossRoadWest(x, y);
					coordSem = coordCrossSemWest(x, y);
					break;
				case Constants.EAST:
					coordRoad = coordCrossRoadEast(x, y);
					coordSem = coordCrossSemEast(x, y);
					break;
				case Constants.NORTH:
					coordRoad = coordCrossRoadNorth(x, y);
					coordSem = coordCrossSemNorth(x, y);
					break;
				case Constants.SOUTH:
					coordRoad = coordCrossRoadSouth(x, y);
					coordSem = coordCrossSemSouth(x, y);

					break;
				default:
					break;

			}

			int actualLenght = s.directions[dir].carQueue.size();
			int maxLength = s.directions[dir].maximumLength;

			String valueRoadString = actualLenght + "/" + maxLength;
			String valueSemString = Integer.toString(s.directions[dir].semaphore);

			if (coordRoad != null) {
				tableModel.setValueAt(valueRoadString, coordRoad.getY(), coordRoad.getX());
			}
			if (coordSem != null) {
				tableModel.setValueAt(valueSemString, coordSem.getY(), coordSem.getX());
			}
		}
	}

	/****************************************************/
	/*************** COORDINATES IN CITY ****************/
	/****************************************************/
	private CoordRoad coordCrossSemSouth(int x, int y) {
		return coordCrossRoadSouth(x, y - 1);
	}

	private CoordRoad coordCrossSemNorth(int x, int y) {
		return coordCrossRoadNorth(x, y + 1);
	}

	private CoordRoad coordCrossSemEast(int x, int y) {
		return coordCrossRoadEast(x - 1, y);
	}

	private CoordRoad coordCrossSemWest(int x, int y) {
		return coordCrossRoadWest(x + 1, y);
	}

	/**
	 *  Transfer coordinate for West road in crossroad
	 * @return
	 */
	private CoordRoad coordCrossRoadWest(int x, int y) {

		return new CoordRoad(x + 1, y + 3);
	}

	/**
	 *  Transfer coordinate for West road in crossroad
	 * @return
	 */
	private CoordRoad coordCrossRoadEast(int x, int y) {
		return new CoordRoad(x + 4, y + 2);
	}

	/**
	 *  Transfer coordinate for West road in crossroad
	 * @return
	 */
	private CoordRoad coordCrossRoadNorth(int x, int y) {
		return new CoordRoad(x + 2, y + 1);
	}

	/**
	 *  Transfer coordinate for West road in crossroad
	 * @return
	 */
	private CoordRoad coordCrossRoadSouth(int x, int y) {
		return new CoordRoad(x + 3, y + 4);
	}



	/**
	 * To store X, Y coord of roads of crossRoad
	 * @author xsych_000
	 *
	 */
	public class CoordRoad {
		private int x;
		private int y;

		public CoordRoad(int x, int y) {
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
