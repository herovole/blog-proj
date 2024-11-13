package org.herovole.blogproj.controller;

import org.herovole.blogproj.domain.tag.CountryTagUnit;
import org.herovole.blogproj.domain.tag.TagUnit;
import org.herovole.blogproj.infra.jpa.entity.ATag;
import org.herovole.blogproj.infra.jpa.entity.MCountry;
import org.herovole.blogproj.infra.jpa.repository.ATagRepository;
import org.herovole.blogproj.infra.jpa.repository.MCountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<String> countrySelectBox() {
        System.out.println("endpoint : select");
        System.out.println("/api/v1/countries");
        List<MCountry> candidates = mCountryRepository.findAll();
        return candidates.stream().map(MCountry::toDomainObj).sorted().map(CountryTagUnit::toJsonString).toList();
    }

    @GetMapping("/topicTags")
    public List<String> topicTags() {
        System.out.println("endpoint : select");
        System.out.println("/api/v1/topicTags");
        List<ATag> candidates = aTagRepository.findAllTags();
        return candidates.stream().map(ATag::toDomainObj).sorted().map(TagUnit::toJsonString).toList();
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
