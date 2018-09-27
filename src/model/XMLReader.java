package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLReader {
    /**
     * This class imports XML-related packages, creates a SAXBuilder, document from files stream.
     * Source code copied from https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
     * @author Austin Kao, Duy Trieu
     */

    private Document xmlDocument;
    protected void loadDoc (String fileName, String defaultFile) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(this.getClass().getClassLoader().getResourceAsStream(fileName));
            doc.getDocumentElement().normalize();
            xmlDocument = doc;
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Configuration file not found. Using default file instead.");
            if (!fileName.equals(defaultFile)) {
                loadDoc(defaultFile, defaultFile);
            }
            e.printStackTrace();
        }
    }
    protected void addCell (ArrayList<String> state, ArrayList<Integer> counts) {
        NodeList cells = xmlDocument.getElementsByTagName("cell");
        for (int i=0; i<cells.getLength(); i++) {
            Node cellNode = cells.item(i);
            if (cellNode.getNodeType() == Node.ELEMENT_NODE) {
                Element cellEl = (Element) cellNode;
                String cellState = cellEl.getElementsByTagName("cell_state").item(0).getTextContent();
                String cellNumber = cellEl.getElementsByTagName("cell_number").item(0).getTextContent();
                //System.out.println(cellState);
                //System.out.println(cellNumber);
                state.add(cellState);
                counts.add(Integer.parseInt(cellNumber));
            }
        }
    }
    protected void addParameters (ArrayList<String> var, ArrayList<Double> val) {
        NodeList parameterList = xmlDocument.getElementsByTagName("variable");
        for(int i = 0; i < parameterList.getLength(); i++) {
            Node xmlNode = parameterList.item(i);
            Element variable = (Element) xmlNode;
            String varName = variable.getAttribute("name");
            String varVal = variable.getAttribute("value");
            System.out.println(varVal);
            if(varName != null) {
                var.add(varName);
            }
            if(varVal != null) {
                val.add(Double.parseDouble(varVal));
            }
        }
    }
    protected int determineGridSize (int size) {
        NodeList gridSize = xmlDocument.getElementsByTagName("size");
        for (int i = 0; i < gridSize.getLength(); i++) {
            Node xmlNode = gridSize.item(i);
            Element xmlElement = (Element) xmlNode;
            if (xmlElement.getTagName().equals("size")) {
                System.out.println("Current node is size");
                String sizeString = xmlElement.getTextContent();
                size = Integer.parseInt(sizeString);
                return size;
            }
        }
        System.out.println("Could not determine grid size from file. Will keep original value.");
        return size;
    }
}
