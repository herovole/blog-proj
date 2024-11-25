package org.herovole.blogproj.application.searcharticles;

import org.herovole.blogproj.domain.IntegerIds;
import org.herovole.blogproj.domain.article.ArticleDatasource;
import org.herovole.blogproj.domain.article.ArticleListSearchOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SearchArticles {

    private static final Logger logger = LoggerFactory.getLogger(SearchArticles.class.getSimpleName());

    private final ArticleDatasource articleDatasource;

    @Autowired
    public SearchArticles(@Qualifier("articleDatasource") ArticleDatasource articleDatasource) {
        this.articleDatasource = articleDatasource;
    }

    public void process(SearchArticlesInput input) throws Exception {
        logger.info("interpreted post : {}", input);
        ArticleListSearchOption searchOption = input.getSearchOption();
        long articleNumber = articleDatasource.countByOptions(searchOption);
        IntegerIds ids = articleDatasource.searchByOptions(searchOption);
        logger.info("job successful.");

    }
}
