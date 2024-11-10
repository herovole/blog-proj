package org.herovole.blogproj.controller;

import org.herovole.blogproj.domain.tag.TagUnit;
import org.herovole.blogproj.entrypoint.property.LocalProperty;
import org.herovole.blogproj.infra.jpa.entity.ATag;
import org.herovole.blogproj.infra.jpa.entity.MCountry;
import org.herovole.blogproj.infra.jpa.repository.ATagRepository;
import org.herovole.blogproj.infra.jpa.repository.MCountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/b/api")
@EnableConfigurationProperties(LocalProperty.class)
public class AdminJsonController {

    private static final String K_COUNTRY_SELECT_BOX_INPUT = "input";
    @Autowired
    private MCountryRepository mCountryRepository;

    @Autowired
    private ATagRepository aTagRepository;

    @PostMapping("/countryselectbox")
    public List<String> countrySelectBox(@RequestBody Map<String, String> request) {
        System.out.println("endpoint : select");
        System.out.println("b/api/countryselectbox");
        List<MCountry> candidates = mCountryRepository.findForwardMatchCandidates(request.get(K_COUNTRY_SELECT_BOX_INPUT));
        return candidates.stream().map(MCountry::getNameEn).sorted().toList();
    }

    @PostMapping("/tags")
    public List<String> tags(@RequestBody Map<String, String> request) {
        System.out.println("endpoint : select");
        System.out.println("b/api/tags");
        List<ATag> candidates = aTagRepository.findAllTags();
        return candidates.stream().map(ATag::toDomainObj).sorted().map(TagUnit::toJsonString).toList();
    }

    @PostMapping("/article")
    public String article(@RequestBody Map<String, String> request) {
        System.out.println("endpoint : upsert");
        System.out.println("b/api/article");

        for(Map.Entry<String,String> e : request.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
        return "";
    }

}
