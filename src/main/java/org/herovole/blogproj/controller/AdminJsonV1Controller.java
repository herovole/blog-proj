package org.herovole.blogproj.controller;

import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.tag.CountryTagUnit;
import org.herovole.blogproj.domain.tag.TagUnit;
import org.herovole.blogproj.infra.jpa.entity.ATag;
import org.herovole.blogproj.infra.jpa.entity.MCountry;
import org.herovole.blogproj.infra.jpa.repository.ATagRepository;
import org.herovole.blogproj.infra.jpa.repository.MCountryRepository;
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
public class AdminJsonV1Controller {

    @Autowired
    private MCountryRepository mCountryRepository;

    @Autowired
    private ATagRepository aTagRepository;


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
            List<ATag> candidates = aTagRepository.findAllTags();
            String[] topicTags = candidates.stream().map(ATag::toDomainObj).sorted().map(TagUnit::toJsonString).toArray(String[]::new);
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
    public String articles(
            @RequestBody Map<String, String> request) {
        System.out.println("endpoint : upsert");
        System.out.println("/api/v1/articles");

        for (Map.Entry<String, String> e : request.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
        return "";
    }


}
