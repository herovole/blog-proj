package org.herovole.blogproj.application.article.searcharticles;

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

    @Autowired
    public SearchArticles(@Qualifier("articleDatasource") ArticleDatasource articleDatasource) {
        this.articleDatasource = articleDatasource;
    }

    public SearchArticlesOutput process(SearchArticlesInput input) throws Exception {
        logger.info("interpreted post : {}", input);
        ArticleListSearchOption searchOption = input.getSearchOption();
        long articleNumber = articleDatasource.countByOptions(searchOption);
        IntegerIds ids = articleDatasource.searchByOptions(searchOption);
        List<Article> articles0 = new ArrayList<>();
        for (IntegerId id : ids) {
            Article article = articleDatasource.findByIdSimplified(id);
            articles0.add(article);
        }
        Articles articles = Articles.of(articles0);
        logger.info("job successful.");

        return SearchArticlesOutput.builder()
                .articles(articles)
                .totalArticles(articleNumber)
                .build();
    }
}
