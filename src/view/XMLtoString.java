package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLtoString {
    public XMLtoString() {

    }

    private String xml2String;
    /**
     * Java Program to read XML as String using BufferedReader, DOM parser and jCabi-xml
     * open source library.
     */

    public String getXmlString() {
        try {
            try {
                    // our XML file for this example
                    File xmlFile = new File("Game_Of_Life.xml");

                    // Let's get XML file as String using BufferedReader
                    // FileReader uses platform's default character encoding
                    // if you need to specify a different encoding, use InputStreamReader
                    Reader fileReader = new FileReader(xmlFile);
                    BufferedReader bufReader = new BufferedReader(fileReader);

                    StringBuilder sb = new StringBuilder();
                    String line = bufReader.readLine();
                    if(line == null) {
                        throw new SAXException();
                    }
                    while (line != null) {
                        sb.append(line).append("\n");
                        line = bufReader.readLine();
                    }
                    xml2String = sb.toString();
                    //System.out.println(xml2String);
                    bufReader.close();
                    return xml2String;
            }catch (SAXException f) {
                return "0";
            }
        } catch (IOException g) {
            return "0";
        }
    }
}
