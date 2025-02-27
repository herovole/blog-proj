package org.herovole.blogproj.domain.image;

import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.accesskey.AccessKey;

import java.io.IOException;

public interface ImageDatasource {

    String imageResourcePrefix();
    void persist(AccessKey key, Image image) throws IOException;
    void remove(AccessKey key) throws IOException;

    Images searchSortedByTimestampDesc(PagingRequest request) throws IOException;

    Image findByName(AccessKey name) throws IOException;

    int getTotal() throws IOException;
}
