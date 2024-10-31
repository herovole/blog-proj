package org.herovole.blogproj.domain.abstractdatasource;


import org.herovole.blogproj.domain.accesskey.AccessKey;

import java.io.IOException;

public interface EnvDatasource {
    void connect(AccessKey location) throws IOException;
}
