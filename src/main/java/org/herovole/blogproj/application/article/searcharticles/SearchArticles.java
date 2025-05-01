package org.herovole.blogproj.application.article.searcharticles;

import org.herovole.blogproj.application.GenericPresenter;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.IntegerId;
import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.article.Article;
import org.herovole.blogproj.domain.article.ArticleDatasource;
import org.herovole.blogproj.domain.article.ArticleListSearchOption;
import org.herovole.blogproj.domain.article.Articles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchArticles {

    private static final Logger logger = LoggerFactory.getLogger(SearchArticles.class.getSimpleName());

    private final ArticleDatasource articleDatasource;
    private final GenericPresenter<SearchArticlesOutput> presenter;

    @Autowired
    public SearchArticles(@Qualifier("articleDatasource") ArticleDatasource articleDatasource,
                          @Qualifier("searchArticlesPresenterRequest") GenericPresenter<SearchArticlesOutput> presenter) {
        this.articleDatasource = articleDatasource;
        this.presenter = presenter;
    }

    /**
     * Normal Process During Request
     * @param input : SearchArticlesInput
     * @throws ApplicationProcessException : ApplicationProcessException
     */
    public void process(SearchArticlesInput input) throws ApplicationProcessException {
        this.process(input, this.presenter);
    }

    /**
     * Both Normal Process and Post Construct Process
     * @param input : SearchArticlesInput
     * @param presenter : GenericPresenter<SearchArticlesOutput>
     * @throws ApplicationProcessException : ApplicationProcessException
     */
    public void process(SearchArticlesInput input, GenericPresenter<SearchArticlesOutput> presenter) throws ApplicationProcessException {
        logger.info("interpreted post : {}", input);
        ArticleListSearchOption searchOption = input.getSearchOption();
        if (!input.getRequiresAuth().isTrue() && !searchOption.getIsPublished().isTrue()) {
            presenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR)
                    .setMessage("Forbidden option")
                    .interruptProcess();
        }

        if (!searchOption.isValid()) {
            presenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR)
                    .setMessage("Too many search options")
                    .interruptProcess();
        }

        long articleNumber = articleDatasource.countByOptions(searchOption);
        IntegerIds ids = articleDatasource.searchByOptions(searchOption);
        List<Article> articles0 = new ArrayList<>();
        for (IntegerId id : ids) {
            Article article = articleDatasource.findByIdSimplified(id);
            articles0.add(article);
        }
        Articles articles = Articles.of(articles0);
        logger.info("job successful.");

        SearchArticlesOutput output = SearchArticlesOutput.builder()
                .articles(articles)
                .totalArticles(articleNumber)
                .build();
        presenter.setContent(output);
    }
}
