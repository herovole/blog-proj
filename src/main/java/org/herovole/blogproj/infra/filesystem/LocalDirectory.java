package org.herovole.blogproj.infra.filesystem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.accesskey.AccessKey;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDirectory {

    public static LocalDirectory of(AccessKey accessKey) throws IOException {
        Path path = LocalFileSystem.getInstance().declareAbsoluteNode(accessKey);
        if (Files.exists(path) && !Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS))
            throw new IOException(path + "is not a directory");
        return new LocalDirectory(path);
    }

    private final Path path;

    public LocalFile declareFile(AccessKey accessKey) throws IOException {
        Path child = LocalFileSystem.getInstance().declareChildNode(this.path, accessKey);
        return LocalFile.of(child);
    }

    public LocalFiles getFiles() throws IOException {
        List<LocalFile> localFiles = new ArrayList<>();
        try (Stream<Path> filePathStream = Files.walk(path)) {
            List<Path> paths = filePathStream.filter(Files::isRegularFile).toList();
            for (Path path : paths) {
                localFiles.add(LocalFile.of(path));
            }
            return LocalFiles.of(localFiles.toArray(new LocalFile[0]));
        }
    }

}
