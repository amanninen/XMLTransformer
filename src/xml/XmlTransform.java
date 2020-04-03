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

		String file ="test.xml";
		// First create a new XMLInputFactory
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		// Setup a new eventReader
		InputStream in = new FileInputStream(file);
		XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
		// Read the XML document

		BufferedWriter out = new BufferedWriter(new FileWriter("file.txt"));

		int count = 0;
		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();
				// If we have a item element we create a new item
				if (startElement.getName().getLocalPart() == ("entry")) {				
					// We read the attributes from this tag and add the date
					// attribute to our object
					Iterator<Attribute> attributes = startElement
							.getAttributes();
					while (attributes.hasNext()) {
						Attribute attribute = attributes.next();
						if (attribute.getName().toString().equals("id")) {
							//item.setDate(attribute.getValue());
							//System.out.println(attribute.getValue());
							out.write("test "+attribute.getValue() + ",");
							count++;
						}
						if (attribute.getName().toString().equals("attr1")) {
							//item.setDate(attribute.getValue());
							//System.out.println(attribute.getValue());
							out.write(attribute.getValue());
						}
					}
				}
			}
			// If we reach the end of an item element we add it to the list
			if (event.isEndElement()) {
				EndElement endElement = event.asEndElement();
				if (endElement.getName().getLocalPart() == ("entry")) {
					//items.add(item);
					out.newLine();
				}
			}
		}

		System.out.println(count);
		out.close();
    }

}