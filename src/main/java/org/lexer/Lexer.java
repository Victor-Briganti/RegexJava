package org.lexer;

public class Lexer {
    String input;
    Integer curPosition;
    Character consumedSymbol;

    Token createToken(Character symbol) {
        switch (symbol) {
            case '(':
                return Token.PARENTHESE_OPEN;
            case ')':
                return Token.PARENTHESE_CLOSE;
            case '*':
                return Token.STAR;
            case '|':
                return Token.UNION;
            case '\\':
                return Token.SLASH;
            default:
                return Token.CHARACTER;
        }
    }

    public Lexer(String input) {
        assert input != null;
        this.input = input;
        this.curPosition = 0;
    }

    public void setInput(String input) {
        this.input = input;
    }

    // Consumes the char at the current position and returns its equivalent token.
    public Token consume() {
        if (curPosition >= input.length()) {
            return Token.EOF;
        }

        char currentChar = input.charAt(curPosition);
        Token token = createToken(currentChar);
        if (token == Token.SLASH) {
            if (curPosition + 1 > input.length()) {
                return Token.NOT_FOUND;
            }

            char nextChar = input.charAt(curPosition + 1);
            Token specialToken = createToken(nextChar);

            // A special character is going to be matched
            if (specialToken != Token.CHARACTER) {
                curPosition += 2;
                consumedSymbol = nextChar;
                return Token.CHARACTER;
            }

            return Token.NOT_FOUND;
        }

        curPosition++;
        consumedSymbol = currentChar;
        return token;
    }

    // Returns the token of the current position.
    public Token peek() {
        if (curPosition >= input.length()) {
            return Token.EOF;
        }

        char currentChar = input.charAt(curPosition);
        Token token = createToken(currentChar);
        if (token == Token.SLASH) {
            if (curPosition + 1 > input.length()) {
                return Token.NOT_FOUND;
            }

            char nextChar = input.charAt(curPosition + 1);
            Token specialToken = createToken(nextChar);

            // A special character is going to be matched
            if (specialToken != Token.CHARACTER) {
                return Token.CHARACTER;
            }

            return Token.NOT_FOUND;
        }

        return token;
    }

    public Character getConsumedSymbol() {
        return consumedSymbol;
    }

}
