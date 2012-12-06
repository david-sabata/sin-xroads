package xroads.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class CellRenderer extends DefaultTableCellRenderer {
	private DefaultTableModel model;
	private float max = 30f; 
	
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
		
		if(row % 3 == 0 && column % 3 == 0) {
			//System.out.println("Row formated: " + row);
			setBackground(Color.WHITE);
		} else if ((row % 3 == 0 && column % 3 != 0) || (row % 3 != 0 && column % 3 == 0)) {
			// TODO set sth, what will change color while value is changing
			if(value == null) {
				setBackground(Color.GREEN);
			} else {
				setBackground(interpolateColorFromValue(Integer.parseInt((String)value)));
			}
			
		} else if (row % 3 != 0 && column % 3 != 0){
			setBackground(Color.GRAY);
		}
	
		return this;
	}

	/**
	 * Interpolate color from value 0 - MAX
	 * @param parseInt
	 * @return
	 */
	private Color interpolateColorFromValue(int cars) {
		float carsf = cars / max;
		
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
