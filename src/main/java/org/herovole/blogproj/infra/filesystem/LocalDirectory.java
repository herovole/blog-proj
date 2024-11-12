package org.herovole.blogproj.infra.filesystem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.DomainUnexpectedArgumentException;

import java.io.File;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDirectory {

    static LocalDirectory of(File file) {
        if (file.exists() && !file.isDirectory()) throw new DomainUnexpectedArgumentException();
        return new LocalDirectory(file);
    }

    private final File file;

}
