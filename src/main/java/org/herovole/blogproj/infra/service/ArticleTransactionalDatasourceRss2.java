package org.herovole.blogproj.infra.service;

import lombok.RequiredArgsConstructor;
import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.domain.SiteInformation;
import org.herovole.blogproj.domain.article.Article;
import org.herovole.blogproj.domain.article.ArticleTransactionalDatasource;
import org.herovole.blogproj.domain.article.RealArticleSimplified;
import org.herovole.blogproj.domain.time.Timestamp;
import org.herovole.blogproj.infra.filesystem.LocalFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
public class ArticleTransactionalDatasourceRss2 implements ArticleTransactionalDatasource {

    private static final Queue<Article> articles = new ConcurrentLinkedQueue<>();
    private static final String AMAZON_S3_KEY_XML = "system/rss.xml";
    private final LocalFile rssXml;
    private final SiteInformation siteInformation;
    private final S3Client s3Client;
    private final String bucketName;

    @Override
    public int amountOfCachedTransactions() {
        return articles.size();
    }

    @Override
    public void flush(AppSession session) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            // Root element
            Element rss = document.createElement("rss");
            rss.setAttribute("version", "2.0");
            rss.setAttribute("xmlns:atom", "http://www.w3.org/2005/Atom");
            document.appendChild(rss);

            Element channel = document.createElement("channel");
            rss.appendChild(channel);

            Element title = document.createElement("title");
            title.appendChild(document.createTextNode(this.siteInformation.getSiteNameJp()));
            Element link = document.createElement("link");
            link.appendChild(document.createTextNode(this.siteInformation.getSiteTopUrl().memorySignature()));
            Element description = document.createElement("description");
            description.appendChild(document.createTextNode(this.siteInformation.getSiteDescription()));
            Element language = document.createElement("language");
            language.appendChild(document.createTextNode(this.siteInformation.getSiteLanguage()));
            Element copyright = document.createElement("copyright");
            copyright.appendChild(document.createTextNode(this.siteInformation.getSiteCopyright()));
            Element lastBuildDate = document.createElement("lastBuildDate");
            lastBuildDate.appendChild(document.createTextNode(Timestamp.now().rss20PubDate()));
            Element generator = document.createElement("generator");
            generator.appendChild(document.createTextNode("Java Spring React"));

            channel.appendChild(title);
            channel.appendChild(link);
            channel.appendChild(description);
            channel.appendChild(language);
            channel.appendChild(copyright);
            channel.appendChild(lastBuildDate);
            channel.appendChild(generator);

            while (!articles.isEmpty()) {
                Article article = articles.poll();
                Element item = new ArticleXml(siteInformation, article).toElement(document);
                channel.appendChild(item);
            }
            rssXml.write(document);

            // Transfer XML to Amazon S3.
            RequestBody requestBody = RequestBody.fromFile(rssXml.toPath());
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(AMAZON_S3_KEY_XML)
                    .build();
            s3Client.putObject(putObjectRequest, requestBody);
        } catch (IOException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void insert(Article article) {
        articles.offer(article);
    }

    @Override
    public void update(Article before, Article after) {
        throw new UnsupportedOperationException();
    }

    @RequiredArgsConstructor
    static class ArticleXml {
        private final SiteInformation siteInformation;
        private final Article article;

        Element toElement(Document document) {
            if (!(article instanceof RealArticleSimplified article1)) throw new IllegalStateException();

            Element item = document.createElement("item");

            Element title = document.createElement("title");
            title.appendChild(document.createTextNode(article1.getTitle().memorySignature()));

            Element link = document.createElement("link");
            link.appendChild(document.createTextNode(siteInformation.getArticlesUrl().memorySignature() + "/" + article1.getArticleId().letterSignature()));

            Element description = document.createElement("description");
            description.appendChild(document.createTextNode(article1.getText().memorySignature()));

            Element category = document.createElement("category");

            Element guid = document.createElement("guid");
            guid.appendChild(document.createTextNode(siteInformation.getArticlesUrl().memorySignature() + "/" + article1.getArticleId().letterSignature()));

            Element pubDate = document.createElement("pubDate");
            pubDate.appendChild(document.createTextNode(article1.getRegistrationTimestamp().rss20PubDate()));

            item.appendChild(title);
            item.appendChild(link);
            item.appendChild(description);
            item.appendChild(guid);
            item.appendChild(pubDate);
            return item;
        }
    }
}
