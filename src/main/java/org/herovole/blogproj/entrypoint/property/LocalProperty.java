package org.herovole.blogproj.entrypoint.property;

import lombok.Data;
import org.herovole.blogproj.infra.filesystem.HazardousFileSystemNodeException;
import org.herovole.blogproj.infra.filesystem.LocalFileSystemNodeFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.config.local")
public class LocalProperty {
    private String boundary;

    public void initializeLocalFileSystemNodeFactory() throws HazardousFileSystemNodeException {
        LocalFileSystemNodeFactory.initialize(boundary);
    }
}
