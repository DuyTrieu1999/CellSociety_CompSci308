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
import java.util.TreeMap;

public class XMLReader {
    /**
     * This class imports XML-related packages and creates a document from an input stream.
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
            if(!xmlDocument.getDocumentElement().getTagName().equals("simulation") && !fileName.equals(defaultFile)) {
                throw new IOException("Wrong type of configuration file.");
            }
        }
        catch (ParserConfigurationException | SAXException e) {
            System.out.println("Configuration file not found. Using default file instead.");
            if (!fileName.equals(defaultFile)) {
                loadDoc(defaultFile, defaultFile);
            }
            e.printStackTrace();
        } catch (IOException f) {
            if (!fileName.equals(defaultFile)) {
                loadDoc(defaultFile, defaultFile);
            }
            f.printStackTrace();
        }
    }
    protected void addCell (ArrayList<String> state, ArrayList<Integer> counts) {
        try {
            NodeList cells = xmlDocument.getElementsByTagName("cell");
            int totalCells = 0;
            for (int i=0; i<cells.getLength(); i++) {
                Node cellNode = cells.item(i);
                if (cellNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element cellEl = (Element) cellNode;
                    String cellState = cellEl.getElementsByTagName("cell_state").item(0).getTextContent();
                    String cellNumber = cellEl.getElementsByTagName("cell_number").item(0).getTextContent();
                    state.add(cellState);
                    counts.add(Integer.parseInt(cellNumber));
                    totalCells += Integer.parseInt(cellNumber);
                }
            }
            int size = determineGridSize(0);
            if((int) Math.pow(size, 2) > totalCells && counts.size() > 0) {
                throw new Exception("Wrong number of cells");
            }
        } catch (Exception e) {
            System.out.println("XML file contains incorrect cell counts.");
            counts.clear();
            state.clear();
        }
    }
    protected void addParameters (TreeMap<String, Double> paramMap) {
        NodeList parameterList = xmlDocument.getElementsByTagName("parameter");
        for(int i = 0; i < parameterList.getLength(); i++) {
            Node xmlNode = parameterList.item(i);
            Element parameter = (Element) xmlNode;
            String paramName = parameter.getElementsByTagName("variable_name").item(0).getTextContent();
            String paramValString = parameter.getElementsByTagName("variable_value").item(0).getTextContent();
            double paramVal = Double.parseDouble(paramValString);
            if(paramName != null && !(paramVal < 0)) {
                paramMap.put(paramName, paramVal);
            }
        }
    }
    protected int determineGridSize (int size) {
        NodeList gridSize = xmlDocument.getElementsByTagName("size");
        for (int i = 0; i < gridSize.getLength(); i++) {
            Node xmlNode = gridSize.item(i);
            Element xmlElement = (Element) xmlNode;
            if (xmlElement.getTagName().equals("size")) {
                String sizeString = xmlElement.getTextContent();
                size = Integer.parseInt(sizeString);
                return size;
            }
        }
        System.out.println("Could not determine grid size from file. Will keep original value.");
        return size;
    }
    protected void loadSave(ArrayList<String> save) {
        try {
            int size = determineGridSize(0);
            NodeList xmlSave = xmlDocument.getElementsByTagName("save");
            for (int i = 0; i < xmlSave.getLength(); i++) {
                Node xmlNode = xmlSave.item(i);
                NodeList cellStatesInSave = xmlNode.getChildNodes();
                for(int j = 0; j < cellStatesInSave.getLength(); j++) {
                    Node cellNode = cellStatesInSave.item(j);
                    if(cellNode instanceof Element) {
                        Element cell = (Element) cellNode;
                        if(cell.getTagName().equals("cell_state")) {
                            String cellState = cell.getTextContent();
                            save.add(cellState);
                        }
                    }
                }
                if((int) Math.pow(size, 2) > save.size()) {
                    throw new Exception("Invalid save state. Cannot load file.");
                }
            }
        } catch (Exception e) {
            save.clear();
            e.printStackTrace();
        }
    }
}
