package org.herovole.blogproj.infra.filesystem;


import org.herovole.blogproj.domain.accesskey.AccessKey;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalFileSystem {
    private static LocalFileSystem instance;

    public static LocalFileSystem getInstance() {
        return instance;
    }

    private final FileSystem fileSystem;
    private final Path directoryBoundary;

    public static void buildSingleton(String boundary) throws HazardousFileSystemNodeException {
        if (instance != null) throw new HazardousFileSystemNodeException("forbidden to overwrite boundary");
        instance = new LocalFileSystem(boundary);
    }

    private LocalFileSystem(String boundary) throws HazardousFileSystemNodeException {
        if (boundary == null || boundary.isEmpty()) throw new HazardousFileSystemNodeException();
        Path directoryBoundaryDeclared = Paths.get(boundary);
        if (Files.isDirectory(directoryBoundaryDeclared)) {
            this.directoryBoundary = directoryBoundaryDeclared;
        } else {
            throw new HazardousFileSystemNodeException();
        }
        this.fileSystem = FileSystems.getDefault();
    }

    private void verify(AccessKey accessKey) throws IOException {
        this.verify(Paths.get(accessKey.memorySignature()));
    }

    private void verify(Path path) throws IOException {
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

    public Path declareAbsoluteNode(AccessKey accessKey) throws IOException {
        if (accessKey.isEmpty()) throw new HazardousFileSystemNodeException();
        this.verify(accessKey);
        return this.fileSystem.getPath(accessKey.memorySignature());
    }

    public Path declareChildNode(Path parent, AccessKey accessKey) throws IOException {
        if (accessKey.isEmpty()) throw new HazardousFileSystemNodeException();
        Path childNode = parent.resolve(accessKey.memorySignature());
        this.verify(childNode);
        return childNode;
    }

}
