package org.herovole.blogproj.domain.image;

import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImageNameTest {

    private String stringWithLengthOfTen = "1234567890";

    @Test
    void valueOf() {
        assertDoesNotThrow(() -> {
            ImageName.valueOf(stringWithLengthOfTen +
                    stringWithLengthOfTen +
                    stringWithLengthOfTen +
                    stringWithLengthOfTen +
                    stringWithLengthOfTen + "123456.jpg");
        });

        assertThrows(DomainInstanceGenerationException.class, () -> {
            ImageName.valueOf(stringWithLengthOfTen +
                    stringWithLengthOfTen +
                    stringWithLengthOfTen +
                    stringWithLengthOfTen +
                    stringWithLengthOfTen + "1234567.jpg");
        });
    }
}