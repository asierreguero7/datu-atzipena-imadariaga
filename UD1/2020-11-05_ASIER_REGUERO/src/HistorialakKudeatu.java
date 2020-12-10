
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author imadariaga
 */
public class HistorialakKudeatu {

    static Document xmlDOM;

    public static void main(String[] args) throws IOException {
        String xmlFileIn = "Historialak.xml";//XML hasierako fitxategia
        String csvFileIn = "HistorialGehiago.csv";//Gehitu beharreko fitxategiak, .csv formatuan
        String xmlFileOut = "HistorialGuztiak.xml";//Aurreko bien batura
        String jsonFileOut = "Historialak.json";//Sarrera objetuen array bat 

        //Metodoak programatzen joan ala, hurrengo lerroak deskomentatuz joan.          
        //BAT: XML fitxategiko datuak memorian, DOM fitxategi baten kargatu
        xmlDOM = xmlFitxategiaMemorianKargatu(xmlFileIn);

        //BI: CSV fitxategi baten dauzkagun datuak DOM zuhaitzera gehitu
        datuakGehitu(csvFileIn);

        //HIRU: Orainarte dauden positiboak zenbatu eta inprimatu
        System.out.println("Orainarte daukagun positibo kopurua: " + positiboKopurua());

        //LAU: XML formatuko fitxategi baten gorde
        if (historialakXMLnGorde(xmlFileOut)) {
            System.out.println(xmlFileOut + " ondo gorde da.");
        }

        //BOST: Ikaslea eskatu eta bere datuak .csv formatuan gorde.        
        Scanner sc = new Scanner(System.in);
        System.out.print("Zein ikasleren historiala nahi duzu ikusi? ");
        String ikaslea = sc.next();
        ikasleBatenHistorialaCsvra(ikaslea);

        //SEI: Datuak Json formatuan esportatu. Zuzenean DOM zuhaitzetik, StreamParser erabilita
        if (historialakJsonera(jsonFileOut)) {
            System.out.println("Datuak ondo esportatu dira " + jsonFileOut + " fitxategira.");
        }

    }

