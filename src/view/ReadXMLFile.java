package view;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * This class will read any XML File according to a preset format. May have to merge with other classes?
 * Code taken from https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 */

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ReadXMLFile {
    public void readFile(String filename) {
        try {

            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();

            DefaultHandler defaultHandler = new DefaultHandler() {

                boolean empId = false;
                boolean firstName = false;
                boolean lastName = false;
                boolean designation = false;

                public void startElement(String uri, String localName, String elmt, Attributes attr)
                        throws SAXException {

                    System.out.println("Start Element :" + elmt);
                    if (elmt.equalsIgnoreCase("grid_size")) {
                        empId = true;
                    }

                    if (elmt.equalsIgnoreCase("cell_state")) {
                        firstName = true;
                    }

                    if (elmt.equalsIgnoreCase("number_of_cells")) {
                        lastName = true;
                    }

                    if (elmt.equalsIgnoreCase("DESIGNATION")) {
                        designation = true;
                    }

                }

                public void endElement(String uri, String localName, String elmt) throws SAXException {

                    System.out.println("End Element :" + elmt);

                }

                public void characters(char chars[], int start, int len) throws SAXException {
                    try{
                        if (empId) {
                            System.out.println("EMPLOYEE ID : " + new String(new String(chars, start, len)));
                            empId = false;
                        }
                        if (firstName) {
                            System.out.println("EMPLOYEE First Name : " + new String(chars, start, len));
                            firstName = false;
                        }
                        if (lastName) {
                            System.out.println("EMPLOYEE Last Name : " + new String(chars, start, len));
                            lastName = false;
                        }
                        if (designation) {
                            System.out.println("EMPLOYEE Designation : " + new String(chars, start, len));
                            designation = false;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            saxParser.parse(filename, defaultHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
