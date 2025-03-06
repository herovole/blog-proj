package org.herovole.blogproj.presentation.filter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EndpointPhrases {

    public static EndpointPhrases of(String... endpoints) {
        return new EndpointPhrases(endpoints);
    }

    private final String[] phrases;

    public boolean isContainedIn(String uri) {
        String formattedUri = uri.toLowerCase();
        return Stream.of(phrases).anyMatch(formattedUri::contains);
    }

}
