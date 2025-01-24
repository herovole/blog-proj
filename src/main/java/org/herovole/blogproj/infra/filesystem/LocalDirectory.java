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

    public static LocalDirectory of(Path path, LocalFileSystem fs) throws IOException {
        fs.verify(path);
        return new LocalDirectory(path, fs);
    }

    public static LocalDirectory of(String path, LocalFileSystem fs) throws IOException {
        return of(Path.of(path), fs);
    }

    private final Path path;
    private final LocalFileSystem fs;

    public LocalDirectory declareDirectory(AccessKey accessKey) throws IOException {
        Path childNode = path.resolve(accessKey.memorySignature());
        if (Files.exists(childNode) && !Files.isDirectory(childNode, LinkOption.NOFOLLOW_LINKS))
            throw new IOException(childNode + "is not a directory");
        return LocalDirectory.of(childNode, fs);
    }

    public LocalFile declareFile(AccessKey accessKey) throws IOException {
        Path childNode = path.resolve(accessKey.memorySignature());
        return LocalFile.of(childNode, fs);
    }

    public LocalFiles getFiles() throws IOException {
        List<LocalFile> localFiles = new ArrayList<>();
        try (Stream<Path> filePathStream = Files.walk(path)) {
            List<Path> paths = filePathStream.filter(Files::isRegularFile).toList();
            for (Path path : paths) {
                localFiles.add(LocalFile.of(path, fs));
            }
            return LocalFiles.of(localFiles.toArray(new LocalFile[0]));
        }
    }

    public LocalFile find(AccessKey accessKey) throws IOException {
        try (Stream<Path> filePathStream = Files.walk(path)) {
            Path one = filePathStream.filter(Files::isRegularFile).filter(e -> accessKey.correspondsWith(e.getFileName().toString())).findAny().orElse(null);
            return one == null ? null : LocalFile.of(one, fs);
        }
    }

}
