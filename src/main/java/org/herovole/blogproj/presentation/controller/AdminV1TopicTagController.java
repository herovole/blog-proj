package org.herovole.blogproj.presentation.controller;

import com.google.gson.Gson;
import org.herovole.blogproj.application.tag.edittopictags.EditTopicTags;
import org.herovole.blogproj.application.tag.edittopictags.EditTopicTagsInput;
import org.herovole.blogproj.application.tag.searchcountrytags.SearchCountryTags;
import org.herovole.blogproj.application.tag.searchcountrytags.SearchCountryTagsInput;
import org.herovole.blogproj.application.tag.searchcountrytags.SearchCountryTagsOutput;
import org.herovole.blogproj.application.tag.searchtopictags.SearchTopicTags;
import org.herovole.blogproj.application.tag.searchtopictags.SearchTopicTagsInput;
import org.herovole.blogproj.application.tag.searchtopictags.SearchTopicTagsOutput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AdminV1TopicTagController {
    private static final Logger logger = LoggerFactory.getLogger(AdminV1TopicTagController.class.getSimpleName());

    private final SearchCountryTags searchCountryTags;
    private final SearchTopicTags searchTopicTags;

    private final EditTopicTags editTopicTags;

    @Autowired
    public AdminV1TopicTagController(
            SearchCountryTags searchCountryTags,
            SearchTopicTags searchTopicTags,
            EditTopicTags editTopicTags) {
        this.searchCountryTags = searchCountryTags;
        this.searchTopicTags = searchTopicTags;
        this.editTopicTags = editTopicTags;
    }

    //?page=...&itemsPerPage=...&isDetailed=...
    @GetMapping("/countries")
    public ResponseEntity<String> countrySelectBox(
            @RequestParam Map<String, String> request
    ) {
        logger.info("Endpoint : countryTags (Get) ");
        try {
            FormContent formContent = FormContent.of(request);
            SearchCountryTagsInput input = SearchCountryTagsInput.fromFormContent(formContent);
            SearchCountryTagsOutput output = this.searchCountryTags.process(input);
            return ResponseEntity.ok(new Gson().toJson(output.toJsonRecord()));
        } catch (DomainInstanceGenerationException e) {
            logger.error("User Error ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Server Error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //?page=...&itemsPerPage=...&isDetailed=...
    @GetMapping("/topicTags")
    public ResponseEntity<String> searchTopicTags(
            @RequestParam Map<String, String> request) {
        logger.info("Endpoint : topicTags (Get) ");
        System.out.println(request);
        try {
            FormContent formContent = FormContent.of(request);
            SearchTopicTagsInput input = SearchTopicTagsInput.fromFormContent(formContent);
            SearchTopicTagsOutput output = this.searchTopicTags.process(input);
            return ResponseEntity.ok(new Gson().toJson(output.toJsonRecord()));
        } catch (DomainInstanceGenerationException e) {
            logger.error("User Error ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Server Error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/topicTags")
    public ResponseEntity<String> editTopicTags(
            @RequestBody Map<String, String> request) {
        logger.info("Endpoint : articles (Post) ");
        System.out.println(request);

        try {
            FormContent formContent = FormContent.of(request);
            EditTopicTagsInput input = EditTopicTagsInput.fromFormContent(formContent);
            this.editTopicTags.process(input);
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
