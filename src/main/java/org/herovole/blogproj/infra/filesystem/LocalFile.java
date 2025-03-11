package org.herovole.blogproj.infra.filesystem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.domain.time.Timestamp;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalFile {

    public static LocalFile of(Path path, LocalFileSystem fs) throws IOException {
        fs.verify(path);
        if (Files.exists(path) && !Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS))
            throw new IOException(path + "is not a file");
        return new LocalFile(path);
    }

    public static LocalFile of(String path, LocalFileSystem fs) throws IOException {
        return of(Path.of(path), fs);
    }

    private final Path path;

    public Path toPath() {
        return this.path;
    }

    public boolean exists() {
        return Files.exists(path);
    }

    public Timestamp getLastModifiedTime() throws IOException {
        FileTime lastModifiedTime = Files.getLastModifiedTime(path);
        return Timestamp.valueOf(lastModifiedTime.toMillis());
    }

    public String getName() {
        return this.path.getFileName().toString();
    }

    public Stream<String> readLines() throws IOException {
        return Files.lines(path);
    }

    public void remove() throws IOException {
        Files.delete(path);
    }

    public void write(Document xmlDocument) throws IOException {
        if (!this.exists()) {
            Files.createFile(this.path);
        }
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            try (java.io.StringWriter writer = new java.io.StringWriter()) {
                transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));

                // Write XML content to file with UTF-8 encoding
                Files.writeString(this.path, writer.toString(),
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }

        } catch (TransformerException e) {
            throw new IOException(e);
        }
    }

}
