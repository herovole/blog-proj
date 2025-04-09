package org.herovole.blogproj.application.site.generaterss2;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.article.searcharticles.SearchArticles;
import org.herovole.blogproj.application.article.searcharticles.SearchArticlesInput;
import org.herovole.blogproj.application.article.searcharticles.SearchArticlesOutput;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.SearchKeywords;
import org.herovole.blogproj.domain.abstractdatasource.PagingRequest;
import org.herovole.blogproj.domain.article.ArticleListSearchOption;
import org.herovole.blogproj.domain.article.ArticleTransactionalDatasource;
import org.herovole.blogproj.domain.article.Articles;
import org.herovole.blogproj.domain.time.Date;
import org.herovole.blogproj.domain.time.RealDateRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GenerateRss {

    private static final Logger logger = LoggerFactory.getLogger(GenerateRss.class.getSimpleName());

    private final SearchArticles searchArticles;
    private final ArticleTransactionalDatasource articleTransactionalDatasourceRss20;
    private final ArticleTransactionalDatasource articleTransactionalDatasourceRss10;
    private final GenericPresenter<SearchArticlesOutput> searchArticlesPresenter;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public GenerateRss(
            SearchArticles searchArticles,
            GenericPresenter<SearchArticlesOutput> searchArticlesPresenter,
            @Qualifier("articleTransactionalDatasourceRss2") ArticleTransactionalDatasource articleTransactionalDatasourceRss20,
            @Qualifier("articleTransactionalDatasourceRss1") ArticleTransactionalDatasource articleTransactionalDatasourceRss10,
            GenericPresenter<Object> presenter) {
        this.searchArticles = searchArticles;
        this.searchArticlesPresenter = searchArticlesPresenter;
        this.articleTransactionalDatasourceRss20 = articleTransactionalDatasourceRss20;
        this.articleTransactionalDatasourceRss10 = articleTransactionalDatasourceRss10;
        this.presenter = presenter;
    }

    public void process(GenerateRssInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);

        ArticleListSearchOption searchOption =
                ArticleListSearchOption.builder()
                        .isPublished(GenericSwitch.positive())
                        .pagingRequest(PagingRequest.of(1, 12))
                        .keywords(SearchKeywords.empty())
                        .dateRange(RealDateRange.of(Date.today().shift(-14), Date.today()))
                        .build();

        SearchArticlesInput searchArticlesInput
                = SearchArticlesInput.builder()
                .searchOption(searchOption)
                .requiresAuth(GenericSwitch.positive())
                .build();

        this.searchArticles.process(searchArticlesInput);
        SearchArticlesOutput searchArticlesOutput = this.searchArticlesPresenter.getContent();
        logger.info("fetched article samples: {}", searchArticlesOutput.getTotalArticles());

        Articles articles = searchArticlesOutput.getArticles();

        ArticleTransactionalDatasource articleTransactionalDatasource
                = input.getVersion().intMemorySignature() == 1 ?
                articleTransactionalDatasourceRss10 :
                articleTransactionalDatasourceRss20;

        articles.stream().forEach(articleTransactionalDatasource::insert);

        logger.info("total transaction number : {}", articleTransactionalDatasource.amountOfCachedTransactions());
        try {
            articleTransactionalDatasource.flush(null);
        } catch (Exception e) {
            logger.error("transaction failure", e);
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR)
                    .interruptProcess();
        }
        logger.info("job successful.");

    }
}
