package org.herovole.blogproj.domain.image;

import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.accesskey.AccessKey;
import org.herovole.blogproj.infra.filesystem.LocalFiles;

import java.io.IOException;

public interface ImageDatasource {

    void persist(AccessKey key, Image image) throws IOException;

    LocalFiles searchSortedByTimestampDesc(PagingRequest request) throws IOException;
}
