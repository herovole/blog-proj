package org.herovole.blogproj.application.article.editarticle;

import org.herovole.blogproj.application.AppSession;
import org.herovole.blogproj.application.AppSessionFactory;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.article.Article;
import org.herovole.blogproj.domain.article.ArticleDatasource;
import org.herovole.blogproj.domain.article.ArticleTransactionalDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EditArticle {

    private static final Logger logger = LoggerFactory.getLogger(EditArticle.class.getSimpleName());

    private final AppSessionFactory sessionFactory;
    private final ArticleDatasource articleDatasource;
    private final ArticleTransactionalDatasource articleTransactionalDatasource;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public EditArticle(AppSessionFactory sessionFactory,
                       @Qualifier("articleDatasource") ArticleDatasource articleDatasource,
                       @Qualifier("articleTransactionalDatasourceMySql") ArticleTransactionalDatasource articleTransactionalDatasource,
                       GenericPresenter<Object> presenter) {
        this.sessionFactory = sessionFactory;
        this.articleDatasource = articleDatasource;
        this.articleTransactionalDatasource = articleTransactionalDatasource;
        this.presenter = presenter;
    }

    public void process(EditArticleInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);
        Article article = input.getArticle();
        IntegerId articleId = article.getArticleId();

        if (articleId.isEmpty()) {
            articleTransactionalDatasource.insert(article);
        } else {
            Article presentArticle = articleDatasource.findById(articleId);
            articleTransactionalDatasource.update(presentArticle, article);
        }
        logger.info("total transaction number : {}", articleTransactionalDatasource.amountOfCachedTransactions());

        try (AppSession session = sessionFactory.createSession()) {
            articleTransactionalDatasource.flush(session);
            session.flushAndClear();
            session.commit();
        } catch (Exception e) {
            logger.error("transaction failure", e);
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR)
                    .interruptProcess();
        }
        logger.info("job successful.");

    }
}
