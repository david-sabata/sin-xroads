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
		System.out.println("Row: " + row);
		if(row % 3 == 0 && column % 3 == 0) {
			//System.out.println("Row formated: " + row);
			setBackground(Color.WHITE);
		} else if ((row % 3 == 0 && column % 3 != 0) || (row % 3 != 0 && column % 3 == 0)) {
			// TODO set sth, what will change color while value is changing
			setBackground(Color.GREEN);
		} else if (row % 3 != 0 && column % 3 != 0){
			setBackground(Color.GRAY);
		}
	
		return this;
	}
	
	
	
	
	
}
