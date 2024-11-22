package org.herovole.blogproj.controller;

import org.herovole.blogproj.application.editarticle.EditArticle;
import org.herovole.blogproj.application.editarticle.EditArticleInput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.tag.CountryTagUnit;
import org.herovole.blogproj.domain.tag.TagUnit;
import org.herovole.blogproj.infra.jpa.entity.ATopicTag;
import org.herovole.blogproj.infra.jpa.entity.MCountry;
import org.herovole.blogproj.infra.jpa.repository.ATopicTagRepository;
import org.herovole.blogproj.infra.jpa.repository.MCountryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AdminV1Controller {

    private static final Logger logger = LoggerFactory.getLogger(AdminV1Controller.class.getSimpleName());

    @Autowired
    private EditArticle editArticle;

    @Autowired
    private MCountryRepository mCountryRepository;

    @Autowired
    private ATopicTagRepository aTopicTagRepository;

    @GetMapping("/countries")
    public ResponseEntity<String[]> countrySelectBox() {
        try {
            System.out.println("endpoint : select");
            System.out.println("/api/v1/countries");
            List<MCountry> candidates = mCountryRepository.findAll();
            String[] countries = candidates.stream().map(MCountry::toDomainObj).sorted().map(CountryTagUnit::toJsonString).toArray(String[]::new);
            return ResponseEntity.ok(countries);
        } catch (DomainInstanceGenerationException e) {
            System.out.println("error : 1");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String[]{"Error: " + e.getMessage()});
        } catch (Exception e) {
            System.out.println("error : 2" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new String[]{"Internal Server Error: " + e.getMessage()});
        }
    }

    @GetMapping("/topicTags")
    public ResponseEntity<String[]> topicTags() {
        try {
            System.out.println("endpoint : select");
            System.out.println("/api/v1/topicTags");
            List<ATopicTag> candidates = aTopicTagRepository.findAllTags();
            String[] topicTags = candidates.stream().map(ATopicTag::toDomainObj).sorted().map(TagUnit::toJsonString).toArray(String[]::new);
            return ResponseEntity.ok(topicTags);
        } catch (DomainInstanceGenerationException e) {
            System.out.println("error : 1");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String[]{"Error: " + e.getMessage()});
        } catch (Exception e) {
            System.out.println("error : 2");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new String[]{"Internal Server Error: " + e.getMessage()});
        }
    }

    @PostMapping("/articles")
    public ResponseEntity<String> articles(
            @RequestBody Map<String, String> request) {
        logger.info("Endpoint : articles (Post) ");
        System.out.println(request);

        try {
            PostContent postContent = PostContent.of(request);
            EditArticleInput input = EditArticleInput.fromPostContent(postContent);
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


}
