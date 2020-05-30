package com.teltov.testrail_api.utils;

import com.teltov.testrail_api.testrail.testng.TestResult;
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
import java.util.List;

public class TestNgResultsXmlParser {
    public static List<TestResult> convertXmlToTestResult(String filePath) {
        File testNgResultXmlFile = new File(filePath);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        //Build Document
        Document document = null;
        try {
            document = builder.parse(testNgResultXmlFile);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Normalize the XML Structure;
        document.getDocumentElement().normalize();

        NodeList tMethods = document.getElementsByTagName("test-method");
        List<TestResult> testResults = new ArrayList<>();

        for (int temp = 0; temp < tMethods.getLength(); temp++){
            Node node = tMethods.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element eElement = (Element) node;
                // Get parent element to capture suite name
                Element suiteElement = (Element) eElement.getParentNode();
                String testClassName = suiteElement.getAttribute("name");
                String testMethodName = eElement.getAttribute("name");
                String testStatus = eElement.getAttribute("status");
                String durationMs = eElement.getAttribute("duration-ms");
                String exception = "";

                // Get exception message if exist
                Node eNode = eElement.getElementsByTagName("exception").item(0);
                Element exceptionNode = (Element) eNode;
                if (exceptionNode != null) {
                    exception = exceptionNode.getAttribute("class");
                }

                var testResult = new TestResult(testClassName, testMethodName, testStatus, exception);
                testResults.add(testResult);
            }
        }
        return testResults;
    }
}
