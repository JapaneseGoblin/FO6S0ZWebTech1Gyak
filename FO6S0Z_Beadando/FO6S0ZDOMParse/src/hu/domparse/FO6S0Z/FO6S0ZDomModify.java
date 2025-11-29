package hu.domparse.FO6S0Z;

import java.io.File;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
public class FO6S0ZDomModify {

	public static void main(String[] args) {
		try {
            File inputFile = new File("FO6S0ZXML.xml");
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();

            // 1. MÓDOSÍTÁS: "Egri csillagok" Műfajának átírása
            System.out.println("1. Módosítás: K01 könyv műfajának pontosítása...");
            NodeList books = doc.getElementsByTagName("Könyv");
            
            for (int i = 0; i < books.getLength(); i++) {
                Element book = (Element) books.item(i);
                
                if ("K01".equals(book.getAttribute("K_ID"))) {
                    NodeList childNodes = book.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node child = childNodes.item(j);
                        if ("Műfaj".equals(child.getNodeName()) && "Történelmi".equals(child.getTextContent())) {
                            child.setTextContent("Történelmi Regény");
                        }
                    }
                }
            }

            // 2. MÓDOSÍTÁS: P001 Példány állapotának javítása
            System.out.println("2. Módosítás: P001 példány állapotának javítása...");
            NodeList copies = doc.getElementsByTagName("Példány");
            
            for (int i = 0; i < copies.getLength(); i++) {
                Element copy = (Element) copies.item(i);
                
                if ("P001".equals(copy.getAttribute("P_ID"))) {
                    NodeList childNodes = copy.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node child = childNodes.item(j);
                        if ("Állapot".equals(child.getNodeName())) {
                            child.setTextContent("Kiváló (Felújított)");
                        }
                    }
                }
            }

            // 3. MÓDOSÍTÁS: Szerző (SZ02) email címének cseréje
            System.out.println("3. Módosítás: SZ02 szerző email címének cseréje...");
            NodeList authors = doc.getElementsByTagName("Szerző");
            
            for (int i = 0; i < authors.getLength(); i++) {
                Element author = (Element) authors.item(i);
                
                if ("SZ02".equals(author.getAttribute("SZ_ID"))) {
                     NodeList childNodes = author.getChildNodes();
                     for (int j = 0; j < childNodes.getLength(); j++) {
                         Node child = childNodes.item(j);
                         if ("Email".equals(child.getNodeName())) {
                             child.setTextContent("new.jkrowling@official.uk");
                         }
                     }
                }
            }

            // 4. MÓDOSÍTÁS: Kölcsönző (U01) utca nevének javítása (Beágyazott elem)
            System.out.println("4. Módosítás: U01 olvasó elköltözött (Utca módosítása)...");
            NodeList users = doc.getElementsByTagName("Kölcsönző");
            
            for (int i = 0; i < users.getLength(); i++) {
                Element user = (Element) users.item(i);
                
                if ("U01".equals(user.getAttribute("KÖL_ID"))) {
                    Node lakcimNode = user.getElementsByTagName("Lakcím").item(0);
                    NodeList lakcimChilds = lakcimNode.getChildNodes();
                    
                    for (int j = 0; j < lakcimChilds.getLength(); j++) {
                        Node child = lakcimChilds.item(j);
                        if ("Utca".equals(child.getNodeName())) {
                            child.setTextContent("Petőfi Sándor utca");
                        }
                    }
                }
            }

            // Kimenet kiírása konzolra
            System.out.println("\n--- Módosított XML dokumentum tartalma: ---");
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult consoleResult = new StreamResult(System.out);
            
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
