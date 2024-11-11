package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostContent {

    private static final String SPLITTER = "\\.";

    public static PostContent of(Map<String, String> kv) {
        final Map<String[], String> field = new HashMap<>();
        for (Map.Entry<String, String> e : kv.entrySet()) {
            String[] keyLayers = e.getKey().split(SPLITTER);
            field.put(keyLayers, e.getValue());
        }
        return new PostContent(field);
    }

    private final Map<String[], String> hash;

    /**
     * "articleEditingPage.commentEditor.1.text"
     *
     * @param keyRoot "articleEditingPage"
     * @return "commentEditor.1.text"
     */
    public PostContent getChildren(String keyRoot) {
        final Map<String[], String> field = new HashMap<>();
        for (Map.Entry<String[], String> e : this.hash.entrySet()) {
            String[] originalKey = e.getKey();
            if (originalKey[0].equals(keyRoot)) {
                String[] newKey = new String[originalKey.length - 1];
                System.arraycopy(originalKey, 1, newKey, 0, newKey.length);
                field.put(newKey, e.getValue());
            }
        }
        return new PostContent(field);
    }

    public String getValue() {
        return this.hash.entrySet().stream().findFirst().map(Map.Entry::getValue).orElse(null);
    }

    private int getLength() {
        return this.hash.size();
    }

    public PostContents getInArray() {
        PostContent[] hashes = new PostContent[this.getLength()];
        for (Map.Entry<String[], String> e : this.hash.entrySet()) {
            String strIndex = e.getKey()[0];
            int index = Integer.parseInt(strIndex);
            hashes[index] = this.getChildren(strIndex);
        }
        return PostContents.of(hashes);
    }

}