    //BAT: XML fitxategiko datuak memorian, DOM fitxategi baten kargatu
    private static Document xmlFitxategiaMemorianKargatu(String xmlFileIn) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFileIn));
            NodeList sarreraNodoak = document.getElementsByTagName("sarrera");

            /*
            for (int i = 0; i < sarreraNodoak.getLength(); i++) {
                Node nodoa = sarreraNodoak.item(i);
                Element elemLiburua = (Element) nodoa;
                System.out.print(elemLiburua.getAttribute("id") + " - ");
                NodeList sarreraNodoarenSemeak = nodoa.getChildNodes();
                for (int j = 0; j < sarreraNodoarenSemeak.getLength(); j++) {
                    Node semea = sarreraNodoarenSemeak.item(j);
                    if (semea.getNodeName() == "ikaslea") {
                        System.out.println(((Element) semea.getChildNodes()).getTextContent());
                    }
                }
            }*/
            return document;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //BI: CSV fitxategi baten dauzkagun datuak DOM zuhaitzera gehitu
    private static void datuakGehitu(String csvFileIn) {
        BufferedReader br = null;
        String line;

        try {
            br = new BufferedReader(new FileReader("HistorialGehiago.csv"));
            while ((line = br.readLine()) != null) {
                String[] ikaslea = line.split(",");

                Element elemSarrera = xmlDOM.createElement("sarrera");
                elemSarrera.setAttribute("id", ikaslea[0]);
                Element elemEguna = xmlDOM.createElement("eguna");
                Element elemIkaslea = xmlDOM.createElement("ikaslea");
                Element elemTenperatura = xmlDOM.createElement("tenperatura");
                Text textEguna = xmlDOM.createTextNode(ikaslea[1]);
                Text textIkaslea = xmlDOM.createTextNode(ikaslea[2]);
                Text textTenperatura = xmlDOM.createTextNode(ikaslea[3]);
                xmlDOM.getDocumentElement().appendChild(elemSarrera);
                elemSarrera.appendChild(elemEguna);
                elemEguna.appendChild(textEguna);
                elemSarrera.appendChild(elemIkaslea);
                elemIkaslea.appendChild(textIkaslea);
                elemSarrera.appendChild(elemTenperatura);
                elemTenperatura.appendChild(textTenperatura);
                if (ikaslea[4] == "konfinauta") {
                    Element elemPcr = xmlDOM.createElement("konfinauta");
                    Text textPcr = xmlDOM.createTextNode("bai");
                    elemSarrera.appendChild(elemPcr);
                    elemPcr.appendChild(textPcr);
                } else {
                    Element elemKonfinatuta = xmlDOM.createElement("konfinauta");
                    Text textKonfinatuta = xmlDOM.createTextNode("ez");
                    elemSarrera.appendChild(elemKonfinatuta);
                    elemKonfinatuta.appendChild(textKonfinatuta);
                }
                if (ikaslea[5] != null && ikaslea[5] != "") {
                    Element elemPcr = xmlDOM.createElement("pcr");
                    Text textPcr = xmlDOM.createTextNode(ikaslea[5]);
                    elemSarrera.appendChild(elemPcr);
                    elemPcr.appendChild(textPcr);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //HIRU: Orainarte dauden positiboak zenbatu eta inprimatu
    private static String positiboKopurua() {
        int positiboak = 0;

        NodeList sarreraNodoak = xmlDOM.getElementsByTagName("sarrera");

        for (int i = 0; i < sarreraNodoak.getLength(); i++) {
            Node nodoa = sarreraNodoak.item(i);
            Element elemLiburua = (Element) nodoa;
            NodeList sarreraNodoarenSemeak = nodoa.getChildNodes();
            for (int j = 0; j < sarreraNodoarenSemeak.getLength(); j++) {
                Node semea = sarreraNodoarenSemeak.item(j);
                try {
                    if (semea.getNodeName() == "pcr" && ((Element) semea.getChildNodes()).getTextContent() == "+") {
                        positiboak++;
                    }
                } catch (Exception ex) {

                }
            }
        }

        return String.valueOf(positiboak);
    }

    //LAU: XML formatuko fitxategi baten gorde
    private static boolean historialakXMLnGorde(String xmlFileOut) {
        try {
            DOMSource source = new DOMSource(xmlDOM);
            StreamResult result = new StreamResult(new java.io.File("HistorialGuztiak.xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
            return true;
        } catch (Exception ex) {

        }
        return false;
    }

    //BOST: Ikaslea eskatu eta bere datuak .csv formatuan gorde.
    private static void ikasleBatenHistorialaCsvra(String ikaslea) {
        PrintWriter outputStream = null;
        String[] ikasleizena = ikaslea.split(".");
        try {
            outputStream = new PrintWriter(ikaslea + ".csv");
            NodeList sarreraNodoak = xmlDOM.getElementsByTagName("sarrera");

            for (int i = 0; i < sarreraNodoak.getLength(); i++) {
                Node nodoa = sarreraNodoak.item(i);
                Element elemLiburua = (Element) nodoa;
                NodeList sarreraNodoarenSemeak = nodoa.getChildNodes();
                if (sarreraNodoarenSemeak.item(1).getTextContent() == ikaslea) {
                    for (int j = 0; j < 5; j++) {
                        try {
                            Node semea = sarreraNodoarenSemeak.item(j);
                            outputStream.print(((Element) semea.getChildNodes()).getTextContent());
                        } catch (Exception ex) {

                        }
                        if (j != 4) {
                            outputStream.print(",");
                        }
                    }
                    outputStream.println();
                }
            }
        } catch (Exception ex) {

        }finally{
            outputStream.close();
        }
    }

    //SEI: Datuak Json formatuan esportatu. Zuzenean DOM zuhaitzetik, StreamParser erabilita
    private static boolean historialakJsonera(String jsonFileOut) throws IOException {
        String eguna="";
        String ikaslea="";
        String zenbakia="";
        String tenperatura ="";
        String konfinatuta ="";
        String pcr = "";
        String linea;
        JsonArrayBuilder jab = Json.createArrayBuilder();//1.

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("ikasleak.xml"));
            FileWriter Writer = new FileWriter("ikasleak.csv");
            NodeList ikasleNodoak = document.getElementsByTagName("sarrera");
            for (int i = 0; i < ikasleNodoak.getLength(); i++) {
                Node nodoa = ikasleNodoak.item(i);
                Element elemIkaslea = (Element) nodoa;
                zenbakia = elemIkaslea.getAttribute("id");
                eguna = elemIkaslea.getElementsByTagName("eguna").item(0).getTextContent();
                ikaslea = elemIkaslea.getElementsByTagName("ikaslea").item(0).getTextContent();
                tenperatura = elemIkaslea.getElementsByTagName("tenperatura").item(0).getTextContent();
                konfinatuta = elemIkaslea.getElementsByTagName("konfinatuta").item(0).getTextContent();
                pcr = elemIkaslea.getElementsByTagName("pcr").item(0).getTextContent();
                 JsonObject model = Json.createObjectBuilder()
                    .add("id", zenbakia)
                    .add("eguna", eguna)
                    .add("ikaslea", ikaslea)
                    .add("konfinatuta", konfinatuta)
                    .add("pcr", pcr)
                    .build();
              jab.add(model);
                   }      
                 
        } catch (Exception e) {

        }finally{
            JsonArray model2 = jab.build();
            FileWriter fw = new FileWriter("historialak.json");
            JsonWriter jsonwriter = Json.createWriter(fw);
                jsonwriter.writeArray(model2);
                jsonwriter.close();
                fw.close();
        }
        return false;
    }

}
