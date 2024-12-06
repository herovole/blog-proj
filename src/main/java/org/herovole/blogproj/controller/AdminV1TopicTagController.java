package org.herovole.blogproj.controller;

import com.google.gson.Gson;
import org.herovole.blogproj.application.searchcountrytags.SearchCountryTags;
import org.herovole.blogproj.application.searchcountrytags.SearchCountryTagsInput;
import org.herovole.blogproj.application.searchcountrytags.SearchCountryTagsOutput;
import org.herovole.blogproj.application.searchtopictags.SearchTopicTags;
import org.herovole.blogproj.application.searchtopictags.SearchTopicTagsInput;
import org.herovole.blogproj.application.searchtopictags.SearchTopicTagsOutput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.PostContent;
import org.herovole.blogproj.domain.tag.country.CountryTagUnit;
import org.herovole.blogproj.infra.jpa.entity.MCountry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AdminV1TopicTagController {
    private static final Logger logger = LoggerFactory.getLogger(AdminV1TopicTagController.class.getSimpleName());

    private final SearchCountryTags searchCountryTags;
    private final SearchTopicTags searchTopicTags;

    @Autowired
    public AdminV1TopicTagController(
            SearchCountryTags searchCountryTags,
            SearchTopicTags searchTopicTags) {
        this.searchCountryTags = searchCountryTags;
        this.searchTopicTags = searchTopicTags;
    }

    //?page=...&itemsPerPage=...&isDetailed=...
    @GetMapping("/countries")
    public ResponseEntity<String> countrySelectBox(
            @RequestParam Map<String, String> request
    ) {
        logger.info("Endpoint : countryTags (Get) ");
        try {
            PostContent postContent = PostContent.of(request);
            SearchCountryTagsInput input = SearchCountryTagsInput.fromPostContent(postContent);
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

        try {
            PostContent postContent = PostContent.of(request);
            SearchTopicTagsInput input = SearchTopicTagsInput.fromPostContent(postContent);
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

}
