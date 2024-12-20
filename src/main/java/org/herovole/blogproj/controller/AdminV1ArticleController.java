package org.herovole.blogproj.controller;

import com.google.gson.Gson;
import org.herovole.blogproj.application.article.editarticle.EditArticle;
import org.herovole.blogproj.application.article.editarticle.EditArticleInput;
import org.herovole.blogproj.application.article.findarticle.FindArticle;
import org.herovole.blogproj.application.article.findarticle.FindArticleInput;
import org.herovole.blogproj.application.article.findarticle.FindArticleOutput;
import org.herovole.blogproj.application.article.searcharticles.SearchArticles;
import org.herovole.blogproj.application.article.searcharticles.SearchArticlesInput;
import org.herovole.blogproj.application.article.searcharticles.SearchArticlesOutput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final SearchArticles searchArticles;
    private final FindArticle findArticle;

    @Autowired
    AdminV1ArticleController(
            EditArticle editArticle,
            SearchArticles searchArticles,
            FindArticle findArticle
    ) {
        this.editArticle = editArticle;
        this.searchArticles = searchArticles;
        this.findArticle = findArticle;
    }

    @PostMapping
    public ResponseEntity<String> postArticles(
            @RequestBody Map<String, String> request) {
        logger.info("Endpoint : articles (Post) ");
        System.out.println(request);

        try {
            FormContent formContent = FormContent.of(request);
            EditArticleInput input = EditArticleInput.fromPostContent(formContent);
            this.editArticle.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }

        return ResponseEntity.ok("");
    }

    @GetMapping
    public ResponseEntity<String> searchArticles(
            @RequestParam Map<String, String> request) {
        logger.info("Endpoint : articles (Get) ");
        System.out.println(request);

        try {
            FormContent formContent = FormContent.of(request);
            SearchArticlesInput input = SearchArticlesInput.fromPostContent(formContent);
            SearchArticlesOutput output = this.searchArticles.process(input);
            return ResponseEntity.ok(new Gson().toJson(output.toJsonRecord()));
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<String> findArticle(
            @PathVariable int id) {
        logger.info("Endpoint : articles (Get) {}", id);

        try {
            FindArticleInput input = FindArticleInput.of(id);
            FindArticleOutput output = this.findArticle.process(input);
            return ResponseEntity.ok(new Gson().toJson(output.toJsonRecord()));
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
