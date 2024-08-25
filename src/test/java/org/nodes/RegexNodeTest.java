package org.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class RegexNodeTest {
    // Variables used to redirect the output
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
        assertEquals('a', this.byteOutput.toString().charAt(0));
        System.out.flush();
    }

    @Test
    public void starNode() {
        CharNode charNode = new CharNode(null, null, 'a');
        StarNode starNode = new StarNode(null, charNode);
        starNode.printNode();

        int result = byteOutput.toString().compareTo("a*");
        assertEquals(0, result);
    }

    @Test
    public void unionNode() {
        CharNode charNode = new CharNode(null, null, 'a');
        UnionAuxNode unionAuxNode = new UnionAuxNode(charNode, charNode);
        UnionNode unionNode = new UnionNode(null, unionAuxNode);
        unionNode.printNode();

        int result = byteOutput.toString().compareTo("a|a");
        assertEquals(0, result);
    }

    @Test
    public void groupNode() {
        CharNode charNode = new CharNode(null, null, 'a');
        UnionAuxNode unionAuxNode = new UnionAuxNode(charNode, charNode);
        UnionNode unionNode = new UnionNode(null, unionAuxNode);
        GroupNode groupNode = new GroupNode(null, unionNode);
        groupNode.printNode();

        int result = byteOutput.toString().compareTo("(a|a)");
        assertEquals(0, result);
    }

    @Test
    public void anyNode() {
        AnyNode node = new AnyNode(null, null);
        node.printNode();
        assertEquals('.', this.byteOutput.toString().charAt(0));
        System.out.flush();
    }

    @Test
    public void plusNode() {
        PlusNode node = new PlusNode(null, null);
        node.printNode();
        assertEquals('+', this.byteOutput.toString().charAt(0));
        System.out.flush();
    }

    @Test
    public void questionNode() {
        QuestionNode node = new QuestionNode(null, null);
        node.printNode();
        assertEquals('?', this.byteOutput.toString().charAt(0));
        System.out.flush();
    }

}
