package com.calculatoria.utils.readers;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractXmlReader {
    private Document doc;
    private XPathFactory xPathfactory;


    /**
     * Creates a reader for the given {@link File}.
     *
     * @param file an Excel-XML file. Must not be null.
     */
    protected AbstractXmlReader(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            xPathfactory = XPathFactory.newInstance();
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    protected AbstractXmlReader(Path file) {
        this(new File(file.toString()));
    }

    protected List<Node> findAll(String xpath) throws XPathExpressionException {
        return findAll(doc, xpath);
    }

    protected List<Node> findAll(Object item, String xpath) throws XPathExpressionException {
        XPathExpression expression = xPathfactory.newXPath().compile(xpath);
        NodeList results = (NodeList) expression.evaluate(item, XPathConstants.NODESET);

        List<Node> list = new ArrayList<>();
        for (int i = 0; i < results.getLength(); i++) {
            list.add(results.item(i));
        }
        return list;
    }

    protected String getText(Node node, String xpath) throws XPathExpressionException {
        XPathExpression expression = xPathfactory.newXPath().compile(xpath);
        String result = "";
        try {
            result = (String) expression.evaluate(node, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
        }
        return result;
    }

}
