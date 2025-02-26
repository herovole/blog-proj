package org.herovole.blogproj.domain.accesskey;

public interface AccessKey {
    boolean isEmpty();

    String memorySignature();

    boolean correspondsWith(String expression);
    AccessKey appendWithSlash(AccessKey accessKey);
}
