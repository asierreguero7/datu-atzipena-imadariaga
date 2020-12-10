/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmltutoriala;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author reguero.asier
 */
public class XMLTutoriala {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        
        //ZUHAITZA XML FITXATEGITUK KARGATU
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("liburutegia.xml"));
        System.out.println("Elementu erroa = Dokumentu elementua: " + document.getDocumentElement().getTagName());
        System.out.println("Liburu kopurua: " + document.getElementsByTagName("liburu").getLength());
        NodeList liburuNodoak = document.getElementsByTagName("liburu");
        System.out.println("-------------------------------------");
        
        //ZUHAITZEKO ELEMENTU GUZTIAK INPRIMATU
        for (int i = 0; i < liburuNodoak.getLength(); i++) {
            Node nodoa = liburuNodoak.item(i);
            Element elemLiburua = (Element) nodoa;
            System.out.print(elemLiburua.getAttribute("isbn") + " - ");
            NodeList liburuNodoarenSemeak = nodoa.getChildNodes();
            for (int j = 0; j < liburuNodoarenSemeak.getLength(); j++) {
                Node semea = liburuNodoarenSemeak.item(j);
                if (semea.getNodeName() == "izenburua") {
                    System.out.println(((Element) semea.getChildNodes()).getTextContent());
                }
            }
        }
        System.out.println("-------------------------------------");

        //ZUHAITZERA ELEMENTU BAT GEHITU
        Element elemLiburu = document.createElement("liburu");
        elemLiburu.setAttribute("isbn", "014");
        Element elemIzenburu = document.createElement("izenburua");
        Element elemEgile = document.createElement("egile");
        Text textIzenburu = document.createTextNode("Vredaman");
        Text textEgile = document.createTextNode("Unai Elorriaga");
        document.getDocumentElement().appendChild(elemLiburu);
        elemLiburu.appendChild(elemIzenburu);
        elemIzenburu.appendChild(textIzenburu);
        elemLiburu.appendChild(elemEgile);
        elemEgile.appendChild(textEgile);
        System.out.println("Liburu kopurua: " + document.getElementsByTagName("liburu").getLength());

        System.out.println("-------------------------------------");

        //ZUHAITZEKO ELEMENTU GUZTIAK INPRIMATU BERRIRO
        for (int i = 0; i < liburuNodoak.getLength(); i++) {
            Node nodoa = liburuNodoak.item(i);
            Element elemLiburua = (Element) nodoa;
            System.out.print(elemLiburua.getAttribute("isbn") + " - ");
            NodeList liburuNodoarenSemeak = nodoa.getChildNodes();
            for (int j = 0; j < liburuNodoarenSemeak.getLength(); j++) {
                Node semea = liburuNodoarenSemeak.item(j);
                if (semea.getNodeName() == "izenburua") {
                    System.out.println(((Element) semea.getChildNodes()).getTextContent());
                }
            }
        }

        //XML FITXATEGI BATEN GORDE ZUHAITZA
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new java.io.File("liburutegiBerria.xml"));
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);

        /*
        Source xmlDoc = new StreamSource("liburutegia.xml");
        Source xslDoc = new StreamSource("LiburuakHtmlra.xsl");
        StreamResult result2 = new StreamResult(new java.io.File("liburutegia.html"));

        Transformer transformer2 = TransformerFactory.newInstance().newTransformer(xslDoc);
        transformer2.transform(xmlDoc, result2);*/
    }

}
