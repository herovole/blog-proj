package org.herovole.blogproj.infra.filesystem;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.Images;
import org.herovole.blogproj.domain.time.Timestamp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalFiles {

    public static LocalFiles of(LocalFile[] files) {
        return new LocalFiles(files);
    }

    private final LocalFile[] files;

    public LocalFiles sortByTimestampDesc() throws IOException {

        final List<LocalFileWithTimestamp> localFilesWithTimestamps = new ArrayList<>();
        for (LocalFile localFile : files) {
            Timestamp timestamp = localFile.getLastModifiedTime();
            localFilesWithTimestamps.add(new LocalFileWithTimestamp(localFile, timestamp));
        }

        LocalFile[] sortedFiles = localFilesWithTimestamps.stream()
                .sorted(Comparator.reverseOrder())
                .map(localFileWithTimestamp -> localFileWithTimestamp.file)
                .toArray(LocalFile[]::new);

        return LocalFiles.of(sortedFiles);
    }

    public Images asImages() {
        return Images.of(Stream.of(this.files).map(ImageAsLocalFile::of).toArray(Image[]::new));
    }

    public int getTotal() {
        return this.files.length;
    }

    // a work class to avoid IOException during sort
    @EqualsAndHashCode
    @RequiredArgsConstructor
    static
    class LocalFileWithTimestamp implements Comparable<LocalFileWithTimestamp> {
        @EqualsAndHashCode.Exclude
        private final LocalFile file;
        @EqualsAndHashCode.Include
        private final Timestamp timestamp;

        @Override
        public int compareTo(LocalFileWithTimestamp o) {
            return this.timestamp.compareTo(o.timestamp);
        }

    }

    public LocalFiles get(PagingRequest pagingRequest) {
        LocalFile[] part = Arrays.copyOfRange(this.files,
                (int) pagingRequest.getOffset(),
                Math.min((int) pagingRequest.getLastIndexZeroOrigin() + 1, this.files.length)
        );
        return LocalFiles.of(part);
    }

    public String[] getFileNames() {
        return Arrays.stream(this.files).map(LocalFile::getName).toArray(String[]::new);
    }
}
