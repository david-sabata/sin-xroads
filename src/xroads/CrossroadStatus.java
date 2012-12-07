package xroads;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import xroads.World.Position;

/**
 * Prenoska pro informaci o stavu fronty na krizovatce.
 * Pole jsou indexovana konstantami NORTH, EAST, SOUTH, WEST
 */
public class CrossroadStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1685604929400573005L;

	/**
	 * Jmeno krizovatkoveho agenta
	 */
	public String name;

	/**
	 * Souradnice v mrizce
	 */
	public Position position;

	/**
	 * Aktualni delky front
	 */
	public int actualLength[] = new int[4];

	/**
	 * Maximalni delky front
	 */
	public int maximumLength[] = new int[4];

	/**
	 * Stavy semaforu (konstanty RED, ORANGE, GREEN)
	 */
	public int semaphore[] = new int[4];




	/**
	 * Deserializace
	 */
	public static CrossroadStatus deserialize(String s) throws IOException, ClassNotFoundException {
		byte[] data = Base64Coder.decode(s);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		Object o = ois.readObject();
		ois.close();
		return (CrossroadStatus) o;
	}

	/**
	 * Serializace
	 */
	public String serialize() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this);
		oos.close();
		return new String(Base64Coder.encode(baos.toByteArray()));
	}
}