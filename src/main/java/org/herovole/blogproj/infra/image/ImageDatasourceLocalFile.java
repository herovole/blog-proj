package org.herovole.blogproj.infra.image;

import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.accesskey.AccessKey;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.infra.filesystem.LocalDirectory;
import org.herovole.blogproj.infra.filesystem.LocalFile;
import org.herovole.blogproj.infra.filesystem.LocalFiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageDatasourceLocalFile implements ImageDatasource {

    private static LocalDirectory parentDirectory;

    @Override
    public void persist(AccessKey key, Image image) throws IOException {
        MultipartFile imageFile = image.toMultipartFile();
        LocalFile destFile = parentDirectory.declareFile(key);
        if (destFile.exists()) throw new IOException("Declared file has already existed.");
        imageFile.transferTo(destFile.toPath());
    }

    @Override
    public LocalFiles searchSortedByTimestampDesc(PagingRequest request) throws IOException {
        return parentDirectory.getFiles().sortByTimestampDesc().get(request);
    }
}
