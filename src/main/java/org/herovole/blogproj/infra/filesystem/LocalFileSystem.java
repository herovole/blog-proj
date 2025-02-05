package org.herovole.blogproj.infra.filesystem;


import lombok.EqualsAndHashCode;
import org.herovole.blogproj.domain.accesskey.AccessKey;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@EqualsAndHashCode
public class LocalFileSystem {

    private final Path directoryBoundary;

    public LocalFileSystem(String boundary) throws HazardousFileSystemNodeException {
        if (boundary == null || boundary.isEmpty()) throw new HazardousFileSystemNodeException("boundary isn't set.");
        Path directoryBoundaryDeclared = Paths.get(boundary);
        if (Files.isDirectory(directoryBoundaryDeclared)) {
            this.directoryBoundary = directoryBoundaryDeclared;
        } else {
            throw new HazardousFileSystemNodeException("Declared boundary isn't a directory.");
        }
    }

    private void verify(AccessKey accessKey) throws IOException {
        this.verify(Paths.get(accessKey.memorySignature()));
    }

    public void verify(Path path) throws IOException {
        try {
            if (!path.toAbsolutePath().normalize().startsWith(
                    directoryBoundary.toAbsolutePath().normalize()
            )) {
                throw new HazardousFileSystemNodeException("new system node is outside of the boundary. : " + this.directoryBoundary.toAbsolutePath().normalize());
            }
        } catch (IOException e) {
            throw new IOException("failed to verify new system node : " + path.toAbsolutePath().normalize());
        }
    }

}

