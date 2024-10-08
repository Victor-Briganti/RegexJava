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
        assertEquals(0, byteOutput.toString().compareTo(result));
    }

    @Test
    public void parserParentheses() {
        String dummy = "(aaa(a(a)a)aa)bbbb(ccc)ll(dd)\\)\\(\\)";
        String result = "(aaa(a(a)a)aa)bbbb(ccc)ll(dd))()";
        parser = new Parser(dummy);
        assertEquals(parser.parse(), 0);
        parser.getAST().printNode();
        assertEquals(0, byteOutput.toString().compareTo(result));
    }

    @Test
    public void parserStar() {
        String dummy = "aa*bbb*jj\\*jsa*";
        String result = "aa*bbb*jj*jsa*";
        parser = new Parser(dummy);
        assertEquals(parser.parse(), 0);
        parser.getAST().printNode();
        assertEquals(0, byteOutput.toString().compareTo(result));
    }

    @Test
    public void parserUnion() {
        String dummy = "(aaa|bb(b|a)a)|x";
        parser = new Parser(dummy);
        assertEquals(parser.parse(), 0);
        parser.getAST().printNode();
        assertEquals(0, byteOutput.toString().compareTo(dummy));
    }

    @Test
    public void parserExpression() {
        String dummy = "(aaa|bb(b|a)a)*xx(y)cd*n|z|m";
        parser = new Parser(dummy);
        assertEquals(parser.parse(), 0);
        parser.getAST().printNode();
        assertEquals(0, byteOutput.toString().compareTo(dummy));
    }

    @Test
    public void parserEmpty() {
        String dummy = "n||z||m||";
        parser = new Parser(dummy);
        assertEquals(parser.parse(), 0);
        parser.getAST().printNode();
        assertEquals(0, byteOutput.toString().compareTo(dummy));
    }

    @Test
    public void parserDoubleStarError() {
        String dummy = "a**";
        parser = new Parser(dummy);
        int expectedValue = ErrorType.DOUBLE_STAR.getValue();
        assertEquals(parser.parse(), expectedValue);
    }

    @Test
    public void parserParenthesesWithoutCloseError() {
        String dummy = "((a)";
        parser = new Parser(dummy);
        int expectedValue = ErrorType.PARENTHESE_WITHOUT_CLOSE.getValue();
        assertEquals(parser.parse(), expectedValue);
    }

    @Test
    public void parserInvalidTokenError() {
        String dummy = ")a)";
        parser = new Parser(dummy);
        int expectedValue = ErrorType.INVALID_TOKEN.getValue();
        assertEquals(parser.parse(), expectedValue);
    }

    @Test
    public void parserAnyToken() {
        String dummy = "(...)a";
        parser = new Parser(dummy);
        assertEquals(0, parser.parse());
        parser.getAST().printNode();
        assertEquals(0, byteOutput.toString().compareTo(dummy));
    }

    @Test
    public void parserSpecialAnyToken() {
        String dummy = "\\.(...)a";
        String result = ".(...)a";
        parser = new Parser(dummy);
        assertEquals(0, parser.parse());
        parser.getAST().printNode();
        assertEquals(0, byteOutput.toString().compareTo(result));
    }

    @Test
    public void parserPlusToken() {
        String dummy = "a?b+c+d";
        parser = new Parser(dummy);
        assertEquals(0, parser.parse());
        parser.getAST().printNode();
        assertEquals(0, byteOutput.toString().compareTo(dummy));
    }

    @Test
    public void parserSpecialPlusToken() {
        String dummy = "\\++a+b+c+dE";
        String result = "++a+b+c+dE";
        parser = new Parser(dummy);
        assertEquals(0, parser.parse());
        parser.getAST().printNode();
        assertEquals(0, byteOutput.toString().compareTo(result));
    }

    @Test
    public void parserQuestionToken() {
        String dummy = "a?b?c?d";
        parser = new Parser(dummy);
        assertEquals(0, parser.parse());
        parser.getAST().printNode();
        assertEquals(0, byteOutput.toString().compareTo(dummy));
    }

    @Test
    public void parserSpecialQuestionToken() {
        String dummy = "\\??a?b?c?dE";
        String result = "??a?b?c?dE";
        parser = new Parser(dummy);
        assertEquals(0, parser.parse());
        parser.getAST().printNode();
        assertEquals(0, byteOutput.toString().compareTo(result));
    }

}
