package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Class to generate a new XML document from the current state of a CA simulation
 * Source code taken from: https://examples.javacodegeeks.com/core-java/xml/parsers/documentbuilderfactory/create-xml-file-in-java-using-dom-parser-example/
 */
public class XMLSaveBuilder {
    private Document saveDocument;

    public void createSave(String filePath, int gridSize, TreeMap<String, Double> parameterValues, ArrayList<String> saveState) {
        try {
            if(Math.pow(gridSize, 2) > saveState.size()) {
                throw new IllegalArgumentException("There are too few cells being saved for a save file to be created.");
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            saveDocument = dBuilder.newDocument();
            Element root = saveDocument.createElement("simulation");
            saveDocument.appendChild(root);
            appendGrid(gridSize, root, saveDocument);
            appendParameters(parameterValues, root, saveDocument);
            appeandSave(saveState, root, saveDocument);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(saveDocument);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            System.out.println("Cannot create save file");
        } catch (TransformerException f) {
            System.out.println("Cannot create save file");
        }
    }

    private void appendGrid(int gridSize, Element root, Document save) {
        try {
            if(gridSize >= 0) {
                throw new Exception("Invalid grid size. Setting grid to default size of 20.");
            }
            Element grid = save.createElement("grid");
            root.appendChild(grid);
            Element size = save.createElement("size");
            grid.appendChild(size);
            String gridSizeString = Integer.toString(gridSize);
            size.appendChild(save.createTextNode(gridSizeString));
        } catch (Exception e) {
            String gridSizeString = "20";
            Element grid = save.createElement("grid");
            root.appendChild(grid);
            Element size = save.createElement("size");
            grid.appendChild(size);
            size.appendChild(save.createTextNode(gridSizeString));
        }
    }

    private void appendParameters(TreeMap<String, Double> parameterValues, Element root, Document save) {
        if (parameterValues.size() == 0) {
            return;
        }
        for (String s : parameterValues.keySet()) {
            Element parameter = save.createElement("parameter");
            root.appendChild(parameter);
            Element variableName = save.createElement("variable_name");
            parameter.appendChild(save.createElement("variable_name"));
            variableName.appendChild(save.createTextNode(s));
            Element variableValue = save.createElement("variable_value");
            parameter.appendChild(variableValue);
            String variableValueString = parameterValues.get(s).toString();
            variableValue.appendChild(save.createTextNode(variableValueString));
        }
    }

    private void appeandSave(ArrayList<String> saveState, Element root, Document save) {
        Element saveElement = save.createElement("save");
        root.appendChild(saveElement);
        for (int i = 0; i < saveState.size(); i++) {
            Element cellState = save.createElement("cell_state");
            saveElement.appendChild(cellState);
            cellState.appendChild(save.createTextNode(saveState.get(i)));
        }
    }
}
