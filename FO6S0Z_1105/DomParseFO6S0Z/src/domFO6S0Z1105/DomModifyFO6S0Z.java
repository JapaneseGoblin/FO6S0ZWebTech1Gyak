package domFO6S0Z1105;

import java.io.File;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomModifyFO6S0Z {

	public static void main(String[] args) {
		try {
			File inputFile = new File("hallgatokFO6S0Z.xml");
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.parse(inputFile);
			
			Node hallgatok = doc.getFirstChild();
			
			Node hallgat = doc.getElementsByTagName("hallgato").item(0);
			
			NamedNodeMap attr = hallgat.getAttributes();
			Node nodeAttr = attr.getNamedItem("id");
			nodeAttr.setTextContent("01");
			
			NodeList list = hallgat.getChildNodes();
			
			for (int i = 0; i < list.getLength(); i++) {
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
