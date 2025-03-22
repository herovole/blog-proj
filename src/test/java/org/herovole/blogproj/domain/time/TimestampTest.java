package org.herovole.blogproj.domain.time;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimestampTest {

    @Test
    void longMemorySignature() {
        Timestamp timestamp = Timestamp.now();
        assertEquals(timestamp
                        .letterSignatureYyyyMMddSpaceHHmmss(),
                Timestamp.valueOf(timestamp.longMemorySignature())
                        .letterSignatureYyyyMMddSpaceHHmmss()
        );
    }
}