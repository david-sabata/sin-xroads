package xroads;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Informace o stavu auta, ktere si lze vyzadat zpravou REQUEST
 * (v pripade ze auto zrovna nespi ve zpozdovacim cyklu)
 */
public class CarStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3597511256900703490L;

	/**
	 * Nazev krizovatky na ktere auto aktualne je
	 */
	public String currentCrossroad;

	/**
	 * Nazev krizovatky do ktere auto jede
	 */
	public String destinationCrossroad;




	/**
	 * Deserializace
	 */
	public static CarStatus deserialize(String s) throws IOException, ClassNotFoundException {
		byte[] data = Base64Coder.decode(s);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		Object o = ois.readObject();
		ois.close();
		return (CarStatus) o;
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
