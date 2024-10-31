package org.herovole.blogproj.infra.filesystem;

import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor
public class HazardousFileSystemNodeException extends IOException {
    public HazardousFileSystemNodeException(String message) {
        super(message);
    }
}
