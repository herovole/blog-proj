package org.herovole.blogproj.application.article.findarticle;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.article.Article;
import org.herovole.blogproj.domain.article.ArticleDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FindArticle {

    private static final Logger logger = LoggerFactory.getLogger(FindArticle.class.getSimpleName());

    private final ArticleDatasource articleDatasource;
    private final GenericPresenter<Article> presenter;

    @Autowired
    public FindArticle(@Qualifier("articleDatasource") ArticleDatasource articleDatasource, GenericPresenter<Article> presenter) {
        this.articleDatasource = articleDatasource;
        this.presenter = presenter;
    }

    public void process(FindArticleInput input) {
        logger.info("interpreted post : {}", input);
        IntegerId articleId = input.getArticleId();

        logger.info("retrieving article : {}", articleId);
        Article article = articleDatasource.findById(articleId);

        logger.info("masking private info...");
        if (input.getIsForPublic().isTrue()) {
            article = article
                    .filterOutHiddenComments()
                    .maskPrivateItems();
        }

        logger.info("sorting comments...");
        article = article.sortComments();

        logger.info("job successful.");
        this.presenter.setContent(article);
    }
}
