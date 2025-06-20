package org.herovole.blogproj.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FormContent {

    private static final String SPLITTER = "\\.";

    public static FormContent of(Map<String, String> kv) {
        final Map<String[], String> field = new HashMap<>();
        for (Map.Entry<String, String> e : kv.entrySet()) {
            String[] keyLayers = e.getKey().split(SPLITTER);
            field.put(keyLayers, e.getValue());
        }
        return new FormContent(field);
    }

    private final Map<String[], String> hash;

    /**
     * "articleEditingPage.commentEditor.1.text"
     *
     * @param keyRoot "articleEditingPage"
     * @return "commentEditor.1.text"
     */
    public FormContent getChildren(String keyRoot) {
        final Map<String[], String> field = new HashMap<>();
        for (Map.Entry<String[], String> e : this.hash.entrySet()) {
            String[] originalKey = e.getKey();
            if (originalKey[0].equals(keyRoot)) {
                String[] newKey = new String[originalKey.length - 1];
                System.arraycopy(originalKey, 1, newKey, 0, newKey.length);
                field.put(newKey, e.getValue());
            }
        }
        return new FormContent(field);
    }

    public FormContent getGrandchildren(String keyRoot, String keyChild) {
        FormContent child = this.getChildren(keyRoot);
        return child.getChildren(keyChild);
    }

    public String getValue() {
        String value = this.hash.entrySet().stream().findFirst().map(Map.Entry::getValue).orElse(null);
        return value == null ? null : URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private int getLength() {
        return this.hash.size();
    }

    public FormContents getInArray() {
        FormContent[] hashes = new FormContent[this.getLength()];
        for (Map.Entry<String[], String> e : this.hash.entrySet()) {
            if (e.getKey().length == 0) continue;
            String strIndex = e.getKey()[0];
            int index = Integer.parseInt(strIndex);
            hashes[index] = this.getChildren(strIndex);
        }
        return FormContents.of(hashes);
    }

    public void println(String prefix) {
        for (Map.Entry<String[], String> e : this.hash.entrySet()) {
            System.out.print(prefix + " - ");
            for (String key : e.getKey()) {
                System.out.print(key + " ");
            }
            System.out.println(":" + e.getValue());
        }
    }

}
