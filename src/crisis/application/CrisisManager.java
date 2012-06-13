package crisis.application;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import crisis.application.exceptions.LoadException;
import crisis.application.exceptions.SaveException;

public class CrisisManager {

	private static Crisis crisis;

	static {
		try {
			load();
		} catch (LoadException e) {
			e.printStackTrace();
			crisis = new Crisis("asd");
		}
	}

	public static Crisis getCrisis() {

		return crisis;
	}

	public static void save() throws SaveException {

		File file = new File("D:/crisis.xml");

		try {
			JAXBContext ctx = JAXBContext.newInstance(Crisis.class);
			Marshaller marshaller = ctx.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(crisis, file);
		} catch (JAXBException e) {
			throw new SaveException("Could not save Crisis to File", e);
		}
	}

	public static void load() throws LoadException {

		File file = new File("D:/crisis.xml");

		try {
			JAXBContext ctx = JAXBContext.newInstance(Crisis.class);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			crisis = (Crisis) unmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			throw new LoadException("Could not load Crisis from File", e);
		}
	}
}
