package com.calculatoria.utils.readers;


import com.calculatoria.utils.TestListener;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathExpressionException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestDataPoolReader extends AbstractXmlReader {
    public static final String DATAPOOL_NAME = "testdata.xml";

    public static final TestDataPoolReader INSTANCE = new TestDataPoolReader();

    private TestDataPoolReader() {
        super(Paths.get(System.getProperty("testdata.dir", "config/testdata"), DATAPOOL_NAME));
    }

    public List<TestDataPoolRecord> getRecords() {
        List<TestDataPoolRecord> results = new ArrayList<>();
        try {
            List<Node> searchParams = findAll("//testdata");

            for (Node node : searchParams) {
                TestDataPoolRecord record = new TestDataPoolRecord();
                record.setCalculatorType(getText(node, "./calculatorType/text()"));
                record.setOperation(getText(node, "./operation/text()"));
                record.setFirstNumber(getText(node, "./numbers/firstNumber/text()"));
                record.setSecondNumber(getText(node, "./numbers/secondNumber/text()"));

                results.add(record);
            }

        } catch (XPathExpressionException e) {
            e.printStackTrace();
            TestListener.attachStacktrace(e.getMessage());
        }

        return results;
    }
}
