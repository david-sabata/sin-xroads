package xroads;

import java.util.regex.Pattern;


/**
 * Staticka trida (fuj) drzici informace o aktualnim svete
 * (mrizce), poskytujici moznost vzajemneho prevodu souradnic 
 * a jmena agenta.
 */
public class World {

	private static int gridWidth;
	private static int gridHeight;



	public static void setGridSize(int width, int height) {
		gridWidth = width;
		gridHeight = height;
	}


	public static int getGridWidth() {
		return gridWidth;
	}

	public static int getGridHeight() {
		return gridHeight;
	}


	/**
	 * Vraci pozici krizovatky v mrizce, pripadne 
	 * null pro neznameho agenta
	 */
	public static Position getAgentCoords(String name) {
		String[] parts = name.split(Pattern.quote("-"));
		if (parts.length != 2)
			return null;

		int i = Integer.parseInt(parts[1]);

		Position pos = new Position();
		pos.x = i % gridWidth;
		pos.y = i / gridWidth;

		return pos;
	}

	/**
	 * Vraci agenta na danych souradnicich. Pozor - pocita 
	 * i s okrajem sirky 1 kolem mrizky krizovatek, kde jsou 
	 * koncovky. Tedy [-1,0] je platna souradnice koncovky 
	 * pripojene na zapadni strane krizovatky na [0,0]
	 */
	public static String getAgentAt(int x, int y) {

		// krizovatka
		if (x >= 0 && x < gridWidth && y >= 0 && y < gridHeight) {
			return "xroad-" + (x + y * gridWidth);
		}

		// koncovka N
		if (y == -1 && x >= 0 && x < gridWidth) {
			return "endpoint-n-" + x;
		}

		// koncovka S
		if (y == gridHeight && x >= 0 && x < gridWidth) {
			return "endpoint-s-" + x;
		}

		// koncovka E
		if (x == gridWidth && y >= 0 && y < gridHeight) {
			return "endpoint-e-" + y;
		}

		// koncovka W
		if (x == -1 && y >= 0 && y < gridHeight) {
			return "endpoint-w-" + y;
		}

		return null;
	}


	/**
	 * Zjistuje zda zadane jmeno agenta odpovida nektere koncovce
	 */
	public boolean isAgentEndpoint(String name) {
		String parts[] = name.split(Pattern.quote("-"));

		return (parts.length == 3 && parts[0].equals("endpoint"));
	}

}
