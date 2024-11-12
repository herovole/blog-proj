package org.herovole.blogproj.infra.filesystem;


import org.herovole.blogproj.domain.accesskey.AccessKey;

import java.io.File;
import java.io.IOException;

public class LocalFileSystemNodeFactory {
    private static LocalFileSystemNodeFactory instance;

    public static LocalFileSystemNodeFactory getInstance() {
        return instance;
    }

    private final File directoryBoundary;

    public static void initialize(String boundary) throws HazardousFileSystemNodeException {
        if (instance != null) throw new HazardousFileSystemNodeException("forbidden to overwrite boundary");
        instance = new LocalFileSystemNodeFactory(boundary);
    }

    private LocalFileSystemNodeFactory(String boundary) throws HazardousFileSystemNodeException {
        if (boundary == null || boundary.isEmpty()) throw new HazardousFileSystemNodeException();
        File directoryBoundaryDeclared = new File(boundary);
        if (directoryBoundaryDeclared.isDirectory()) {
            this.directoryBoundary = directoryBoundaryDeclared;
        } else {
            throw new HazardousFileSystemNodeException();
        }
    }

    private void verify(AccessKey accessKey) throws IOException {
        this.verify(new File(accessKey.memorySignature()));
    }

    private void verify(File file) throws IOException {
        try {
            if (!file.getCanonicalPath().contains(directoryBoundary.getCanonicalPath())) {
                throw new HazardousFileSystemNodeException("new system node is outside of the boundary. : " + this.directoryBoundary.getCanonicalPath());
            }
        } catch (IOException e) {
            throw new IOException("failed to verify new system node : " + file.getCanonicalPath());
        }
    }

    public LocalFile declareLocalFile(AccessKey accessKey) throws IOException {
        if (accessKey.isEmpty()) throw new HazardousFileSystemNodeException();
        this.verify(accessKey);
        return LocalFile.of(new File(accessKey.memorySignature()));
    }
}
