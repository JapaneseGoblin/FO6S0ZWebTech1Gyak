package hu.domparse.FO6S0Z;

import java.io.*;
import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;


public class FO6S0ZDomRead {

	public static void main(String[] args) throws SAXException,
    IOException, ParserConfigurationException{
		File xmlFile = new File("FO6S0ZXML.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        System.out.println("Gyökér elem: " + doc.getDocumentElement().getNodeName());
        System.out.println("-------------------------");


        System.out.println("\n--- SZERZŐK ---");
        NodeList nList = doc.getElementsByTagName("Szerző");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String id = elem.getAttribute("SZ_ID");
                String nev = elem.getElementsByTagName("Név").item(0).getTextContent();
                String nemzetiseg = elem.getElementsByTagName("Nemzetiség").item(0).getTextContent();
                String szulEv = elem.getElementsByTagName("SzületésiÉv").item(0).getTextContent();
                
                System.out.println("Szerző ID: " + id);
                System.out.println("Név: " + nev);
                System.out.println("Nemzetiség: " + nemzetiseg);
                System.out.println("Születési Év: " + szulEv);
                System.out.println("-");
            }
        }

        // 2. KÖNYVEK BEOLVASÁSA
        System.out.println("\n--- KÖNYVEK ---");
        nList = doc.getElementsByTagName("Könyv");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String id = elem.getAttribute("K_ID");
                String cim = elem.getElementsByTagName("Cím").item(0).getTextContent();
                String leiras = elem.getElementsByTagName("Leírás").item(0).getTextContent();

                System.out.println("Könyv ID: " + id);
                System.out.println("Cím: " + cim);
                System.out.println("Leírás: " + leiras);

                // MŰFAJ KEZELÉSE 
                NodeList mufajList = elem.getElementsByTagName("Műfaj");
                for (int j = 0; j < mufajList.getLength(); j++) {
                    System.out.println("Műfaj: " + mufajList.item(j).getTextContent());
                }
                System.out.println("-");
            }
        }

        // 3. PÉLDÁNYOK BEOLVASÁSA
        System.out.println("\n--- PÉLDÁNYOK ---");
        nList = doc.getElementsByTagName("Példány");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;
                
                String pid = elem.getAttribute("P_ID");
                String kid = elem.getElementsByTagName("K_ID").item(0).getTextContent();
                String ev = elem.getElementsByTagName("KiadásiÉv").item(0).getTextContent();
                String allapot = elem.getElementsByTagName("Állapot").item(0).getTextContent();

                System.out.println("Példány ID: " + pid);
                System.out.println("Tartozik a következő könyvhöz (K_ID): " + kid);
                System.out.println("Kiadás éve: " + ev);
                System.out.println("Állapot: " + allapot);
                System.out.println("-");
            }
        }

        // 4. KÖLCSÖNZŐK (OLVASÓK) BEOLVASÁSA
        System.out.println("\n--- KÖLCSÖNZŐK ---");
        nList = doc.getElementsByTagName("Kölcsönző");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String kolId = elem.getAttribute("KÖL_ID");
                String nev = elem.getElementsByTagName("Név").item(0).getTextContent();
                String email = elem.getElementsByTagName("Email").item(0).getTextContent();
                
                // LAKCÍM (Komplex elem) KEZELÉSE
                // Először lekérjük a Lakcím node-ot, majd abból az al-elemeket
                Node lakcimNode = elem.getElementsByTagName("Lakcím").item(0);
                String varos = "", utca = "", hazszam = "";
                
                if (lakcimNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element lakcimElem = (Element) lakcimNode;
                    varos = lakcimElem.getElementsByTagName("Város").item(0).getTextContent();
                    utca = lakcimElem.getElementsByTagName("Utca").item(0).getTextContent();
                    hazszam = lakcimElem.getElementsByTagName("Házszám").item(0).getTextContent();
                }

                System.out.println("Olvasó ID: " + kolId);
                System.out.println("Név: " + nev);
                System.out.println("Email: " + email);
                System.out.println("Lakcím: " + varos + ", " + utca + " " + hazszam + ".");
                System.out.println("-");
            }
        }

        // 5. KÖLCSÖNZÉSEK (TRANZAKCIÓK) BEOLVASÁSA
        System.out.println("\n--- KÖLCSÖNZÉSEK ---");
        nList = doc.getElementsByTagName("Kölcsönzés");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;

                String koId = elem.getAttribute("KO_ID");
                String kolId = elem.getElementsByTagName("KÖL_ID").item(0).getTextContent();
                String pId = elem.getElementsByTagName("P_ID").item(0).getTextContent();
                String kezdet = elem.getElementsByTagName("Kezdete").item(0).getTextContent();

                System.out.println("Tranzakció ID: " + koId);
                System.out.println("Olvasó (KÖL_ID): " + kolId);
                System.out.println("Példány (P_ID): " + pId);
                System.out.println("Dátum: " + kezdet);
                System.out.println("-");
            }
        }
		
	}

}
