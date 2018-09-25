package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLReader {
    /**
     * This class imports XML-related packages, creates a SAXBuilder, document from files stream.
     * Source code copied from https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
     * @author Austin Kao, Duy Trieu
     */

    private Document configDoc;
    protected void loadDoc (String fileName, String defaultFile) {
        File layout = new File(fileName);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(layout);
            doc.getDocumentElement().normalize();
            configDoc = doc;
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            if (!fileName.equals(defaultFile)) {
                loadDoc(defaultFile, defaultFile);
                System.out.println("file not found, used default file instead.");
            }
            e.printStackTrace();
        }
    }
    protected void addCell (ArrayList<String> state, ArrayList<Integer> percent) {
        NodeList cells = configDoc.getElementsByTagName("cell");
        for (int i=0; i<cells.getLength(); i++) {
            Node cellNode = cells.item(i);
            if (cellNode.getNodeType() == Node.ELEMENT_NODE) {
                Element cellEl = (Element) cellNode;
                String cellState = cellEl.getElementsByTagName("cell_state").item(0).getTextContent();
                String cellPercent = cellEl.getElementsByTagName("cell_percent").item(0).getTextContent();
                state.add(cellState);
                percent.add(Integer.parseInt(cellPercent));
            }
        }
    }
    protected void addVariable (ArrayList<String> var, ArrayList<Double> val) {
        NodeList variables = configDoc.getElementsByTagName("variable");
        for (int i=0; i<variables.getLength(); i++) {
            Element variable = (Element) variables.item(i);
            String varName = variable.getAttribute("name");
            String varVal = variable.getAttribute("value");
            var.add(varName);
            val.add(Double.parseDouble(varVal));
        }
    }
    protected void addGrid () {
        NodeList grid = configDoc.getElementsByTagName("size");

    }
}
