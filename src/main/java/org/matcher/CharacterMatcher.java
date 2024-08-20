package org.matcher;

/**
 * Concrete implementation of the PatternMatcher interface that matches a single
 * character against a stored token
 */
public class CharacterMatcher implements PatternMatcher {
    private char token;

    public CharacterMatcher(char token) {
        this.token = token;
    }

    @Override
    public boolean isEpsilon() {
        return false;
    }

    @Override
    public boolean tokenMatch(char token) {
        return this.token == token;
    }

    @Override
    public String getToken() {
        return Character.toString(token);
    }

}
