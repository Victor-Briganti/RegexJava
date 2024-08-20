package org.matcher;

/**
 * Concrete implementation of the PatternMatcher interface that matches any
 * character.
 * Used for transition of states without consuming any symbol.
 */
public class EpsilonMatcher implements PatternMatcher {

    @Override
    public boolean isEpsilon() {
        return true;
    }

    @Override
    public boolean tokenMatch(char token) {
        return true;
    }

    @Override
    public String getToken() {
        return "{Epsilon}";
    }

}
