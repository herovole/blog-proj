package org.herovole.blogproj.domain.helper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AggregateSignatureSplits {
    public static class Builder {
        private final Map<Integer, Object> map = new HashMap<>();

        public Builder set(int index, Object o) {
            map.put(index, o);
            return this;
        }

        public AggregateSignatureSplits build() {
            int maxIndex = map.keySet().stream().max(Integer::compareTo).orElse(-1);
            String[] splits = new String[maxIndex + 1];
            for (int i = 0; i <= maxIndex; i++) {
                splits[i] = map.containsKey(i) ? map.get(i).toString() : "";
            }
            return new AggregateSignatureSplits(splits);
        }
    }

    public static AggregateSignatureSplits of(String separator, String line) {
        return new AggregateSignatureSplits(line.split(separator));
    }

    private final String[] splits;

    public String letterSignature(String separator) {
        return String.join(separator, this.splits);
    }

    public String get(int i) {
        return splits[i];
    }
    public int length() {
        return this.splits.length;
    }
}
