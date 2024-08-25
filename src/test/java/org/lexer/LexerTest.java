package org.lexer;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LexerTest {
    String dummy = "\\\\abcd|efgh)()*|\n|~\\*.++";

    @Test
    public void consume() {
        Lexer lexer = new Lexer(dummy);

        Token[] expectedTokens = {
                Token.CHARACTER,
                Token.CHARACTER,
                Token.CHARACTER,
                Token.CHARACTER,
                Token.CHARACTER,
                Token.UNION,
                Token.CHARACTER,
                Token.CHARACTER,
                Token.CHARACTER,
                Token.CHARACTER,
                Token.PARENTHESE_CLOSE,
                Token.PARENTHESE_OPEN,
                Token.PARENTHESE_CLOSE,
                Token.STAR,
                Token.UNION,
                Token.CHARACTER,
                Token.UNION,
                Token.CHARACTER,
                Token.CHARACTER,
                Token.ANY,
                Token.PLUS,
                Token.PLUS,
        };

        for (Token expectedToken : expectedTokens) {
            assertEquals(expectedToken, lexer.consume());
        }
    }

    @Test
    public void peek() {
        Lexer lexer = new Lexer(dummy);

        Token[] expectedTokens = {
                Token.CHARACTER,
                Token.CHARACTER,
                Token.CHARACTER,
                Token.CHARACTER,
                Token.UNION,
                Token.CHARACTER,
                Token.CHARACTER,
                Token.CHARACTER,
                Token.CHARACTER,
                Token.PARENTHESE_CLOSE,
                Token.PARENTHESE_OPEN,
                Token.PARENTHESE_CLOSE,
                Token.STAR,
                Token.UNION,
                Token.CHARACTER,
                Token.UNION,
                Token.CHARACTER,
                Token.CHARACTER,
                Token.ANY,
                Token.PLUS,
                Token.PLUS,
                Token.EOF
        };

        for (Token expectedToken : expectedTokens) {
            lexer.consume();
            assertEquals(expectedToken, lexer.peek());
        }
    }

    @Test
    public void consumedSymbol() {
        Lexer lexer = new Lexer(dummy);

        Character[] expectedChars = {
                '\\',
                'a',
                'b',
                'c',
                'd',
                '|',
                'e',
                'f',
                'g',
                'h',
                ')',
                '(',
                ')',
                '*',
                '|',
                '\n',
                '|',
                '~',
                '*',
                '.',
                '+',
                '+',
        };

        for (Character expectedChar : expectedChars) {
            lexer.consume();
            assertEquals(expectedChar, lexer.getConsumedSymbol());
        }
    }

}
