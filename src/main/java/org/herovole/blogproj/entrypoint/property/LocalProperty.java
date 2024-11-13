package org.herovole.blogproj.entrypoint.property;

import org.herovole.blogproj.infra.filesystem.HazardousFileSystemNodeException;
import org.herovole.blogproj.infra.filesystem.LocalFileSystem;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.config.local")
public class LocalProperty {
    private String boundary;

    public void setUpLocalFileSystemReader() throws HazardousFileSystemNodeException {
        LocalFileSystem.buildSingleton(boundary);
    }
}
