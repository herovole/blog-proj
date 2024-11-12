package org.herovole.blogproj.domain.image;

import org.herovole.blogproj.domain.accesskey.AccessKey;

public interface ImageDatasource {

    void persist(AccessKey key, Image image);
}
