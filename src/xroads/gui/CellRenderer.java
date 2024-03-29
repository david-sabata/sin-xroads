package xroads.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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
	public CellRenderer(DefaultTableModel model) {
		this.model = model;

	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

		// Borders
		if (row == 0 || column == 0 || row == model.getRowCount() - 1 || column == model.getColumnCount() - 1) {
			if ((row == 0 && column == 0) || (row == 0 && column == model.getRowCount() - 1) || (row == model.getRowCount() - 1 && column == 0)
					|| (row == model.getRowCount() - 1 && column == model.getColumnCount() - 1)) {
				setBackground(Color.WHITE);
				setIcon(null);
			} else {
				if (row > 0)
					row -= 1;
				if (column > 0)
					column -= 1;

				if ((row == 0 || column == 0) && !(row == model.getRowCount() - 2 || column == model.getColumnCount() - 2)) {
					setIcon(null);
					if ((row % 3 == 0 && column % 3 != 0) || (row % 3 != 0 && column % 3 == 0)) {
						setBackground(Color.BLUE);
					} else {
						setBackground(Color.WHITE);

					}
				} else {
					setIcon(null);
					if ((row % 3 == 0 && column % 3 != 0) || (row % 3 != 0 && column % 3 == 0)) {
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
			if (row % 3 == 0 && column % 3 == 0) {
				setIcon(null);
				setBackground(Color.WHITE);
			} else if ((row % 3 == 0 && column % 3 != 0) || (row % 3 != 0 && column % 3 == 0)) {
				setIcon(null);
				if (value == null) {
					setBackground(Color.GREEN);
				} else {
					String[] temp;
					String act;
					String max;
					temp = ((String) value).split("/");
					try { // pri zobrazeni obsazenosti silnice vedouci do koncovky hazi pristup mimo pole
						act = temp[0];
						max = temp[1];
						setBackground(interpolateColorFromValue(Integer.parseInt(act), Integer.parseInt(max)));
					} catch (java.lang.ArrayIndexOutOfBoundsException x) {
						setBackground(Color.GREEN);
					}
				}
				// vykreslen isemaforu do bunek prezentujicich silnice
				if (column % 3 == 1 && row % 3 == 0 && row < table.getRowCount() - 3) { // prijezd ze severu
					String v = (String)table.getValueAt(row + 2, column + 1);
					if (v != null) {
						int sem = Integer.parseInt(v);
						drawSemaphoreToCell(sem);
						setVerticalAlignment(SwingConstants.BOTTOM);
					}
				} else if (column % 3 == 2 && row % 3 == 0 && row > 1) { // prijezd z jihu
					String v = (String)table.getValueAt(row, column + 1);
					if (v != null) {
						int sem = Integer.parseInt(v);
						drawSemaphoreToCell(sem);
						setVerticalAlignment(SwingConstants.TOP);
					}
				} else if (column > 1 && (column % 3 == 0) && (row % 3 == 1)) { // prijezd z vychodu
					String v = (String)table.getValueAt(row + 1, column);
					if (v != null) {
						int sem = Integer.parseInt(v);
						drawSemaphoreToCell(sem);
						setHorizontalAlignment(SwingConstants.LEFT);
					}
				} else if (column % 3 == 0 && row % 3 == 2 && column < table.getColumnCount() - 3) { // prijezd ze zapadu
					
					String v = (String)table.getValueAt(row + 1, column + 2);
					if (v != null) {
						int sem = Integer.parseInt(v);
						drawSemaphoreToCell(sem);
						setHorizontalAlignment(SwingConstants.RIGHT);
					}
				}
			} else if (row % 3 != 0 && column % 3 != 0) {
				setIcon(null); // jak kurva fungují podmínky v Javě?!!?
				setBackground(Color.GRAY);
			}
		}

		return this;
	}
	
	private void drawSemaphoreToCell(int sem) {
		switch (sem) {
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

	/**
	 * Interpolate color from value 0 - MAX
	 * @param parseInt
	 * @return
	 */
	private Color interpolateColorFromValue(int actCars, int maxCars) {
		float carsf = actCars / (float) maxCars;

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
		float red = origRedRChannel * carsf + origGreenRChannel * (1f - carsf);
		float green = origRedGChannel * carsf + origGreenGChannel * (1f - carsf);
		float blue = origRedBChannel * carsf + origGreenBChannel * (1f - carsf);
		float alpha = origRedAChannel * carsf + origGreenAChannel * (1f - carsf);


		return new Color(red, green, blue, alpha);
	}





}
