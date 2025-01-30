package org.herovole.blogproj.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenericSwitchTest {

    @Test
    void isTrue() {
        GenericSwitch t1 = GenericSwitch.positive();
        GenericSwitch t2 = GenericSwitch.valueOf("true");
        GenericSwitch t3 = GenericSwitch.valueOf("TRUE");
        GenericSwitch t4 = GenericSwitch.valueOf("1");
        GenericSwitch f1 = GenericSwitch.negative();
        GenericSwitch f2 = GenericSwitch.valueOf("false");
        GenericSwitch f3 = GenericSwitch.valueOf("FALSE");
        GenericSwitch f4 = GenericSwitch.valueOf("0");

        assertTrue(t1.isTrue());
        assertTrue(t2.isTrue());
        assertTrue(t3.isTrue());
        assertTrue(t4.isTrue());
        assertFalse(f1.isTrue());
        assertFalse(f2.isTrue());
        assertFalse(f3.isTrue());
        assertFalse(f4.isTrue());
    }
}