package org.matcher;

/**
 * Concrete implementation of the PatternMatcher interface that matches any
 * character.
 * Differently of the Epsilon this matcher does consume a token.
 */
public class AnyMatcher implements PatternMatcher {

    @Override
    public boolean isEpsilon() {
        return false;
    }

    @Override
    public boolean tokenMatch(char token) {
        return true;
    }

    @Override
    public String getToken() {
        return ".";
    }

}
