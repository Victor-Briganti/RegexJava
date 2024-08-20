package org.lexer;

/**
 * Lexer is a class that processes an input string to produce tokens based on
 * predefined symbols and patterns. It is used to analyze and tokenize the
 * input.
 */
public class Lexer {
    // The input string to be tokenized.
    private String input;

    // Current position within the input string.
    private Integer curPosition;

    // The last symbol that was consumed.
    private Character consumedSymbol;

    /**
     * Creates a token based on the provided symbol.
     *
     * @param symbol The character to be tokenized.
     * @return The corresponding Token.
     */
    private Token createToken(Character symbol) {
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

    /**
     * Initializes the Lexer with the input string.
     *
     * @param input The input string to be tokenized. Must not be null.
     */
    public Lexer(String input) {
        assert input != null : "Input must not be null";
        this.input = input;
        this.curPosition = 0;
    }

    /**
     * Sets a new input string for the Lexer.
     *
     * @param input The new input string.
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Consumes the character at the current position and returns its corresponding
     * token.
     * Updates the position and consumed symbol accordingly.
     *
     * @return The token corresponding to the current character or EOF if the end of
     *         input is reached.
     */
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

    /**
     * Peeks at the character at the current position and returns its corresponding
     * token
     * without advancing the position. Handles special cases where the character is
     * a backslash ('\\').
     *
     * @return The token corresponding to the current character or EOF if the end of
     *         input is reached.
     */
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

    /**
     * Returns the last consumed symbol.
     *
     * @return The character that was last consumed.
     */
    public Character getConsumedSymbol() {
        return consumedSymbol;
    }

}
