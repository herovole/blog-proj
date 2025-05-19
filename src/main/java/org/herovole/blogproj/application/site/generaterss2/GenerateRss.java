package org.herovole.blogproj.application.site.generaterss2;

import jakarta.annotation.PostConstruct;
import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.article.searcharticles.SearchArticles;
import org.herovole.blogproj.application.article.searcharticles.SearchArticlesInput;
import org.herovole.blogproj.application.article.searcharticles.SearchArticlesOutput;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.GenericSwitch;
import org.herovole.blogproj.domain.article.Article;
import org.herovole.blogproj.domain.article.ArticleListSearchOption;
import org.herovole.blogproj.domain.article.ArticleTransactionalDatasource;
import org.herovole.blogproj.domain.article.Articles;
import org.herovole.blogproj.domain.meta.RssDestinations;
import org.herovole.blogproj.domain.meta.RssType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GenerateRss implements RssDestinations {

    private static final Logger logger = LoggerFactory.getLogger(GenerateRss.class.getSimpleName());
    private static final int NUMBER_ADOPTED = 12;

    private final SearchArticles searchArticles;
    private final ArticleTransactionalDatasource articleTransactionalDatasourceRss20Feed;
    private final ArticleTransactionalDatasource articleTransactionalDatasourceRss20;
    private final ArticleTransactionalDatasource articleTransactionalDatasourceRss10;
    private final ArticleTransactionalDatasource articleTransactionalDatasourceRss20German;
    private final ArticleTransactionalDatasource articleTransactionalDatasourceRss10German;
    private final GenericPresenter<SearchArticlesOutput> searchArticlesPresenter;
    private final GenericPresenter<Object> presenter;

    @Autowired
    public GenerateRss(
            SearchArticles searchArticles,
            @Qualifier("searchArticlesPresenterPlain") GenericPresenter<SearchArticlesOutput> searchArticlesPresenter,
            @Qualifier("articleTransactionalDatasourceRss2Feed") ArticleTransactionalDatasource articleTransactionalDatasourceRss20Feed,
            @Qualifier("articleTransactionalDatasourceRss2") ArticleTransactionalDatasource articleTransactionalDatasourceRss20,
            @Qualifier("articleTransactionalDatasourceRss1") ArticleTransactionalDatasource articleTransactionalDatasourceRss10,
            @Qualifier("articleTransactionalDatasourceRss2German") ArticleTransactionalDatasource articleTransactionalDatasourceRss20German,
            @Qualifier("articleTransactionalDatasourceRss1German") ArticleTransactionalDatasource articleTransactionalDatasourceRss10German,
            GenericPresenter<Object> presenter) {
        this.searchArticles = searchArticles;
        this.searchArticlesPresenter = searchArticlesPresenter;
        this.articleTransactionalDatasourceRss20Feed = articleTransactionalDatasourceRss20Feed;
        this.articleTransactionalDatasourceRss20 = articleTransactionalDatasourceRss20;
        this.articleTransactionalDatasourceRss10 = articleTransactionalDatasourceRss10;
        this.articleTransactionalDatasourceRss20German = articleTransactionalDatasourceRss20German;
        this.articleTransactionalDatasourceRss10German = articleTransactionalDatasourceRss10German;
        this.presenter = presenter;
    }

    @PostConstruct
    public void processToGenerateRssUponDeployment() throws ApplicationProcessException {
        logger.info("Start initial RSS generation.");
        try {
            for (RssType rssType : RssType.valuesForGeneratingUponDeployment()) {
                GenerateRssInput input = new GenerateRssInput(rssType);
                this.process(input);
            }
        } catch (ApplicationProcessException e) {
            logger.error("Error initial RSS generation", e);
            this.presenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR)
                    .interruptProcess();
        }
    }

    public void process(GenerateRssInput input) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);

        final RssType rssType = input.getRssType();
        final ArticleListSearchOption[] searchOptions = rssType.searchOptions();
        Articles articles = Articles.empty();

        for (ArticleListSearchOption searchOption : searchOptions) {
            SearchArticlesInput searchArticlesInput
                    = SearchArticlesInput.builder()
                    .searchOption(searchOption)
                    .requiresAuth(GenericSwitch.positive())
                    .build();
            this.searchArticles.process(searchArticlesInput, this.searchArticlesPresenter);
            SearchArticlesOutput searchArticlesOutput = this.searchArticlesPresenter.getContent();
            logger.info("fetched article samples: {}", searchArticlesOutput.getTotalArticles());

            Articles articlesOfThisSearch = searchArticlesOutput.getArticles();
            articles = articles.append(articlesOfThisSearch);
        }
        articles = articles.sliceFirst(NUMBER_ADOPTED);

        for(Article article : articles.stream().toList()) {
            logger.info("Application " + article.getArticleId().letterSignature() + " " + article.getRegistrationTimestamp().letterSignatureFrontendDisplay());
        }

        final ArticleTransactionalDatasource articleTransactionalDatasource = rssType.getCorrespondingDatasource(this);
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

    @Override
    public ArticleTransactionalDatasource getRss20Destination() {
        return this.articleTransactionalDatasourceRss20;
    }

    @Override
    public ArticleTransactionalDatasource getRss10Destination() {
        return this.articleTransactionalDatasourceRss10;
    }

    @Override
    public ArticleTransactionalDatasource getRssFeedDestination() {
        return this.articleTransactionalDatasourceRss20Feed;
    }

    @Override
    public ArticleTransactionalDatasource getGermanRss20Destination() {
        return this.articleTransactionalDatasourceRss20German;
    }

    @Override
    public ArticleTransactionalDatasource getGermanRss10Destination() {
        return this.articleTransactionalDatasourceRss10German;
    }
}
