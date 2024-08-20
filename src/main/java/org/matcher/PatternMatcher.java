package org.matcher;

/**
 * Interface for matching a pattern passed by input.
 */
public interface PatternMatcher {
    /**
     * Epsilon are matchers that allows the automaton to move from one state to
     * another without consuming any input symbol.
     * 
     * @return true if is an espsilon, false otherwise.
     */
    public boolean isEpsilon();

    /**
     * Compares the given token with the stored token.
     * Epsilon always return true.
     * 
     * @param token character to be compare with the stored token.
     * @return true if the given token matches the stored token, false otherwise
     */
    public boolean tokenMatch(char token);

    /**
     * Getter for the token stored on the current pattern matcher.
     * For debugging purposes only.
     * 
     * @return The token of this matcher
     */
    public String getToken();
}
