package org.nodes;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class RegexNodeTest {
    ByteArrayOutputStream byteOutput;
    PrintStream printStream;
    PrintStream commonOut;

    void setCommonOutput() {
        System.out.flush();
        System.setOut(commonOut);
    }

    public RegexNodeTest() {
        // Redirecting the output to a byte ouput for verifications.
        // Thanks to:
        // https://stackoverflow.com/questions/8708342/redirect-console-output-to-string-in-java
        byteOutput = new ByteArrayOutputStream();
        printStream = new PrintStream(this.byteOutput);
        commonOut = System.out;
        System.setOut(printStream);
    }

    @Test
    public void charNode() {
        CharNode node = new CharNode(null, null, 'a');
        node.printNode();
        assertTrue(this.byteOutput.toString().charAt(0) == 'a');
        System.out.flush();
    }

    @Test
    public void starNode() {
        CharNode charNode = new CharNode(null, null, 'a');
        StarNode starNode = new StarNode(charNode, null);
        starNode.printNode();

        int result = byteOutput.toString().compareTo("a*");
        assertTrue(result == 0);
    }

    @Test
    public void unionNode() {
        CharNode charNode = new CharNode(null, null, 'a');
        UnionNode unionNode = new UnionNode(charNode, charNode);
        unionNode.printNode();

        int result = byteOutput.toString().compareTo("a|a");
        assertTrue(result == 0);
    }

    @Test
    public void groupNode() {
        CharNode charNode = new CharNode(null, null, 'a');
        UnionNode unionNode = new UnionNode(charNode, charNode);
        GroupNode groupNode = new GroupNode(unionNode, null);
        groupNode.printNode();

        int result = byteOutput.toString().compareTo("(a|a)");
        assertTrue(result == 0);
    }

    @Test
    public void elementaryNode() {
        CharNode charNode = new CharNode(null, null, 'a');
        UnionNode unionNode = new UnionNode(charNode, charNode);
        GroupNode groupNode = new GroupNode(unionNode, null);
        ElementaryNode elementNode = new ElementaryNode(groupNode, null);
        elementNode.printNode();

        int result = byteOutput.toString().compareTo("(a|a)");
        assertTrue(result == 0);
    }

    @Test
    public void basicNode() {
        CharNode charNode = new CharNode(null, null, 'a');
        UnionNode unionNode = new UnionNode(charNode, charNode);
        GroupNode groupNode = new GroupNode(unionNode, null);
        BasicNode basicNode = new BasicNode(groupNode, null);
        basicNode.printNode();

        int result = byteOutput.toString().compareTo("(a|a)");
        assertTrue(result == 0);
    }

}
