package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class IntegerId implements Comparable<IntegerId> {

    private static final String EMPTY = "-";

    public static IntegerId valueOf(long field) {
        return new IntegerId(field);
    }

    public static IntegerId valueOf(String field) {
        return new IntegerId(Long.parseLong(field, 10));
    }

    public static IntegerId empty() {
        return new IntegerId(null);
    }

    private final Long id;

    public boolean isEmpty() {
        return null == id;
    }

    public String letterSignature() {
        return this.isEmpty() ? EMPTY : String.valueOf(this.id);
    }

    public Long memorySignature() {
        return this.id;
    }

    @Override
    public int compareTo(IntegerId o) {
        Long thisValue = this.isEmpty() ? -1 : this.id;
        Long thatValue = o.isEmpty() ? -1 : o.id;
        return thisValue.compareTo(thatValue);
    }
}
