package org.herovole.blogproj.infra.filesystem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.time.Timestamp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalFile {

    public static LocalFile of(Path path, LocalFileSystem fs) throws IOException {
        fs.verify(path);
        if (Files.exists(path) && !Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS))
            throw new IOException(path + "is not a file");
        return new LocalFile(path);
    }

    public static LocalFile of(String path, LocalFileSystem fs) throws IOException {
        return of(Path.of(path), fs);
    }

    private final Path path;

    public Path toPath() {
        return this.path;
    }

    public boolean exists() {
        return Files.exists(path);
    }

    public Timestamp getLastModifiedTime() throws IOException {
        FileTime lastModifiedTime = Files.getLastModifiedTime(path);
        return Timestamp.valueOf(lastModifiedTime.toMillis());
    }

    public String getName() {
        return this.path.getFileName().toString();
    }

    public Stream<String> readLines() throws IOException {
        return Files.lines(path);
    }

}
