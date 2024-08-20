package org.parser;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {
    Parser parser;

    // Variables used to redirect the output
    ByteArrayOutputStream byteOutput;
    PrintStream printStream;
    PrintStream commonOut;

    void setCommonOutput() {
        System.out.flush();
        System.setOut(commonOut);
    }

    void setRedirectedOutput() {
        // Redirecting the output to a byte ouput for verifications.
        // Thanks to:
        // https://stackoverflow.com/questions/8708342/redirect-console-output-to-string-in-java
        commonOut = System.out;
        System.setOut(printStream);
    }

    public ParserTest() {
        byteOutput = new ByteArrayOutputStream();
        printStream = new PrintStream(this.byteOutput);
        setRedirectedOutput();
    }

    @Test
    public void parserChar() {
        String dummy = "abcdefghij\\\\";
        String result = "abcdefghij\\";
        parser = new Parser(dummy);
        assertEquals(parser.parse(), 0);
        parser.getAST().printNode();
        assertTrue(byteOutput.toString().compareTo(result) == 0);
    }

    @Test
    public void parserParentheses() {
        String dummy = "(aaa(a(a)a)aa)bbbb(ccc)ll(dd)\\)\\(\\)";
        String result = "(aaa(a(a)a)aa)bbbb(ccc)ll(dd))()";
        parser = new Parser(dummy);
        assertEquals(parser.parse(), 0);
        parser.getAST().printNode();
        assertTrue(byteOutput.toString().compareTo(result) == 0);
    }

    @Test
    public void parserStar() {
        String dummy = "aa*bbb*jj\\*jsa*";
        String result = "aa*bbb*jj*jsa*";
        parser = new Parser(dummy);
        assertEquals(parser.parse(), 0);
        parser.getAST().printNode();
        assertTrue(byteOutput.toString().compareTo(result) == 0);
    }

    @Test
    public void parserUnion() {
        String dummy = "(aaa|bb(b|a)a)|x";
        parser = new Parser(dummy);
        assertEquals(parser.parse(), 0);
        parser.getAST().printNode();
        assertTrue(byteOutput.toString().compareTo(dummy) == 0);
    }

    @Test
    public void parserExpression() {
        String dummy = "(aaa|bb(b|a)a)*xx(y)cd*n|z|m";
        parser = new Parser(dummy);
        assertEquals(parser.parse(), 0);
        parser.getAST().printNode();
        assertTrue(byteOutput.toString().compareTo(dummy) == 0);
    }

}
