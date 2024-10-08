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

    // The last symbol that was consumed.
    private Token consumedToken;

    /**
     * Creates a token based on the provided symbol.
     *
     * @param symbol The character to be tokenized.
     * @return The corresponding Token.
     */
    private Token createToken(Character symbol) {
        return switch (symbol) {
            case '(' -> Token.PARENTHESE_OPEN;
            case ')' -> Token.PARENTHESE_CLOSE;
            case '*' -> Token.STAR;
            case '|' -> Token.UNION;
            case '\\' -> Token.SLASH;
            case '.' -> Token.ANY;
            case '+' -> Token.PLUS;
            case '?' -> Token.QUESTION;
            default -> Token.CHARACTER;
        };
    }

    /**
     * Initializes the Lexer with the input string.
     *
     * @param input The input string to be tokenized. Must not be null.
     */
    public Lexer(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string should not be null");
        }

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
                consumedToken = Token.CHARACTER;
                return Token.CHARACTER;
            }

            return Token.NOT_FOUND;
        }

        curPosition++;
        consumedSymbol = currentChar;
        consumedToken = token;
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

    /**
     * Returns the last consumed token.
     *
     * @return The token that was last consumed.
     */
    public Token getConsumedToken() {
        return consumedToken;
    }

}
