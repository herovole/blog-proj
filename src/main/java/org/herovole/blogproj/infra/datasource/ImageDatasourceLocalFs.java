package org.herovole.blogproj.infra.datasource;

import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.accesskey.AccessKey;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.domain.image.ImageDatasource;
import org.herovole.blogproj.domain.image.Images;
import org.herovole.blogproj.infra.filesystem.ImageAsLocalFile;
import org.herovole.blogproj.infra.filesystem.ImageAsMultipartFile;
import org.herovole.blogproj.infra.filesystem.LocalDirectory;
import org.herovole.blogproj.infra.filesystem.LocalFile;
import org.herovole.blogproj.infra.filesystem.LocalFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ImageDatasourceLocalFs implements ImageDatasource {

    private final LocalDirectory parentDirectory;

    @Autowired
    public ImageDatasourceLocalFs(LocalDirectory parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    @Override
    public void persist(AccessKey key, Image image) throws IOException {
        if (!(image instanceof ImageAsMultipartFile)) throw new IllegalStateException("incompatible Image type.");
        MultipartFile imageFile = ((ImageAsMultipartFile) image).toMultipartFile();
        LocalFile destFile = parentDirectory.declareFile(key);
        if (destFile.exists()) throw new IOException("Declared file has already existed.");
        imageFile.transferTo(destFile.toPath());
    }

    @Override
    public void remove(AccessKey key) throws IOException {
        LocalFile file = parentDirectory.find(key);
        file.remove();
    }

    @Override
    public Images searchSortedByTimestampDesc(PagingRequest request) throws IOException {
        LocalFiles files = parentDirectory.getFiles().sortByTimestampDesc().get(request);
        return files.asImages();
    }

    @Override
    public Image findByName(AccessKey name) throws IOException {
        LocalFile file = parentDirectory.find(name);
        return ImageAsLocalFile.of(file);
    }

    @Override
    public int getTotal() throws IOException {
        return parentDirectory.getFiles().getTotal();
    }
}
