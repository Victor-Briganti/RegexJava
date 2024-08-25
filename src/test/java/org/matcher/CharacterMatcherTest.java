package org.matcher;

import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterMatcherTest {
    CharacterMatcher character;

    @Test
    public void characterTokenMatch() {
        character = new CharacterMatcher('a');
        assertTrue(character.tokenMatch('a'));
        assertFalse(character.tokenMatch('d'));
    }

    @Test
    public void epsilonGetterToken() {
        character = new CharacterMatcher('b');
        int result = character.getToken().compareTo("b");
        assertEquals(0, result);
    }
}
