package org.example.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.ComparisonFailure;
import org.junit.Test;

public class Issue1446_Test {
    @Test
    public void testFoobars() { // this passes but should fail
        assertEquals("\bfoo", "\bbar");
    }

    @Test
    public void stringsStartingWithSameEscapedCharacterDiffer() {
        try {
            assertEquals("\bbar", "\bfoo");
        } catch (ComparisonFailure exception) {
            assertEquals("expected:<\b[bar]> but was:<\b[foo]>", exception.getMessage());
            return;
        }
        fail("ComparisonFailure expected");
    }
}
