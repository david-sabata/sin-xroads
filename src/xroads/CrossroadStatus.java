package xroads;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Prenoska pro informaci o stavu fronty na krizovatce.
 * Stavy jsou pro kazdy smer zvlast, indexovano NORTH, EAST, SOUTH, WEST
 */
public class CrossroadStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1603663560954925848L;


	/**
	 * Jmeno krizovatkoveho agenta
	 */
	public String name;

	/**
	 * Souradnice v mrizce
	 */
	public Position position;

	/**
	 * Stavy pro kazdy smer zvlast
	 */
	public DirectionStatus directions[] = new DirectionStatus[4];





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