package org.herovole.blogproj.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IPv4AddressTest {


    @Test
    void isEmpty() {
        assertTrue(IPv4Address.empty().isEmpty());
        assertTrue(IPv4Address.valueOf(0L).isEmpty());
        assertTrue(IPv4Address.valueOf((Long) null).isEmpty());
        assertTrue(IPv4Address.valueOf("").isEmpty());
        assertTrue(IPv4Address.valueOf((String) null).isEmpty());
        assertFalse(IPv4Address.valueOf(256L * 256).isEmpty());
        assertFalse(IPv4Address.valueOf("123.123.123.123").isEmpty());
    }

    @Test
    void toRegularFormat() {
        assertEquals("123.123.123.123", IPv4Address.valueOf("123.123.123.123").toRegularFormat());
        assertEquals("255.255.255.255", IPv4Address.valueOf("255.255.255.255").toRegularFormat());
        assertNull(IPv4Address.valueOf("").toRegularFormat());
        assertNull(IPv4Address.valueOf((String) null).toRegularFormat());
    }

    @Test
    void aton() {
        assertEquals(256L * 256, IPv4Address.valueOf(256L * 256).aton());
        assertEquals(256L * 256 * 256 * 256 - 1, IPv4Address.valueOf(256L * 256 * 256 * 256 - 1).aton());
        assertEquals(0L, IPv4Address.valueOf(0L).aton());
        assertEquals(0L, IPv4Address.valueOf((Long) null).aton());
    }
}