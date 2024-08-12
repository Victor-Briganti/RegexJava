package org.regexjava;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RegexTest {
    Regex regex;

    @Test
    public void matchStar() {
        regex = new Regex();
        assertTrue(regex.match("a*b", "baaaaaaaaaaaaaaab") < 0);
    }
}
