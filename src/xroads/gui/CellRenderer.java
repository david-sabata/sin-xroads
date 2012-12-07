package xroads.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import xroads.Constants;

@SuppressWarnings("serial")
public class CellRenderer extends DefaultTableCellRenderer {
	private DefaultTableModel model;

	/**
	 * Associate renderer with table model
	 * @param model
	 */
	public  CellRenderer(DefaultTableModel model) {
			this.model = model;
			
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
			 
		//System.out.println("Cell: " + Integer.toString(row) + " : " + Integer.toString(column));
		//System.out.println("Row: " + row);
		
		// Borders
		if (row == 0 ||  column == 0 || row == model.getRowCount() - 1 || column == model.getColumnCount() - 1) {
			if((row == 0 && column == 0) || 
					(row == 0 &&  column == model.getRowCount() - 1)  ||
					(row == model.getRowCount() - 1 && column == 0) ||
					(row == model.getRowCount() - 1 && column == model.getColumnCount() - 1)) {
				setBackground(Color.WHITE);
				setIcon(null);
			} else {
				if(row > 0)
					row -= 1;
				if(column > 0) 
					column -= 1;
				
				if((row == 0 || column == 0) && !(row == model.getRowCount() -2 || column == model.getColumnCount() - 2)) {
					setIcon(null);
					if ((row % 3 == 0 && column % 3 != 0) || (row % 3 != 0 && column % 3 == 0)) {
						setBackground(Color.BLUE);
					} else {
						setBackground(Color.WHITE);
						
					}
				} else  {
					setIcon(null);
					if((row % 3 == 0 && column % 3 != 0) || (row % 3 != 0 && column % 3 == 0)) {
						//System.out.println("Row formated: " + row);
						setBackground(Color.WHITE);
					} else {
						setBackground(Color.BLUE);
					}
				}
			}
			
		} 
		
		
		 // Others
		else {
			row -= 1;
			column -= 1;
			if(row % 3 == 0 && column % 3 == 0) {
				//System.out.println("Row formated: " + row);
				setIcon(null);
				setBackground(Color.WHITE);
			} else if ((row % 3 == 0 && column % 3 != 0) || (row % 3 != 0 && column % 3 == 0)) {
				// TODO set sth, what will change color while value is changing
				setIcon(null);
				if(value == null) {
					setBackground(Color.GREEN);
				} else {
					String [] temp;
					String act;
					String max;
					temp = ((String) value).split("/");
					act = temp[0];
					max = temp [1];
					
					setBackground(interpolateColorFromValue(Integer.parseInt(act), Integer.parseInt(max)));
				}
				
			} else if (row % 3 != 0 && column % 3 != 0){
				if(value != null) {
					int sem = Integer.parseInt((String) value);		
					switch(sem) {
						case Constants.GREEN: 
							setIcon(new ImageIcon("images/crossroad_green.gif"));
							break;
						case Constants.RED: 
							setIcon(new ImageIcon("images/crossroad_red.gif"));
							break;
						case Constants.ORANGE: 
							setIcon(new ImageIcon("images/crossroad_orange.gif"));
							break;
						default:
							setBackground(Color.GRAY);	
							break;
							
					} 
				}
				
				setBackground(Color.GRAY);
			}
		}
	
		return this;
	}

	/**
	 * Interpolate color from value 0 - MAX
	 * @param parseInt
	 * @return
	 */
	private Color interpolateColorFromValue(int actCars, int maxCars) {
		float carsf = actCars / (float)maxCars;
		
		// Green color channel
		float origGreenRChannel = Color.GREEN.getRed() / 255f;
		float origGreenGChannel = Color.GREEN.getGreen() / 255f;
		float origGreenBChannel = Color.GREEN.getBlue() / 255f;
		float origGreenAChannel = Color.GREEN.getAlpha() / 255f;
		
		// Red color channel
		float origRedRChannel = Color.RED.getRed() / 255f;
		float origRedGChannel = Color.RED.getGreen() / 255f;
		float origRedBChannel = Color.RED.getBlue() / 255f;
		float origRedAChannel = Color.RED.getAlpha() / 255f;
		
		// Interpolate color
		float red =  origRedRChannel * carsf + origGreenRChannel * (1f - carsf);
		float green =  origRedGChannel * carsf + origGreenGChannel * (1f - carsf);
		float blue =  origRedBChannel * carsf + origGreenBChannel * (1f - carsf);
		float alpha =  origRedAChannel * carsf + origGreenAChannel * (1f - carsf);
		

		return new Color(red, green, blue, alpha);	
	}
	
	
	
	
	
}
