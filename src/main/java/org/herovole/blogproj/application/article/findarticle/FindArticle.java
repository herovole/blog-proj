package org.herovole.blogproj.application.article.findarticle;

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

    @Autowired
    public FindArticle(@Qualifier("articleDatasource") ArticleDatasource articleDatasource) {
        this.articleDatasource = articleDatasource;
    }

    public FindArticleOutput process(FindArticleInput input) {
        logger.info("interpreted post : {}", input);
        IntegerId articleId = input.getArticleId();
        Article article = articleDatasource.findById(articleId);
        logger.info("job successful.");

        return new FindArticleOutput(article);
    }
}
