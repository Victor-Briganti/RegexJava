package org.matcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EpsilonMatcherTest {
    EpsilonMatcher espilon;

    @Test
    public void epsilonTokenMatch() {
        espilon = new EpsilonMatcher();
        assertTrue(espilon.tokenMatch('a'));
        assertTrue(espilon.tokenMatch('b'));
        assertTrue(espilon.tokenMatch('c'));
        assertTrue(espilon.tokenMatch('d'));
    }

    @Test
    public void epsilonGetterToken() {
        espilon = new EpsilonMatcher();
        int result = espilon.getToken().compareTo("{Epsilon}");
        assertEquals(0, result);
    }

}