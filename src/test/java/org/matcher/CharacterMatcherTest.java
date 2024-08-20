package org.matcher;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CharacterMatcherTest {
    CharacterMatcher character;

    @Test
    public void characterTokenMatch() {
        character = new CharacterMatcher('a');
        assertTrue(character.tokenMatch('a'));
        assertTrue(!character.tokenMatch('d'));
    }

    @Test
    public void epsilonGetterToken() {
        character = new CharacterMatcher('b');
        int result = character.getToken().compareTo("b");
        assertTrue(result == 0);
    }
}
