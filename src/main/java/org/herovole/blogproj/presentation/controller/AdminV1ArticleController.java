package org.herovole.blogproj.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.herovole.blogproj.application.article.editarticle.EditArticle;
import org.herovole.blogproj.application.article.editarticle.EditArticleInput;
import org.herovole.blogproj.application.article.findarticle.FindArticle;
import org.herovole.blogproj.application.article.findarticle.FindArticleInput;
import org.herovole.blogproj.application.article.searcharticles.SearchArticles;
import org.herovole.blogproj.application.article.searcharticles.SearchArticlesInput;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.herovole.blogproj.presentation.presenter.BasicPresenter;
import org.herovole.blogproj.presentation.presenter.FindArticlePresenter;
import org.herovole.blogproj.presentation.presenter.SearchArticlesPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/articles")
public class AdminV1ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(AdminV1ArticleController.class.getSimpleName());
    private final EditArticle editArticle;
    private final BasicPresenter editArticlePresenter;
    private final SearchArticles searchArticles;
    private final SearchArticlesPresenter searchArticlesPresenter;
    private final FindArticle findArticle;
    private final FindArticlePresenter findArticlePresenter;

    @Autowired
    AdminV1ArticleController(
            EditArticle editArticle,
            BasicPresenter editArticlePresenter, SearchArticles searchArticles,
            SearchArticlesPresenter searchArticlesPresenter, FindArticle findArticle,
            FindArticlePresenter findArticlePresenter) {
        this.editArticle = editArticle;
        this.editArticlePresenter = editArticlePresenter;
        this.searchArticles = searchArticles;
        this.searchArticlesPresenter = searchArticlesPresenter;
        this.findArticle = findArticle;
        this.findArticlePresenter = findArticlePresenter;
    }

    @PostMapping
    public ResponseEntity<String> postArticles(
            HttpServletRequest httpServletRequest,
            @RequestBody Map<String, String> request) {
        logger.info("Endpoint : articles (Post) ");
        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
        if (!servletRequest.getAdminUserFromAttribute().getRole().editsArticles()) {
            this.editArticlePresenter.setUseCaseErrorType(UseCaseErrorType.AUTH_INSUFFICIENT);
            return this.editArticlePresenter.buildResponseEntity();
        }

        try {
            FormContent formContent = FormContent.of(request);
            EditArticleInput input = EditArticleInput.fromPostContent(formContent);
            this.editArticle.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.editArticlePresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Error Application Process Exception : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.editArticlePresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }

        return editArticlePresenter.buildResponseEntity();
    }

    @GetMapping
    public ResponseEntity<String> searchArticles(
            @RequestParam Map<String, String> request) {
        logger.info("Endpoint : articles (Get) ");

        try {
            FormContent formContent = FormContent.of(request);
            SearchArticlesInput input = SearchArticlesInput.fromFormContent(formContent);
            this.searchArticles.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.searchArticlesPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.searchArticlesPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.searchArticlesPresenter.buildResponseEntity();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> findArticle(
            @PathVariable int id,
            @RequestParam Map<String, String> request) {
        logger.info("Endpoint : articles (Get) {}", id);

        try {
            FormContent formContent = FormContent.of(request);
            FindArticleInput input = FindArticleInput.of(id, formContent);
            this.findArticle.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.findArticlePresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.findArticlePresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.findArticlePresenter.buildResponseEntity();
    }
}
