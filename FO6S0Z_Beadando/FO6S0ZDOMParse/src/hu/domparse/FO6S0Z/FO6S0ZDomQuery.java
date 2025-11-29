package hu.domparse.FO6S0Z;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FO6S0ZDomQuery {

	public static void main(String[] args) {
		try {
            File xmlFile = new File("FO6S0ZXML.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            
            doc.getDocumentElement().normalize();

            System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());
            System.out.println("--------------------------------------------------");

            // 1. LEKÉRDEZÉS: Könyvek címeinek listázása
            System.out.println("\n1. LEKÉRDEZÉS: Könyvcímek listája");
            NodeList bookList = doc.getElementsByTagName("Könyv");
            
            for (int i = 0; i < bookList.getLength(); i++) {
                Node node = bookList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    String cim = elem.getElementsByTagName("Cím").item(0).getTextContent();
                    System.out.println("-> " + cim);
                }
            }

            // 2. LEKÉRDEZÉS: Szerzők születési évének megjelenítése
            System.out.println("\n2. LEKÉRDEZÉS: Szerzők és születési évük");
            NodeList authorList = doc.getElementsByTagName("Szerző");
            
            for (int i = 0; i < authorList.getLength(); i++) {
                Node node = authorList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    String nev = elem.getElementsByTagName("Név").item(0).getTextContent();
                    String ev = elem.getElementsByTagName("SzületésiÉv").item(0).getTextContent();
                    System.out.println(nev + " (Született: " + ev + ")");
                }
            }

            // 3. LEKÉRDEZÉS: Kölcsönzők lakcíme (Összetett elem kezelése)
            System.out.println("\n3. LEKÉRDEZÉS: Kölcsönzők teljes lakcíme");
            NodeList userList = doc.getElementsByTagName("Kölcsönző");
            
            for (int i = 0; i < userList.getLength(); i++) {
                Node node = userList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    String nev = elem.getElementsByTagName("Név").item(0).getTextContent();
                    
                    Element lakcimElem = (Element) elem.getElementsByTagName("Lakcím").item(0);
                    String varos = lakcimElem.getElementsByTagName("Város").item(0).getTextContent();
                    String utca = lakcimElem.getElementsByTagName("Utca").item(0).getTextContent();
                    String hsz = lakcimElem.getElementsByTagName("Házszám").item(0).getTextContent();
                    
                    System.out.printf("Olvasó: %-15s | Cím: %s, %s %s.\n", nev, varos, utca, hsz);
                }
            }

            // 4. LEKÉRDEZÉS: Aktív kölcsönzések ID-k alapján
            System.out.println("\n4. LEKÉRDEZÉS: Kölcsönzési tranzakciók adatai");
            NodeList loanList = doc.getElementsByTagName("Kölcsönzés");
            
            for (int i = 0; i < loanList.getLength(); i++) {
                Node node = loanList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    
                    String tranzakcioId = elem.getAttribute("KO_ID");
                    
                    String pId = elem.getElementsByTagName("P_ID").item(0).getTextContent();
                    String kolId = elem.getElementsByTagName("KÖL_ID").item(0).getTextContent();
                    String datum = elem.getElementsByTagName("Kezdete").item(0).getTextContent();
                    
                    System.out.println("Tranzakció [" + tranzakcioId + "]: " + kolId + " kikölcsönözte -> " + pId + " (" + datum + ")");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
