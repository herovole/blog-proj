package org.herovole.blogproj.application;

import java.io.IOException;

public interface AppSessionFactory {
    AppSession createSession();

    void connect() throws IOException;
}
