package org.matcher;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AnyMatcherTest {
    AnyMatcher any;

    @Test
    public void anyTokenMatch() {
        any = new AnyMatcher();
        assertTrue(any.tokenMatch('a'));
        assertTrue(any.tokenMatch('d'));
    }

}
