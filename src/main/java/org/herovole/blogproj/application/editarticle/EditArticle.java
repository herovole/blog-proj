package org.herovole.blogproj.application.editarticle;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.article.ArticleDatasource;
import org.herovole.blogproj.domain.article.ArticleEditingPage;
import org.herovole.blogproj.domain.article.ArticleTransactionalDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EditArticle {

    private static final Logger logger = LoggerFactory.getLogger(EditArticle.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final ArticleDatasource articleDatasource;
    private final ArticleTransactionalDatasource articleTransactionalDatasource;

    @Autowired
    public EditArticle(AppSessionFactory sessionFactory, ArticleDatasource articleDatasource, ArticleTransactionalDatasource articleTransactionalDatasource) {
        this.sessionFactory = sessionFactory;
        this.articleDatasource = articleDatasource;
        this.articleTransactionalDatasource = articleTransactionalDatasource;
    }

    public void process(EditArticleInput input) throws Exception {
        logger.info("interpreted post : {}", input);
        ArticleEditingPage article = input.getArticle();
        IntegerId articleId = article.getArticleId();

        ArticleEditingPage presentArticle = articleDatasource.findById(articleId);

        if (presentArticle.isEmpty()) {
            articleTransactionalDatasource.insert(article);
        } else {
            articleTransactionalDatasource.update(presentArticle, article);
        }
        logger.info("total transaction number : {}", articleTransactionalDatasource.amountOfCachedTransactions());

        try (AppSession session = sessionFactory.createSession()) {
            articleTransactionalDatasource.flush(session);
        }
        logger.info("job successful.");

    }
}
