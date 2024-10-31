package org.herovole.blogproj.infra.filesystem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalFile {
    private final File file;

    static LocalFile of(File file) {
        return new LocalFile(file);
    }

    public BufferedReader toBufferedReader() throws FileNotFoundException {
        return new BufferedReader(new FileReader(this.file));
    }
}
