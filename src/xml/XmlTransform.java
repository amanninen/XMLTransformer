package xml;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XmlTransform {

	public static void RunTransformation() throws XMLStreamException, IOException {

		String file ="export.xml";
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		InputStream in = new FileInputStream(file);
		XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

		BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));

		int count = 0;
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
				if (startElement.getName().getLocalPart() == ("Record")) {				
					Iterator<Attribute> attributes = startElement
							.getAttributes();
					while (attributes.hasNext()) {
						Attribute attribute = attributes.next();
						out.write(attribute.getValue() + ",");
					}
				}
			}
			if (event.isEndElement()) {
				EndElement endElement = event.asEndElement();
				if (endElement.getName().getLocalPart() == ("Record")) {
					count++;
					out.newLine();
				}
			}
		}

		System.out.println(count);
		out.close();
    }

}