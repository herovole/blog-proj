package org.herovole.blogproj.domain.comment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.stream.Stream;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TextBlackUnit {


    private static final String SEPARATOR = " ";

    public static TextBlackUnit empty() {
        return new TextBlackUnit(new String[0]);
    }

    public static TextBlackUnit fromLine(String line) {
        String[] words = line.split(SEPARATOR);
        String[] words1 = Stream.of(words).filter(e -> !e.isEmpty() && !e.isBlank()).toArray(String[]::new);
        return new TextBlackUnit(words1);
    }

    private final String[] words;

    public boolean isEmpty() {
        return this.words == null || this.words.length == 0;
    }

    public boolean appliesTo(String text) {
        if (text == null || this.isEmpty()) return false;
        for (String word : words) {
            if (!text.toLowerCase().contains(word.toLowerCase())) return false;
        }
        return true;
    }
}
